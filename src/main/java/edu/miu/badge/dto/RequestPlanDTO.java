package edu.miu.badge.dto;

import edu.miu.badge.domains.Location;
import edu.miu.badge.domains.PlanType;
import edu.miu.badge.domains.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestPlanDTO {
    private String name;
    private String description;
    private List<Integer> planTypesId;
    private List<Long> locationsId;
    private List<Integer> allowedRolesId;
}
