package io.vdubovsky.retailrules.util;

import io.vdubovsky.retailapi.dto.TransactionDto;
import io.vdubovsky.retailrules.exception.TransactionValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class ValidatorUtils {

    private final int maxPeriodDays;

    public ValidatorUtils(@Value("${app.bonus.max-period-days:90}") int maxPeriodDays) {
        this.maxPeriodDays = maxPeriodDays;
    }

    public void validateTransactionBonusComputationRequest(List<TransactionDto> transactions) {
        if (CollectionUtils.isEmpty(transactions)) {
            throw new TransactionValidationException("Transaction list should be populated");
        }
        LocalDate minDate = null;
        LocalDate maxDate = null;
        for (TransactionDto tx : transactions) {
            if (tx.getUserId() == null) {
                throw new TransactionValidationException("userId cannot be null");
            }
            if (tx.getId() == null) {
                throw new TransactionValidationException("id cannot be null");
            }
            if (tx.getExecutedAt() == null) {
                throw new TransactionValidationException("executedAt cannot be null");
            }
            if (tx.getAmount() == null) {
                throw new TransactionValidationException("amount cannot be null");
            }
            minDate = minDate == null ? tx.getExecutedAt().toLocalDate() : getEarliestDate(minDate, tx.getExecutedAt().toLocalDate());
            maxDate = maxDate == null ? tx.getExecutedAt().toLocalDate() : getLatestDate(maxDate, tx.getExecutedAt().toLocalDate());

            if (DAYS.between(minDate, maxDate) > maxPeriodDays) {
                throw new TransactionValidationException("period between earliest and latest transaction should not be greater than %d days".formatted(maxPeriodDays));
            }
        }
    }

    private LocalDate getEarliestDate(LocalDate localDate1, LocalDate localDate2) {
        return localDate1.isAfter(localDate2) ? localDate2 : localDate1;
    }

    private LocalDate getLatestDate(LocalDate localDate1, LocalDate localDate2) {
        return localDate1.isBefore(localDate2) ? localDate2 : localDate1;
    }
}
