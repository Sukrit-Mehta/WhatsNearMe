package sukrit.example.com.nearme.Adapters;

/**
 * Created by Sukrit on 1/1/2018.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import sukrit.example.com.nearme.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
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
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(170, 170));
        
        return imageView;
    }

}