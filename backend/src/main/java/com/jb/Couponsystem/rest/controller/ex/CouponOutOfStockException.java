package com.jb.Couponsystem.rest.controller.ex;

public class CouponOutOfStockException extends Exception {

    public CouponOutOfStockException() {
        super("This coupon is out of stock");
    }
}
