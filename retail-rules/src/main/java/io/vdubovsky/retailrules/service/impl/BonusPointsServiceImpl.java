package io.vdubovsky.retailrules.service.impl;

import io.vdubovsky.retailapi.dto.TransactionBonusPointsReport;
import io.vdubovsky.retailapi.dto.TransactionDto;
import io.vdubovsky.retailrules.service.BonusPointsService;
import io.vdubovsky.retailrules.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class BonusPointsServiceImpl implements BonusPointsService {

    private final KieContainer kieContainer;
    private final ExecutorService droolsExecutorService;
    private final MapperUtils mapperUtils;

    @Override
    public DeferredResult<TransactionBonusPointsReport> compute(List<TransactionDto> transactions) {
        DeferredResult<TransactionBonusPointsReport> result = new DeferredResult<>();

        droolsExecutorService.submit(
                () -> {
                    updateTransactionDtoFactsWithBonusPoints(transactions);
                    result.setResult(mapperUtils.mapToReport(transactions));
                });

        return result;
    }

    private void updateTransactionDtoFactsWithBonusPoints(List<TransactionDto> transactionDto) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            for (TransactionDto dto : transactionDto) {
                kieSession.insert(dto);
            }
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
    }
}
