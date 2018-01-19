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
        //if(place.getBitmap()!=null) {
            holder.ivPlaceImage.setImageBitmap(place.getBitmap());
      //  }
       /* else {
            switch (place.getPlaceType())
            {
                case "atm":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.atm_machine));
                    break;
                case "doctor":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.doctor));
                    break;
                case "electrician":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.electrician));
                    break;
                case "laundry":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.laundry));
                    break;
                case "parking":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.parking));
                    break;
                case "car_wash":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.car_wash));
                    break;
                case "hair_care":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.hair_salon));
                    break;
                case "hindu_temple":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.temple));
                    break;
                case "stadium":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.stadium));
                    break;
                case "mall":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.mall));
                    break;
                case "police":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.police));
                    break;
                case "plumber":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.plumber));
                    break;
                case "post_office":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.post_office));
                    break;
                case "restaurant":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.restaurant));
                    break;
                case "train_station":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.train));
                    break;
                case "taxi_stand":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.taxi));
                    break;
                case "bus_station":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.bus_stop));
                    break;
                case "airport":
                    holder.ivPlaceImage.setImageDrawable(context.getResources().getDrawable(R.drawable.airport));
                    break;
                    default:
            }
        }*/
        holder.ratingBar.setRating(place.getRating().floatValue());
        holder.tvOpenNow.setText(String.valueOf(place.getOpenNow()));
        Double dist = CommonMethods.getDistance(place.getMyLat(),place.getMyLong(),place.getLat(),place.getLng(),"K");
        Double dist1 = Double.valueOf(Math.round(dist * 100));
        dist1 = dist1 / 100;
        Double d = dist1;
        holder.btnDistance.setText(d+" Kms");

        holder.btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,RouteMapsActivity.class);
                intent.putExtra("myLat",placeArrayList.get(position).getMyLat());
                intent.putExtra("myLong",placeArrayList.get(position).getMyLong());
                intent.putExtra("placeLat",placeArrayList.get(position).getLat());
                intent.putExtra("placeLong",placeArrayList.get(position).getLng());
                context.startActivity(intent);
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
