package sukrit.example.com.nearme.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sukrit.example.com.nearme.Models.Place;
import sukrit.example.com.nearme.R;

/**
 * Created by Sukrit on 1/1/2018.
 */

public class PlaceNameAdapter extends RecyclerView.Adapter<PlaceNameAdapter.DetailsItemHolder> {

    ArrayList<Place> placeArrayList;
    Context context;

    public PlaceNameAdapter(ArrayList<Place> placeArrayList, Context context) {
        super();
        this.placeArrayList=placeArrayList;
        this.context=context;
    }

    @Override
    public DetailsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutType;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutType = R.layout.place_list_item;
        View itemView = layoutInflater.inflate(layoutType,parent,false);
        return new DetailsItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailsItemHolder holder, int position) {
        Place place = placeArrayList.get(position);
        holder.tvName.setText(place.getName());
        holder.tvAddress.setText(place.getVicinity());
        holder.tvRating.setText(String.valueOf(place.getRating()));
        holder.tvOpenNow.setText(String.valueOf(place.getOpenNowStatus()));
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }


    public class DetailsItemHolder extends RecyclerView.ViewHolder{

        View testView;
        TextView tvName;
        TextView tvAddress;
        TextView tvRating;
        TextView tvOpenNow;

        public DetailsItemHolder(View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvPlaceName);
            tvAddress = itemView.findViewById(R.id.tvPlaceAddress);
            tvAddress = itemView.findViewById(R.id.tvRatingValue);
            tvOpenNow = itemView.findViewById(R.id.tvOpenNowValue);
            testView  = itemView;
        }
    }
}
