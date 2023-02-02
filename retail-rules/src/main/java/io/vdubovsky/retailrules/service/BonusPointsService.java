package io.vdubovsky.retailrules.service;

import io.vdubovsky.retailapi.dto.TransactionBonusPointsReport;
import io.vdubovsky.retailapi.dto.TransactionDto;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

public interface BonusPointsService {

    DeferredResult<TransactionBonusPointsReport> compute(List<TransactionDto> transactionDto);
}
