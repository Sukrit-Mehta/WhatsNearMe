package sukrit.example.com.nearme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import sukrit.example.com.nearme.Activities.RouteMapsActivity;
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
    public void onBindViewHolder(final DetailsItemHolder holder, final int position) {
        Place place = placeArrayList.get(position);
        holder.tvName.setText(place.getName());
        holder.tvAddress.setText(place.getVicinity());
        if(place.getBitmap()!=null) {
         holder.ivPlaceImage.setImageBitmap(place.getBitmap());
        /*Picasso.with(context)
                .load("https://lh4.googleusercontent.com/-1wzlVdxiW14/USSFZnhNqxI/AAAAAAAABGw/YpdANqaoGh4/s1600-w140-h140/Google%2BSydney")
                .fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivPlaceImage);*/
        }

        holder.ratingBar.setRating(place.getRating().floatValue());
        holder.tvOpenNow.setText(String.valueOf(place.getOpenNow()));
        Double dist = CommonMethods.getDistance(place.getMyLat(),place.getMyLong(),place.getLat(),place.getLng(),"K");
        Double dist1 = Double.valueOf(Math.round(dist * 1000));
        dist1 = dist1 / 1000;
        Double d = dist1;
        holder.btnDistance.setText(d+" Kms");

        holder.testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                func(position);
            }
        });

       /* holder.btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                func(position);
            }
        });*/

        holder.btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                func(position);
            }
        });
        if(String.valueOf(place.getOpenNow()).equalsIgnoreCase("true"))
        {
            holder.tvOpenNow.setTextColor(Color.parseColor("#3aa83e"));
            holder.tvOpenNow.setText("Open Now !");
        }
        else {
            holder.tvOpenNow.setTextColor(Color.RED);
            holder.tvOpenNow.setText("Closed !");
        }
    }

    void func(int position)
    {
        Intent intent =new Intent(context,RouteMapsActivity.class);
        intent.putExtra("myLat",placeArrayList.get(position).getMyLat());
        intent.putExtra("myLong",placeArrayList.get(position).getMyLong());
        intent.putExtra("placeLat",placeArrayList.get(position).getLat());
        intent.putExtra("placeLong",placeArrayList.get(position).getLng());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    public class DetailsItemHolder extends RecyclerView.ViewHolder {

        View testView;
        TextView tvName;
        TextView tvAddress;
        ImageView ivPlaceImage;
        RatingBar ratingBar;
        Button btnDistance;
        TextView tvOpenNow;
        ImageView btnNavigate;

        public DetailsItemHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvPlaceName);
            tvAddress = itemView.findViewById(R.id.tvPlaceAddress);
            ivPlaceImage = itemView.findViewById(R.id.ivPlaceImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvOpenNow = itemView.findViewById(R.id.tvOpenNowValue);
            btnDistance = itemView.findViewById(R.id.btnDistance);
            //btnNavigate=itemView.findViewById(R.id.navigate);
            testView = itemView;
        }

    }
}
