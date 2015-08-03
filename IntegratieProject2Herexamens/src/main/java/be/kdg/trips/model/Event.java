package be.kdg.trips.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name = "Event")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false, unique = true, length = 11)
    private int eventId;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "events")
    private Set<User> invitedUsers;
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Set<User> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(Set<User> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
