package io.vdubovsky.retailapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TransactionDto {
    private UUID id;
    private BigDecimal amount;
    private UUID userId;
    private LocalDateTime executedAt;
    private Integer bonusPoints = 0;
}
