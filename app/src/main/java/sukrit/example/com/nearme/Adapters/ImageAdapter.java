package sukrit.example.com.nearme.Adapters;

/**
 * Created by Sukrit on 1/1/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import sukrit.example.com.nearme.Activities.PlacesListActivity;
import sukrit.example.com.nearme.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public String[] tags = {
        "atm", "bus_station","train_station","taxi_stand","airport", "bank", "restaurant", "pharmacy", "movie_theater","doctor",
            "electrician","plumber","police","mall","post_office","stadium","hindu_temple",
            "parking","car_wash","laundry","hair_care"
    };

    public Integer[] mThumbIds = {
           R.drawable.atm_machine,R.drawable.bus_stop,R.drawable.train,R.drawable.taxi,R.drawable.airport,R.drawable.mastercard,R.drawable.hotel,R.drawable.pharmacy,R.drawable.theater
            ,R.drawable.doctor,R.drawable.electrician,R.drawable.plumber,R.drawable.police,R.drawable.mall,
            R.drawable.post_office,R.drawable.stadium,R.drawable.temple,R.drawable.parking,R.drawable.car_wash,
            R.drawable.laundry,R.drawable.hair_salon

    };

    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
        imageView.setPadding(21,21,21,24);

        imageView.setTag(tags[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlacesListActivity.class);
                intent.putExtra("tagValue",imageView.getTag().toString());
                mContext.startActivity(intent);
            }
        });
        
        return imageView;
    }

}