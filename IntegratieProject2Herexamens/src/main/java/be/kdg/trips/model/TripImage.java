package be.kdg.trips.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name="Trip_Image")
public class TripImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID", nullable = false, unique = true, length = 11)
    private int imageId;
    @Column(name="imgUrl", length=1000, nullable=true)
    private String imgUrl;

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Column(name="thumbUrl", length=1000, nullable=true)
    private String thumbUrl;
    @Column(name="description", length=1000, nullable=true)
    private String description;
    @ManyToOne
    @JoinColumn(name = "locationId")
    private TripLocation tripLocation;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TripLocation getTripLocation() {
        return tripLocation;
    }

    public void setTripLocation(TripLocation tripLocation) {
        this.tripLocation = tripLocation;
    }
}
