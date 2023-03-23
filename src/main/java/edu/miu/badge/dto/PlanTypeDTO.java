package edu.miu.badge.dto;

import edu.miu.badge.enumeration.PlanTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanTypeDTO {
    private Integer id;
    private PlanTypeEnum planType;
}
