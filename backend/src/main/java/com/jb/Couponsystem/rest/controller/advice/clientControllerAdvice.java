package com.jb.Couponsystem.rest.controller.advice;

import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.rest.common.ErrorResponse;
import com.jb.Couponsystem.rest.controller.ex.CouponOutOfStockException;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;
import com.jb.Couponsystem.rest.controller.ex.TokenInvalidOrExpiredException;
import com.jb.Couponsystem.service.login.ex.InvalidLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class clientControllerAdvice {

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidLogin(InvalidLoginException exception) {
        return ErrorResponse.from(exception);
    }

    @ExceptionHandler(TokenInvalidOrExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleTokenInvalidOrExpired(TokenInvalidOrExpiredException exception) {
        return ErrorResponse.from(exception);
    }

    @ExceptionHandler(CouponOutOfStockException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleCouponOutOfStock(CouponOutOfStockException exception) {
        return ErrorResponse.from(exception);
    }

    @ExceptionHandler(NoSuchCouponException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleNoSuchCoupon(NoSuchCouponException exception) {
        return ErrorResponse.from(exception);
    }
}
