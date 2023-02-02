package io.vdubovsky.retailrules.rest.advice;

import io.vdubovsky.retailapi.dto.ErrorMessage;
import io.vdubovsky.retailrules.exception.TransactionValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransactionValidationException.class)
    public ErrorMessage handleBadRequestException(TransactionValidationException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage().setErrorCode(HttpStatus.BAD_REQUEST.value()).setErrorDescription(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorMessage handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage().setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setErrorDescription("Internal server error");
    }
}
