package com.uer.healthcure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class PredictionPahse2 extends Activity {
String sheartrate,sbp,stemp,soxy;
int tp=0;
int matchcount=0;
String data[];
ListView lst;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prediction2);


        lst=(ListView)findViewById(R.id.list223) ;
        Intent i1= getIntent();
        sheartrate =i1.getStringExtra("heartrate");
        sbp=i1.getStringExtra("bpm");
        stemp=i1.getStringExtra("temp");
        soxy=i1.getStringExtra("oxy");

        try{
           GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"GetTraining.php";
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);

        }catch(Exception e){
            //Toast.makeText(PredictionPahse2.this, ""+e, Toast.LENGTH_LONG).show();
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
            progress = ProgressDialog.show(PredictionPahse2.this, null,"Searching Information...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                //Toast.makeText(PredictionPahse2.this," "+out, Toast.LENGTH_LONG).show();
                //- Extracting the Dataset Symptoms through JSon Decoding

                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                data=new String[arraylength];

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    matchcount=0;
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    if(sheartrate.contentEquals(jsonChildNode.optString("HeartRate"))){
                        matchcount=matchcount+1;
                    }
                    if(sbp.contentEquals(jsonChildNode.optString("BPM"))){
                        matchcount=matchcount+1;
                    }
                    if(soxy.contentEquals(jsonChildNode.optString("Oxylevel"))){
                        matchcount=matchcount+1;
                    }
                    if(stemp.contentEquals(jsonChildNode.optString("Temperature"))){
                        matchcount=matchcount+1;
                    }
                    float matchration=(float)matchcount/4;
                      data[i]="HR: "+jsonChildNode.optString("HeartRate")+" BPM : "+jsonChildNode.optString("BPM")+"Temp : "+jsonChildNode.optString("Temperature")+"Oxy : "+jsonChildNode.optString("Oxylevel")+"\n Match Count : "+matchcount+"\n Match Ration :"+ matchration+"\nPrediction :"+jsonChildNode.optString("Prediction");
                    // Problability getting
                    /*
                    heartrate.setText("Heart Rate : "+jsonChildNode.optString("HeartRate"));
                    bp.setText("Blood Pressure : "+jsonChildNode.optString("BPM"));
                    temp.setText("Temp : "+jsonChildNode.optString("Temperature"));
                    oxy.setText("OxyGen : "+jsonChildNode.optString("Oxygen"));
                    */



                    LevelAdapter l1=new LevelAdapter(PredictionPahse2.this,data);
                    lst.setAdapter(l1);

                }




            }catch(Exception e){
                //Toast.makeText(PredictionPahse2.this," ", Toast.LENGTH_LONG).show();
            }

            progress.dismiss();
        }



    }
}
