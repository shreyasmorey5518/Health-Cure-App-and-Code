package com.uer.healthcure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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

public class ViewHealthDetails extends Activity {
TextView tv1;
    String heartrate[];
    String bpm[];
    String oxylevel[];
    String temperature[];
    GraphView graph,heart_graph,oxy_level,temp_graph;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewhealthdetails);

         graph = (GraphView) findViewById(R.id.graph);
         heart_graph=(GraphView) findViewById(R.id.heartrate);
         oxy_level=(GraphView) findViewById(R.id.oxygen);
         temp_graph=(GraphView) findViewById(R.id.temperature);


         Intent i1= getIntent();

         String kitid=i1.getStringExtra("uid");

        try{
           GetData gettrans=new GetData();
            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"ViewHealthDetails.php?kid="+ URLEncoder.encode(kitid);
            // url=url+"pass="+URLEncoder.encode(upass)+"&";
            gettrans.execute(url);

        }catch(Exception e){
            //Toast.makeText(ViewHealthDetails.this, ""+e, Toast.LENGTH_LONG).show();
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
            progress = ProgressDialog.show(ViewHealthDetails.this, null,"Searching Information...");

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            try{
               // Toast.makeText(ViewHealthDetails.this," "+out, Toast.LENGTH_LONG).show();
                //- Extracting the Dataset Symptoms through JSon Decoding

                JSONObject jsonResponse = new JSONObject(out);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("user_info");
                int arraylength=jsonMainNode.length();
                heartrate=new String[arraylength];
                bpm=new String[arraylength];
               oxylevel=new String[arraylength];
                temperature=new String[arraylength];


                for (int i = 0; i < jsonMainNode.length(); i++) {

                    // Problability getting
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    heartrate[i]=""+(int)Float.parseFloat(jsonChildNode.optString("HeartRate"));
                    bpm[i]=""+(int)Float.parseFloat(jsonChildNode.optString("BPM"));
                    oxylevel[i]=""+(int)Float.parseFloat(jsonChildNode.optString("Oxygen"));
                    temperature[i]=""+(int)Float.parseFloat(jsonChildNode.optString("Temperature"));
                    //Toast.makeText(ViewHealthDetails.this," "+ temperature[i], Toast.LENGTH_LONG).show();

                }






            }catch(Exception e){
                //Toast.makeText(ViewHealthDetails.this," ", Toast.LENGTH_LONG).show();
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{

                    new DataPoint(0, Integer.parseInt(bpm[0])),
                    new DataPoint(1, Integer.parseInt(bpm[1])),
                    new DataPoint(2, Integer.parseInt(bpm[2])),
                    new DataPoint(3, Integer.parseInt(bpm[3])),
                    new DataPoint(3, Integer.parseInt(bpm[4])),
                    new DataPoint(3, Integer.parseInt(bpm[5])),
                    new DataPoint(3, Integer.parseInt(bpm[6])),
                    new DataPoint(3, Integer.parseInt(bpm[7])),
                    new DataPoint(4, Integer.parseInt(bpm[8]))

        });
            graph.addSeries(series);


            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                    new DataPoint(0, Integer.parseInt(heartrate[0])),
                    new DataPoint(1, Integer.parseInt(heartrate[1])),
                    new DataPoint(2, Integer.parseInt(heartrate[2])),
                    new DataPoint(3, Integer.parseInt(heartrate[3])),
                    new DataPoint(3, Integer.parseInt(heartrate[4])),
                    new DataPoint(3, Integer.parseInt(heartrate[5])),
                    new DataPoint(3, Integer.parseInt(heartrate[6])),
                    new DataPoint(3, Integer.parseInt(heartrate[7])),
                    new DataPoint(4, Integer.parseInt(heartrate[8]))

            });
            heart_graph.addSeries(series1);


            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                    new DataPoint(0, Integer.parseInt(oxylevel[0])),
                    new DataPoint(1, Integer.parseInt(oxylevel[1])),
                    new DataPoint(2, Integer.parseInt(oxylevel[2])),
                    new DataPoint(3, Integer.parseInt(oxylevel[3])),
                    new DataPoint(3, Integer.parseInt(oxylevel[4])),
                    new DataPoint(3, Integer.parseInt(oxylevel[5])),
                    new DataPoint(3, Integer.parseInt(oxylevel[6])),
                    new DataPoint(3, Integer.parseInt(oxylevel[7])),
                    new DataPoint(4, Integer.parseInt(oxylevel[8]))

            });
           oxy_level.addSeries(series2);




            LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                    new DataPoint(0, Integer.parseInt(temperature[0])),
                    new DataPoint(1, Integer.parseInt(temperature[1])),
                    new DataPoint(2, Integer.parseInt(temperature[2])),
                    new DataPoint(3, Integer.parseInt(temperature[3])),
                    new DataPoint(3, Integer.parseInt(temperature[4])),
                    new DataPoint(3, Integer.parseInt(temperature[5])),
                    new DataPoint(3, Integer.parseInt(temperature[6])),
                    new DataPoint(3, Integer.parseInt(temperature[7])),
                    new DataPoint(4, Integer.parseInt(temperature[8]))

            });
            temp_graph.addSeries(series3);

            progress.dismiss();
        }



    }







}
