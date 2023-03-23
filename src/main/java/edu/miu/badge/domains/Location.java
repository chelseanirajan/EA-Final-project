package edu.miu.badge.domains;

import edu.miu.badge.enumeration.LocationType;
import jakarta.persistence.*;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@Entity
@Table(name = "Locations")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    @Column(nullable = false)
    private String locationName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int capacity;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Location_Timeslot")
    private List<TimeSlot> timeSlots;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;
}
