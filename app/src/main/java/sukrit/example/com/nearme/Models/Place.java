package sukrit.example.com.nearme.Models;

import android.graphics.Bitmap;

/**
 * Created by Sukrit on 1/1/2018.
 */

public class Place {
    String name;
    String vicinity;
    Double rating;
    Bitmap bitmap;
    Boolean openNow;
    Double lat;
    Double lng;
    Double myLat;
    Double myLong;
    String placeType;

    public Place() {
    }

    public Place(String name, String vicinity, Double rating, Bitmap bitmap, Boolean openNow, Double lat, Double lng,Double myLat,Double myLong, String placeType) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.bitmap = bitmap;
        this.openNow = openNow;
        this.lat = lat;
        this.lng = lng;
        this.myLat = myLat;
        this.myLong = myLong;
        this.placeType=placeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getMyLat() {
        return myLat;
    }

    public void setMyLat(Double myLat) {
        this.myLat = myLat;
    }

    public Double getMyLong() {
        return myLong;
    }

    public void setMyLong(Double myLong) {
        this.myLong = myLong;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }
}
