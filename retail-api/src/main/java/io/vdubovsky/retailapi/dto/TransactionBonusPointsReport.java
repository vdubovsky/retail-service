package io.vdubovsky.retailapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TransactionBonusPointsReport {
    private Map<UUID, List<MonthBonusPointsReportDto>> report = new LinkedHashMap<>();
    private Map<UUID, Integer> totalPoints = new LinkedHashMap<>();
}
