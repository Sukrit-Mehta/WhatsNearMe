package sukrit.example.com.nearme.Models;

/**
 * Created by Sukrit on 1/1/2018.
 */

public class Place {
    String name;
    String vicinity;
    Double rating;
    //Boolean openNowStatus;

    public Place() {
    }

    public Place(String name, String vicinity, Double rating) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
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

}
