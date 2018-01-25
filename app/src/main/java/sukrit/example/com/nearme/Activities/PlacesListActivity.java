package sukrit.example.com.nearme.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.List;

import sukrit.example.com.nearme.Adapters.PlaceNameAdapter;
import sukrit.example.com.nearme.Models.Place;
import sukrit.example.com.nearme.R;

public class PlacesListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PlaceNameAdapter placeNameAdapter;

    ArrayList<Place> arrayList = new ArrayList<>();
    ArrayList<Place> jsonArray1 = new ArrayList<>();

    String url="";
    String tagValue="";
    ProgressDialog progressDialog;

    LinearLayoutManager llm;
    public static final String TAG = "locationsettings";

    double myLat,myLong;
    Toolbar mToolbar;

    Spinner spinner;
    TextView placeType;
    SearchView searchView;

    SharedPreferences sPref;
    int mOnScreenItems,mTotalItemsInList,mPreviousTotal,mFirstVisibleItem,mVisibleThreshold;
    boolean mLoadingItems=false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);


        mToolbar=findViewById(R.id.places_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Near Me");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView=findViewById(R.id.searchView);

        placeType=findViewById(R.id.placeType);

        spinner = findViewById(R.id.sortSpinner);

        List<String> categories = new ArrayList<String>();
        categories.add("Distance");
        categories.add("Ratings");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        myLat= HomeActivity.latitude;
        myLong= HomeActivity.longitude;

        Log.d(TAG, "onCreate: aaj "+myLat+","+myLong);

        tagValue = getIntent().getStringExtra("tagValue");
        placeType.setText(tagValue);

        mRecyclerView = findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(PlacesListActivity.this);

        DownloadData downloadData=new DownloadData();
        downloadData.execute(url,tagValue);

        llm = new LinearLayoutManager(PlacesListActivity.this);

        placeNameAdapter = new PlaceNameAdapter(arrayList,PlacesListActivity.this);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(placeNameAdapter);
     /*   mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mOnScreenItems = mRecyclerView.getChildCount();
                mTotalItemsInList = llm.getItemCount();
                mFirstVisibleItem = llm.findFirstVisibleItemPosition();

                if (mLoadingItems) {
                    if (mTotalItemsInList > mPreviousTotal) {
                        mLoadingItems = false;
                        mPreviousTotal = mTotalItemsInList;
                    }
                }

                if (!mLoadingItems && (mTotalItemsInList - mOnScreenItems) <= (mFirstVisibleItem + mVisibleThreshold)) {
                    new DownloadData().execute();
                    mLoadingItems = true;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.isEmpty() &&arrayList.size()>0)
                {
                    ArrayList<Place> filteredArrayList = new ArrayList<>();
                    for(int i=0;i<arrayList.size();i++)
                    {
                        if(arrayList.get(i).getName().toLowerCase().contains(newText.toLowerCase()) ||
                                arrayList.get(i).getVicinity().toLowerCase().contains(newText.toLowerCase()))
                        {
                            filteredArrayList.add(arrayList.get(i));
                        }
                    }
                    mRecyclerView.setAdapter(new PlaceNameAdapter(filteredArrayList,PlacesListActivity.this));
                }
                else {
                    mRecyclerView.setAdapter(new PlaceNameAdapter(arrayList,PlacesListActivity.this));
                }
                return true;
            }
        });



    }

    public class DownloadData extends AsyncTask<String,Void,ArrayList<Place>>
    {

        @Override
        protected ArrayList<Place> doInBackground(String... params) {

            URL url1 = null;
            try {
                Log.d(TAG, "doInBackground: params[0]"+params[0]);
                //url1=new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.6305,77.3721&radius=1000&type="+params[1]+"&sensor=true&key=AIzaSyBGGC-1ZHbK31cuKwoTQBFmzJKVLOa5GPk");
                //url1=new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+myLat+","+myLong+"&radius=5000&type="+params[1]+"&sensor=true&key=AIzaSyBGGC-1ZHbK31cuKwoTQBFmzJKVLOa5GPk");
                url1=new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+myLat+","+myLong+"&radius=1000&type="+params[1]+"&sensor=true&key=AIzaSyBGGC-1ZHbK31cuKwoTQBFmzJKVLOa5GPk");
                Log.d(TAG, "doInBackground: "+myLat+","+myLong);
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

            progressDialog.dismiss();

            mRecyclerView.setVisibility(View.VISIBLE);
//            progressDialog.dismiss();
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
                    Double rating=0.0;
                    if(jsonArray.getJSONObject(i).has("rating")) {
                         rating = jsonArray.getJSONObject(i).getDouble("rating");
                    }
                    else
                    {
                        rating=5.0;
                    }
                    Double longitude = jsonArray.getJSONObject(i).getJSONObject("geometry")
                            .getJSONObject("location").getDouble("lng");
                    Double latitude = jsonArray.getJSONObject(i).getJSONObject("geometry")
                            .getJSONObject("location").getDouble("lat");
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
                    Bitmap bmp=null;
                   try {
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                   }catch (Exception e) {
                       Log.d(TAG, "Exception "+e);
                   }
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

                    Place p = new Place(name,vicinity,rating,bmp,openNow,latitude,longitude,myLat,myLong,tagValue);
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
