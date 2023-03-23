package edu.miu.badge.exceptions;

public class TransactionDeclinedException extends RuntimeException{


    public TransactionDeclinedException(String message) {
        super(message);
    }
}
