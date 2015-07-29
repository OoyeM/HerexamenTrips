package be.kdg.trips.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy="tripId")
    private Set<Trip> trips;

    @OneToMany(mappedBy="createdBy")
    private Set<Event> createdEvents;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "User_Events", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "EVENT_ID",
                    nullable = false, updatable = false) })
    private Set<Event> events;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public Set<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(Set<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
