package be.kdg.trips.model;

/**
 * Created by Matthias on 10/08/2015.
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_events",uniqueConstraints = @UniqueConstraint(columnNames = { "event_id", "user_id" }))
public class UserEvent implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "userevent_id")
    private Integer usereventId;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "accepted")
    private boolean accepted;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User invitedUser;

    @ManyToOne
    @JoinColumn(name = "event_id", insertable=false, updatable=false)
    private Event event;

    public Integer getUsereventId() {
        return usereventId;
    }

    public void setUsereventId(Integer usereventId) {
        this.usereventId = usereventId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(User invitedUser) {
        this.invitedUser = invitedUser;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

}
