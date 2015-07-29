package be.kdg.trips.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name="Trip")
public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="TRIP_ID", nullable=false, unique=true, length=11)
    private int tripId;
    @Column(name="TITLE", length=50, nullable=true)
    private String title;
    @Column(name="DESCRIPTION", length=500, nullable=true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;

    @OneToMany(mappedBy="trip")
    private Set<Event> events;

    @OneToMany(mappedBy="trip")
    private Set<TripLabel> tripLabels;

    @OneToMany(mappedBy = "trip")
    private Set<TripLocation> locations;

    public Set<TripLabel> getTripLabels() {
        return tripLabels;
    }

    public void setTripLabels(Set<TripLabel> tripLabels) {
        this.tripLabels = tripLabels;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int id) {
        this.tripId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
