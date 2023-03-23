package edu.miu.badge.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMembershipDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int member_id;
    private int plan_id;
    private int planType_id;
    private int usedAllowances;
}
