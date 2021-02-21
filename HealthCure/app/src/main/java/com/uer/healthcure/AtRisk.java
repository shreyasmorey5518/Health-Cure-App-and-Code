package com.uer.healthcure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
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

public class AtRisk extends Activity {
    ListView lst1;
    String heartrate[];
    String personname[];
    String oxylevel[];
    String temp[];
    String bpm[];
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atrisk);
        lst1=(ListView)findViewById(R.id.list11);

        try{
            GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"HeartDignosis.php";
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);

        }catch(Exception e){
            //Toast.makeText(AtRisk.this, ""+e, Toast.LENGTH_LONG).show();
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
            progress = ProgressDialog.show(AtRisk.this, null,"Searching Information...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
               // Toast.makeText(AtRisk.this," "+out, Toast.LENGTH_LONG).show();
                //- Extracting the Dataset Symptoms through JSon Decoding

                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                heartrate=new String[arraylength];
                personname=new String[arraylength];
                oxylevel=new String[arraylength];
                temp=new String[arraylength];
                bpm=new String[arraylength];


                for (int i = 0; i < jsonMainNode.length(); i++) {

                    // Problability getting
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    heartrate[i]=jsonChildNode.optString("HeartRate");
                    oxylevel[i]=jsonChildNode.optString("Oxygen");
                    temp[i]=jsonChildNode.optString("Temperature");
                    bpm[i]=jsonChildNode.optString("BPM");

                    int heartrat11= (int)Float.parseFloat(heartrate[i]);
                    int oxylevel1=(int)Float.parseFloat(oxylevel[i]);
                    int temp11=(int)Float.parseFloat(temp[i]);


                    if(heartrat11<36 || heartrat11>37 || oxylevel1<90 ||oxylevel1>100 || temp11<36 || temp11>37) {
                        personname[i] = "Name : "+jsonChildNode.optString("Name")+"\nTemperature  : "+temp[i]+"\nBPM : "+bpm[i]+"\nHeartRate : "+heartrate[i]+"\n Oxygen :"+ oxylevel[i];
                    }
                    //oxylevel[i]=jsonChildNode.optString("Oxygen");

                }




                LevelAdapter adapter=new LevelAdapter(AtRisk.this,  personname);
                lst1.setAdapter(adapter)	;

            }catch(Exception e){
                Toast.makeText(AtRisk.this,"Parameters not normal", Toast.LENGTH_LONG).show();
            }




            progress.dismiss();
        }



    }
}
