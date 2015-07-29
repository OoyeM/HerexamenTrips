package be.kdg.trips.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name="TripLocation")
public class TripLocation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="location_id", nullable=false, unique=true, length=11)
    private int locationId;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @OneToMany(cascade = javax.persistence.CascadeType.REMOVE,mappedBy = "tripLocation")
    @Cascade({CascadeType.REMOVE})
    private Set<TripImage> tripImages;

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

    public Set<TripImage> getTripImages() {
        return tripImages;
    }

    public void setTripImages(Set<TripImage> tripImages) {
        this.tripImages = tripImages;
    }
}
