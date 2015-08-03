package be.kdg.trips.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name = "TripLocation")
public class TripLocation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false, unique = true, length = 11)
    private int locationId;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "tripLocation")
    private Set<TripImage> tripImages;

    @Column(name = "lng")
    private double lng;
    @Column(name = "lat")
    private double lat;
    @Column(name = "category")
    private String category;
    @Column(name = "orderNumber")
    private int orderNumber;
    @Column(name = "title")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "question")
    private String question;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<TripImage> getTripImages() {
        return tripImages;
    }

    public void setTripImages(Set<TripImage> tripImages) {
        this.tripImages = tripImages;
    }
}