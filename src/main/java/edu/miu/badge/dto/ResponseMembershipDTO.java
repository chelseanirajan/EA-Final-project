package edu.miu.badge.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMembershipDTO {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ResponseMemberDTO member;
    private ResponsePlanDTO plan;
    private PlanTypeDTO planType;
    private int usedAllowances;
}
