package be.kdg.trips.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name = "Trip_label")
public class TripLabel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRIPLABEL_ID", nullable = false, unique = true, length = 11)
    private int tripLabelId;
    @Column(name = "DESCRIPTION", length = 20, nullable = true)
    private String description;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public int getTripLabelId() {
        return tripLabelId;
    }

    public void setTripLabelId(int tripLabelId) {
        this.tripLabelId = tripLabelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}
