package be.kdg.trips.model;

import javax.persistence.*;

/**
 * Created by Matthias on 23/07/2015.
 */
@Entity
@Table(name="Trip_Image")
public class TripImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID", nullable = false, unique = true, length = 11)
    private int imageId;
    @Column(name="imgUrl", length=1000, nullable=true)
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "locationId")
    private TripLocation tripLocation;
}
