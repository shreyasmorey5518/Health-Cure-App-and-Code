package com.uer.healthcure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class Prediction extends Activity {
TextView heartrate,bp,temp,oxy;
String sheartrate,sbp,stemp,soxy;
Button b1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prediction);

       heartrate=(TextView)findViewById(R.id.heartrate);
       bp=(TextView)findViewById(R.id.bp);
       temp=(TextView)findViewById(R.id.temp);
       oxy=(TextView)findViewById(R.id.oxygen1);

       b1=(Button)findViewById(R.id.applyprediction);


       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent i1= new Intent(Prediction.this,PredictionPahse2.class);
               i1.putExtra("temp",stemp);
               i1.putExtra("oxy",soxy);
               i1.putExtra("bpm",sbp);
               i1.putExtra("heartrate",sheartrate);
               startActivity(i1);


           }
       });



        try{
           GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"GetCurrentData.php";
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);

        }catch(Exception e){
            //Toast.makeText(Prediction.this, ""+e, Toast.LENGTH_LONG).show();
        }



    }





    private class GetData extends AsyncTask<String, Integer, String> {
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
            progress = ProgressDialog.show(Prediction.this, null,"Searching Information...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                //Toast.makeText(Prediction.this," "+out, Toast.LENGTH_LONG).show();
                //- Extracting the Dataset Symptoms through JSon Decoding

                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();


                for (int i = 0; i < jsonMainNode.length(); i++) {

                    // Problability getting
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    heartrate.setText("Heart Rate : "+jsonChildNode.optString("HeartRate"));
                    bp.setText("Blood Pressure : "+jsonChildNode.optString("BPM"));
                    temp.setText("Temp : "+jsonChildNode.optString("Temperature"));
                    oxy.setText("OxyGen : "+jsonChildNode.optString("Oxygen"));

                    sheartrate=jsonChildNode.optString("HeartRate");
                    sbp=jsonChildNode.optString("BPM");
                    stemp=jsonChildNode.optString("Temperature");
                    soxy=jsonChildNode.optString("Oxygen");
                }




            }catch(Exception e){
                //Toast.makeText(Prediction.this," ", Toast.LENGTH_LONG).show();
            }

            progress.dismiss();
        }



    }
}
