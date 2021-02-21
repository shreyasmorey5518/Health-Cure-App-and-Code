package com.uer.healthcure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class AllotKit2 extends Activity {
EditText et1;
Button b1;
String uid;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allotkit2);

        Intent i1= getIntent();

        uid=i1.getStringExtra("uid");

//Toast.makeText(AllotKit2.this,""+uid,Toast.LENGTH_LONG).show();
        et1=(EditText)findViewById(R.id.kitnumber);

        b1=(Button)findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str11=et1.getText().toString().trim();

                try {
                    //Toast.makeText(AllotKit2.this,"uid"+uid,Toast.LENGTH_LONG).show();
                    //Toast.makeText(AllotKit2.this,"str11"+str11,Toast.LENGTH_LONG).show();
                    DbParameter db1 = new DbParameter();
                    String url = db1.getHostpath();
                    url = url +"UpdateKit.php?uid="+URLEncoder.encode(uid.trim());
                    url=url+"&kit="+URLEncoder.encode(str11);
                    LoadData l1 = new LoadData();
                    l1.execute(url);

                }catch(Exception e){
                    //Toast.makeText(AllotKit2.this,""+e,Toast.LENGTH_LONG).show();
                }
            }
        });


    }




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
            progress = ProgressDialog.show(AllotKit2.this, null,"Searching Information...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                //Toast.makeText(AllotKit2.this," "+out, Toast.LENGTH_LONG).show();
                //- Extracting the Dataset Symptoms through JSon Decoding
                out=out.trim();
              if(out.contentEquals("1")){

                  //Toast.makeText(AllotKit2.this,"Kit Allot Sucessfully",Toast.LENGTH_LONG).show();
                  finish();
              }


            }catch(Exception e){
                //Toast.makeText(AllotKit2.this," ", Toast.LENGTH_LONG).show();
            }


            progress.dismiss();
        }



    }
}
