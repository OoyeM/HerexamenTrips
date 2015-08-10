package be.kdg.trips.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name ="user_id")
	private Integer user_id;
	@NotEmpty
	@Email
	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;
	@NotEmpty
	@Column(name = "password", nullable = false, length = 60)
	private String password;
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	@OneToMany(mappedBy = "createdBy")
	private Set<Trip> trips;
	@OneToMany(mappedBy = "createdBy")
	private Set<Event> createdEvents;
	@OneToMany(mappedBy = "userId")
	private Set<UserEvent> invitedEvents;

//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "User_Events", joinColumns = {
//			@JoinColumn(name = "user_id", nullable = false, updatable = false)},
//			inverseJoinColumns = {@JoinColumn(name = "event_id",
//					nullable = false, updatable = false)})
//	private Set<Event> events;



	public User() {
	}

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String username, String password, boolean enabled, Set<UserRole> userRole) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}


	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}



	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public Set<UserRole> getUserRole() {
		return this.userRole;
	}



	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
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

	public Set<UserEvent> getInvitedEvents() {
		return invitedEvents;
	}

	public void setInvitedEvents(Set<UserEvent> invitedEvents) {
		this.invitedEvents = invitedEvents;
	}
}
