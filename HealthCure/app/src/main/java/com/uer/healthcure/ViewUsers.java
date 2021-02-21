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

public class ViewUsers extends Activity {
    ListView lst1;
    String heartrate[];
    String personname[];
    String oxylevel[];
    String temp[];
    String bpm[];
    String contact[];
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewusers);
        lst1=(ListView)findViewById(R.id.listdata);

        try{
            GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"ViewUsers.php";
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);

        }catch(Exception e){
            //Toast.makeText(ViewUsers.this, ""+e, Toast.LENGTH_LONG).show();
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
            progress = ProgressDialog.show(ViewUsers.this, null,"Searching Information...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
                //Toast.makeText(ViewUsers.this," "+out, Toast.LENGTH_LONG).show();
                //- Extracting the Dataset Symptoms through JSon Decoding

                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                heartrate=new String[arraylength];
                personname=new String[arraylength];
                oxylevel=new String[arraylength];
                temp=new String[arraylength];
                bpm=new String[arraylength];
                contact=new String[arraylength];


                for (int i = 0; i < jsonMainNode.length(); i++) {

                    // Problability getting
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    //heartrate[i]=jsonChildNode.optString("HeartRate");
                    oxylevel[i]=jsonChildNode.optString("Oxygen");
                    temp[i]=jsonChildNode.optString("Temperature");
                    bpm[i]=jsonChildNode.optString("BPM");
                    contact[i]=jsonChildNode.optString("ContactNumber");
                    personname[i]=jsonChildNode.optString("Name");


                    heartrate[i] ="Email  : "+jsonChildNode.optString("Email");

                    //Toast.makeText(ViewUsers.this," "+personname[i], Toast.LENGTH_LONG).show();
                    //oxylevel[i]=jsonChildNode.optString("Oxygen");

                }


                LevelAdapter2 adapter=new LevelAdapter2(ViewUsers.this,  personname,contact,heartrate);
                lst1.setAdapter(adapter)	;

            }catch(Exception e){
                //Toast.makeText(ViewUsers.this," "+e, Toast.LENGTH_LONG).show();
            }




            progress.dismiss();
        }



    }
}
