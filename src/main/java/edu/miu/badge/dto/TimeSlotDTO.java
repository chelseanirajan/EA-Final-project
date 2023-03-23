package edu.miu.badge.dto;

import edu.miu.badge.enumeration.DayOfTheWeek;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Daniel Tsegay Meresie
 */
@Setter @Getter @AllArgsConstructor
@NoArgsConstructor @ToString
public class TimeSlotDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private  DayOfTheWeek day;
}
