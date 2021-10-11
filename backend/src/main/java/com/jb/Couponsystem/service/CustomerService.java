package com.jb.Couponsystem.service;

import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.data.entity.Customer;
import com.jb.Couponsystem.rest.controller.ex.CouponOutOfStockException;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {

    Optional<Customer> getCustomer(long id);

    List<Customer> getAllCustomers();

    void deleteCustomer(long id);

    Customer create(Customer employee);

    Optional<Customer> update(Customer employee);

    Optional<Customer> getByEmail(String email);

    Optional<Coupon> addCoupon(long customerId, long couponId) throws CouponOutOfStockException, NoSuchCouponException;

    List<Coupon> getAllCoupons(long customerId);

    public List<Coupon> getNotPurchasedCoupons(long id);

    public List<Coupon> getExpiredCoupons(long customerId, LocalDate date);
}
