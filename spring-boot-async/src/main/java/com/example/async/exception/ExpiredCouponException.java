package com.example.async.exception;

public class ExpiredCouponException extends RuntimeException {
    public ExpiredCouponException(String message) {
        super(message);
    }
}
