package edu.miu.badge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.miu.badge.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTransactionDTO {
    private Integer badgeId;
    private Integer planId;
    private Integer locationId;
}
