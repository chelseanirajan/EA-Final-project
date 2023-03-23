package edu.miu.badge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class ResponsePlanDTO {
    private Integer id;
    private String name;
    private String description;
    private List<PlanTypeDTO> planTypes;
    private List<LocationDTO> locations;
    private List<RolesDTO> allowedRoles;
}
