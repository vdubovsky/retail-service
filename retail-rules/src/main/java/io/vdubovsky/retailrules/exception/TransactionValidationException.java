package io.vdubovsky.retailrules.exception;

public class TransactionValidationException extends RuntimeException{

    public TransactionValidationException(String message) {
        super(message);
    }
}
