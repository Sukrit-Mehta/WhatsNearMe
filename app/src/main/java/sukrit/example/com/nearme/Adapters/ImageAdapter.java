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

import sukrit.example.com.nearme.Activities.Main2Activity;
import sukrit.example.com.nearme.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public String[] tags = {
        "atm", "bus_station", "bank", "restaurant", "pharmacy", "movie_theater"
    };

    public Integer[] mThumbIds = {
           R.drawable.atm_machine,R.drawable.bus_stop,R.drawable.mastercard,R.drawable.hotel,R.drawable.pharmacy,R.drawable.theater
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
        imageView.setLayoutParams(new GridView.LayoutParams(170, 170));
        imageView.setTag(tags[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Main2Activity.class);
                intent.putExtra("tagValue",imageView.getTag().toString());
                mContext.startActivity(intent);
            }
        });
        
        return imageView;
    }

}