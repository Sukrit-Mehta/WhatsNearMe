package sukrit.example.com.nearme.Activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
    String tagValue="";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tagValue = getIntent().getStringExtra("tagValue");
        mRecyclerView = findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(Main2Activity.this);

        DownloadData downloadData=new DownloadData();
        downloadData.execute(url,tagValue);

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
                url1=new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.6305,77.3721&radius=1000&type="+params[1]+"&sensor=true&key=AIzaSyBGGC-1ZHbK31cuKwoTQBFmzJKVLOa5GPk");
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
            progressDialog.setMessage("Please wait...");
            mRecyclerView.setVisibility(View.INVISIBLE);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Place> jsonArray) {
            super.onPostExecute(jsonArray);

            mRecyclerView.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
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
                    String photo_reference ;
                    if(jsonArray.getJSONObject(i).has("photos")) {
                        photo_reference = jsonArray.getJSONObject(i).getJSONArray("photos").getJSONObject(0)
                                .getString("photo_reference");
                    }
                    else {
                        photo_reference="CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU";
                    }
                    Boolean openNow;
                    if(jsonArray.getJSONObject(i).has("opening_hours")) {
                        if(jsonArray.getJSONObject(i).getJSONObject("opening_hours").getBoolean("open_now")==true) {
                            openNow = true;
                        }
                        else {
                            openNow = false;
                        }
                    }
                    else {
                        openNow = false;
                    }
                    URL url = new URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth=140&maxheight=140&photoreference="+photo_reference+"&key="+"AIzaSyBGGC-1ZHbK31cuKwoTQBFmzJKVLOa5GPk");
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

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
                    if(openNow==null)
                    {
                        openNow = true;
                    }

                    Place p = new Place(name,vicinity,rating,bmp,openNow);
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
