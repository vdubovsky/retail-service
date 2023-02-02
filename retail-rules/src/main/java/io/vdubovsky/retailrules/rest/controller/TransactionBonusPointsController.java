package io.vdubovsky.retailrules.rest.controller;

import io.vdubovsky.retailapi.dto.TransactionBonusPointsReport;
import io.vdubovsky.retailapi.dto.TransactionDto;
import io.vdubovsky.retailrules.service.BonusPointsService;
import io.vdubovsky.retailrules.util.ValidatorUtils;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
@Validated
public class TransactionBonusPointsController {

    private final BonusPointsService bonusPointsService;
    private final ValidatorUtils validatorUtils;

    @PostMapping("/bonuses")
    public DeferredResult<TransactionBonusPointsReport> compute(
            @RequestBody List<TransactionDto> transactions) {
        validatorUtils.validateTransactionBonusComputationRequest(transactions);
        return bonusPointsService.compute(transactions);
    }
}
