package edu.miu.badge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.miu.badge.domains.Location;
import edu.miu.badge.domains.Member;
import edu.miu.badge.domains.Membership;
import edu.miu.badge.domains.Transaction;
import edu.miu.badge.enumeration.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link Transaction} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTransactionDTO {
    private  int id;
    private LocalDateTime date;
    private ResponseMemberDTO member;
    private ResponseMembershipDTO membership;
    private LocationDTO location;
    private TransactionType type;
}
