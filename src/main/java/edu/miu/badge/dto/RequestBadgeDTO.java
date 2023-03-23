package edu.miu.badge.dto;

import edu.miu.badge.enumeration.BadgeStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestBadgeDTO {
    private BadgeStatus badgeStatus;
    private int memberId;
}
