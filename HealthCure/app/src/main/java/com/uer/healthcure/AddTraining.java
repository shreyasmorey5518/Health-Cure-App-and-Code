package com.uer.healthcure;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class AddTraining extends AppCompatActivity {

    EditText bpm,heartrate,temperature,oxylevel,prediction1;
    Button user_register;
    String pass11="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtraining);
        bpm=(EditText)findViewById(R.id.bpm);
        heartrate=(EditText)findViewById(R.id.heartrate);
        oxylevel=(EditText)findViewById(R.id.oxygen11);
        temperature=(EditText)findViewById(R.id.temperature);
        prediction1=(EditText)findViewById(R.id.uprediction);
        user_register=(Button)findViewById(R.id.register_user);
        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            try {
                DbParameter db1 = new DbParameter();
                String url = db1.getHostpath();

                url = url + "AddTraining.php?heartrate=" + URLEncoder.encode(heartrate.getText().toString())
                        + "&oxylevel=" + URLEncoder.encode(oxylevel.getText().toString())
                        + "&temp=" + URLEncoder.encode(temperature.getText().toString())
                        + "&prediction=" + URLEncoder.encode(prediction1.getText().toString())
                        + "&bpm=" + URLEncoder.encode(bpm.getText().toString());

                LoadData l1 = new LoadData();
                l1.execute(url);
            }catch(Exception e){
                //Toast.makeText(AddTraining.this,""+e, Toast.LENGTH_LONG).show();
            }

            }
        });

    }

    //code for send data to the server
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class LoadData extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;


                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();

            }catch (Exception e){

                out= e.toString();
            }
            return out;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(AddTraining.this, null,
                    "Updating  Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Toast.makeText(AddTraining.this, "Training Added Sucessfully"+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            finish();
        }



    }
}
