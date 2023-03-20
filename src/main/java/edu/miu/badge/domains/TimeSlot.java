package edu.miu.badge.domains;

import edu.miu.badge.enumeration.DayOfTheWeek;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
@Entity @Table(name = "timeslots")
@Setter @Getter @AllArgsConstructor
@NoArgsConstructor @ToString
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeSlotId;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private  DayOfTheWeek day;
}
