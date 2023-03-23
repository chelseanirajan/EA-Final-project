package edu.miu.badge.dto;

import edu.miu.badge.enumeration.LocationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import edu.miu.badge.domains.TimeSlot;
import java.util.List;
/**
 *
 * @author Daniel Tsegay Meresie
 */

@Setter @Getter @AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long locationId;
    private String locationName;
    private String description;
    private int capacity;
    private LocationType locationType;
}
