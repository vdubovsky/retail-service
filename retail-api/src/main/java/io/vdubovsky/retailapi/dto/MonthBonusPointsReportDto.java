package io.vdubovsky.retailapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class MonthBonusPointsReportDto {
    private UUID userId;
    private Month month;
    private Integer year;
    private Integer bonusPoints;
    private List<TransactionDto> transactions = new ArrayList<>();
}
