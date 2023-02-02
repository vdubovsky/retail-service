package io.vdubovsky.retailrules.util;

import io.vdubovsky.retailapi.dto.MonthBonusPointsReportDto;
import io.vdubovsky.retailapi.dto.TransactionBonusPointsReport;
import io.vdubovsky.retailapi.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class MapperUtils {

    public TransactionBonusPointsReport mapToReport(List<TransactionDto> source) {
        Map<UUID, List<MonthBonusPointsReportDto>> reportMap = new LinkedHashMap<>();

        source.stream()
                .sorted(Comparator.comparing(TransactionDto::getExecutedAt))
                .filter(tx -> tx.getBonusPoints() > 0)
                .forEach(tx -> reportMap.merge(tx.getUserId(), createMonthReportListForNewUser(tx),
                        (existedList, firstList) -> updateMonthReportListForExistingUser(existedList, tx)));

        return new TransactionBonusPointsReport().setReport(reportMap);
    }

    private List<MonthBonusPointsReportDto> createMonthReportListForNewUser(TransactionDto tx) {
        List<MonthBonusPointsReportDto> result = new ArrayList<>();
        MonthBonusPointsReportDto monthBonusPointsReportDto = createNewMonthReport(tx);
        result.add(monthBonusPointsReportDto);
        return result;
    }

    private List<MonthBonusPointsReportDto> updateMonthReportListForExistingUser(List<MonthBonusPointsReportDto> list, TransactionDto tx) {
        MonthBonusPointsReportDto existingMonthReport = list.stream()
                .filter(sameMonthAndYear(tx))
                .findFirst().orElse(null);

        if (existingMonthReport == null) {
            MonthBonusPointsReportDto newMonthReport = createNewMonthReport(tx);
            list.add(newMonthReport);
        } else {
            existingMonthReport.setBonusPoints(existingMonthReport.getBonusPoints() + tx.getBonusPoints());
            existingMonthReport.getTransactions().add(tx);
        }

        return list;
    }

    private MonthBonusPointsReportDto createNewMonthReport(TransactionDto tx) {
        MonthBonusPointsReportDto monthReport = new MonthBonusPointsReportDto();

        monthReport.setBonusPoints(tx.getBonusPoints());
        monthReport.setMonth(tx.getExecutedAt().getMonth());
        monthReport.setYear(tx.getExecutedAt().getYear());
        monthReport.setUserId(tx.getUserId());

        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionDtos.add(tx);
        monthReport.setTransactions(transactionDtos);

        return monthReport;
    }

    private Predicate<MonthBonusPointsReportDto> sameMonthAndYear(TransactionDto transaction) {
        return monthReport -> monthReport.getYear().equals(transaction.getExecutedAt().getYear())
                && monthReport.getMonth().equals(transaction.getExecutedAt().getMonth());
    }
}
