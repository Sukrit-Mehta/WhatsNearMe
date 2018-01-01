package sukrit.example.com.nearme.Models;

/**
 * Created by Sukrit on 1/1/2018.
 */

public class Place {
    String name;
    String vicinity;
    Double rating;
    String icon;
    Boolean openNowStatus;

    public Place() {
    }

    public Place(String name, String vicinity, Double rating, String icon,  Boolean openNowStatus) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.icon = icon;
        this.openNowStatus = openNowStatus;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getOpenNowStatus() {
        return openNowStatus;
    }

    public void setOpenNowStatus(Boolean openNowStatus) {
        this.openNowStatus = openNowStatus;
    }
}
