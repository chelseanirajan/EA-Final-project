package edu.miu.badge.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.miu.badge.domains.Badge;
import edu.miu.badge.enumeration.BadgeStatus;
import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link Badge} entity
 */

@Data
@NoArgsConstructor
public class ResponseBadgeDTO implements Serializable {

    private  int id;
    private BadgeStatus badgeStatus;
    private int badgeNumber;
    private ResponseMemberDTO member;

}