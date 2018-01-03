package sukrit.example.com.nearme.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sukrit.example.com.nearme.Adapters.PlaceNameAdapter;
import sukrit.example.com.nearme.Models.Place;
import sukrit.example.com.nearme.R;

public class Main2Activity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PlaceNameAdapter placeNameAdapter;

    ArrayList<Place> arrayList = new ArrayList<>();
    ArrayList<Place> jsonArray1 = new ArrayList<>();


    public static final String TAG="TTT";
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mRecyclerView = findViewById(R.id.recyclerView);

        DownloadData downloadData=new DownloadData();
        downloadData.execute(url);

        Log.d(TAG, "array List : "+arrayList);

        placeNameAdapter = new PlaceNameAdapter(arrayList,Main2Activity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
        mRecyclerView.setAdapter(placeNameAdapter);
    }

    public class DownloadData extends AsyncTask<String,Void,ArrayList<Place>>
    {

        @Override
        protected ArrayList<Place> doInBackground(String... params) {

            URL url1 = null;
            try {
                Log.d(TAG, "doInBackground: params[0]"+params[0]);
                url1=new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.6305,77.3721&radius=1000&type=restaurant&sensor=true&key=AIzaSyBGGC-1ZHbK31cuKwoTQBFmzJKVLOa5GPk");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                connection= (HttpURLConnection) url1.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream=null;
            try {
                inputStream = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder sb =new StringBuilder();
                String buffer;
                while ((buffer=br.readLine())!=null)
                {
                    sb.append(buffer);
                }
                String str = sb.toString();
                jsonArray1=getJson(str);
                Log.d(TAG, "doInBackground: " + jsonArray1);
            /*    connection.setReadTimeout(15000 *//* milliseconds *//*);
                connection.setConnectTimeout(15000 *//* milliseconds *//*);
                connection.setDoInput(true);*/

            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonArray1;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected void onPostExecute(ArrayList<Place> jsonArray) {
            super.onPostExecute(jsonArray);

            Log.d(TAG, "onPostExecute: "+jsonArray.size());

            int i;
            for (i = 0; i < jsonArray.size(); i++) {
                arrayList.add(jsonArray.get(i));
                placeNameAdapter.notifyDataSetChanged();
            }
            Log.d(TAG, "onPostExecute: " +arrayList);

        }

        public ArrayList<Place> getJson(String str)
        {
            ArrayList<Place> placeArrayList = new ArrayList<>();
            try {

                JSONObject jsonObject = new JSONObject(str);
                //Log.d("TAGGER", "getJson: "+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i=0;i<jsonArray.length();i++) {
                    String name = jsonArray.getJSONObject(i).getString("name");
                    String vicinity = jsonArray.getJSONObject(i).getString("vicinity");
                    Double rating = jsonArray.getJSONObject(i).getDouble("rating");
                    //String icon = jsonArray.getJSONObject(0).getString("icon");

                    if(name==null)
                    {
                        name = "default Name";
                    }
                    if(vicinity==null)
                    {
                        vicinity = "default Vicinity";
                    }
                    if(rating==null)
                    {
                        rating = 10.0;
                    }
                 /*   if(icon==null)
                    {
                        icon = "default Icon";
                    }*/


                    Place p = new Place(name,vicinity,rating);
//                Log.d(TAG, "getJson: "+jsonArray.getJSONObject(0).getString("name"));
//                Log.d(TAG, "getJson: "+jsonArray.getJSONObject(0).getString("vicinity"));
//                Log.d(TAG, "getJson: "+jsonArray.getJSONObject(0).getDouble("rating"));
//                Log.d(TAG, "getJson: "+jsonArray.getJSONObject(0).getString("icon"));
                    placeArrayList.add(p);
                }
                Log.d(TAG, "getJson: "+placeArrayList);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return placeArrayList;
        }
    }

}
