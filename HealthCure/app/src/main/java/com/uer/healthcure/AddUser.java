package com.uer.healthcure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

public class AddUser extends Activity {
    EditText user_name,contact,email,pass;
    Button user_register;
    String pass11="";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        user_name=(EditText)findViewById(R.id.name);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        user_register=(Button)findViewById(R.id.register_user);

        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    DbParameter db1 = new DbParameter();
                    String url = db1.getHostpath();
                    url = url +"AddPatient.php?name=" + URLEncoder.encode(user_name.getText().toString())
                            + "&contact=" + URLEncoder.encode(contact.getText().toString())
                            + "&email=" + URLEncoder.encode(email.getText().toString())
                            + "&pass=" + URLEncoder.encode(pass.getText().toString());

                    LoadData l1 = new LoadData();
                    l1.execute(url);
                }catch(Exception e){
                    //Toast.makeText(AddUser.this,""+e,Toast.LENGTH_LONG).show();
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
            progress = ProgressDialog.show(AddUser.this, null,
                    "Updating Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //Toast.makeText(AddUser.this, "User Added Sucessfully"+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            String pass= pass11;
          //  SmsManager smgr = SmsManager.getDefault();
           // smgr.sendTextMessage(contact.getText().toString(),null,"user name: "+email.getText().toString()+" password: "+pass,null,null);
            //transactions.setText("Transactions :"+count);

          // Intent register_i1 = new Intent(AddUser.this,Login.class);
          //  startActivity(register_i1);
            finish();
        }



    }
}
