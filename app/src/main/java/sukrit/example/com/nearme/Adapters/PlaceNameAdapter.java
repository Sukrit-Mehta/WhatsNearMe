package sukrit.example.com.nearme.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import sukrit.example.com.nearme.Models.Place;
import sukrit.example.com.nearme.R;
import sukrit.example.com.nearme.Utils.CommonMethods;

/**
 * Created by Sukrit on 1/1/2018.
 */

public class PlaceNameAdapter extends RecyclerView.Adapter<PlaceNameAdapter.DetailsItemHolder> {

    ArrayList<Place> placeArrayList;
    Context context;

    public PlaceNameAdapter(ArrayList<Place> placeArrayList, Context context) {
        Log.d("GGG", "PlaceNameAdapter: " + placeArrayList);
        this.placeArrayList = placeArrayList;
        this.context = context;
    }

    @Override
    public DetailsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutType;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutType = R.layout.place_list_item;
        View itemView = layoutInflater.inflate(layoutType, parent, false);
        return new DetailsItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailsItemHolder holder, int position) {
        Place place = placeArrayList.get(position);
        holder.tvName.setText(place.getName());
        holder.tvAddress.setText(place.getVicinity());
        holder.ivPlaceImage.setImageBitmap(place.getBitmap());
        holder.ratingBar.setRating(place.getRating().floatValue());
        holder.tvOpenNow.setText(String.valueOf(place.getOpenNow()));
        Double dist = CommonMethods.getDistance(28.6305,77.3721,place.getLat(),place.getLng(),"K");
        Double dist1 = Double.valueOf(Math.round(dist * 100));
        dist1 = dist1 / 100;
        Double d = dist1;
        holder.btnDistance.setText(d+" Kms");

    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public class DetailsItemHolder extends RecyclerView.ViewHolder {

        View testView;
        TextView tvName;
        TextView tvAddress;
        ImageView ivPlaceImage;
        RatingBar ratingBar;
        Button btnDistance;
        TextView tvOpenNow;

        public DetailsItemHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvPlaceName);
            tvAddress = itemView.findViewById(R.id.tvPlaceAddress);
            ivPlaceImage = itemView.findViewById(R.id.ivPlaceImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvOpenNow = itemView.findViewById(R.id.tvOpenNowValue);
            btnDistance = itemView.findViewById(R.id.btnDistance);
            testView = itemView;
        }
    }
}
