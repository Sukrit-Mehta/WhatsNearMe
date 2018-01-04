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

    public Place() {
    }

    public Place(String name, String vicinity, Double rating, Bitmap bitmap, Boolean openNow) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.bitmap = bitmap;
        this.openNow = openNow;
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
}
