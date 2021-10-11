package com.jb.Couponsystem.service;


import com.jb.Couponsystem.data.entity.Company;
import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Optional<Company> getCompany(long id);

    List<Company> getAllCompanies();

    void deleteCompany(long id);

    Company create(Company Company);

    Optional<Company> update(Company company);

    Optional<Company> getByEmail(String email);

    Optional<Coupon> addCoupon(long companyId, Coupon coupon) throws NoSuchCouponException;

    List<Coupon> getAllCoupons(long companyId);

    boolean deleteCoupon(long companyId, long couponId) throws NoSuchCouponException;

    Optional<Coupon> updateCoupon(Coupon coupon, long companyId) throws NoSuchCouponException;
}
