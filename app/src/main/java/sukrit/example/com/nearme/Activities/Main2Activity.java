package sukrit.example.com.nearme.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    ArrayList<Place> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        arrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recylerView);

        DownloadData downloadData=new DownloadData();
        downloadData.execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.9912242,77.707112&radius=1000&type=restaurant&sensor=true&key=AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");

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
                url1=new URL(params[0]);
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
                String buffer = null;
                while ((buffer=br.readLine())!=null)
                {
                    sb.append(buffer);
                }
                String str = sb.toString();
                arrayList=getJson(str);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return arrayList;
        }


        @Override
        protected void onPostExecute(ArrayList<Place> jsonArray) {
            super.onPostExecute(jsonArray);

            int i;

            for (i = 0; i < jsonArray.size(); i++) {
                arrayList.add(jsonArray.get(i));
                placeNameAdapter.notifyDataSetChanged();
            }
        }

        public ArrayList<Place> getJson(String str)
        {
            ArrayList<Place> placeArrayList = new ArrayList<>();
            try {

                JSONObject jsonObject = new JSONObject(str);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i=0;i<jsonArray.length();i++) {
                    Place p = new Place(jsonArray.getJSONObject(i).getString("name"),
                            jsonArray.getJSONObject(i).getString("vicinity"),
                            jsonArray.getJSONObject(i).getDouble("rating"),
                            jsonArray.getJSONObject(i).getString("icon"),
                            jsonArray.getJSONObject(i).getJSONArray("opening_hours").getJSONObject(0).getBoolean("open_now")
                    );
                    placeArrayList.add(p);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return placeArrayList;
        }
    }

}
