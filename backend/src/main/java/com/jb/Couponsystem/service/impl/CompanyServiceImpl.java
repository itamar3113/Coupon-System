package com.jb.Couponsystem.service.impl;

import com.jb.Couponsystem.data.entity.Company;
import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.data.repo.CompanyRepository;
import com.jb.Couponsystem.data.repo.CouponRepository;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;
import com.jb.Couponsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
    }


    @Override
    public Optional<Company> getCompany(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company create(Company company) {
        if (company != null) {
            company.setId(0);
            return companyRepository.save(company);
        }
        return null;
    }

    @Override
    public Optional<Company> update(Company company) {
        if (company != null) {
            return Optional.of(companyRepository.save(company));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> getByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    @Override
    public Optional<Coupon> addCoupon(long companyId, Coupon coupon) throws NoSuchCouponException {
        Optional<Company> company = companyRepository.findById(companyId);
        if (coupon != null && company.isPresent()) {
            coupon.setId(0);
            coupon.setCompany(company.get());
            return Optional.of(couponRepository.save(coupon));
        }
        throw new NoSuchCouponException("Coupon is null");
    }

    @Override
    public List<Coupon> getAllCoupons(long companyId) {
        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean deleteCoupon(long companyId, long couponId) throws NoSuchCouponException {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent() && companyId == coupon.get().getCompany().getId()) {
            couponRepository.deleteById(couponId);
            return true;
        }
        throw new NoSuchCouponException("There is no coupon with id " + couponId);
    }

    @Override
    public Optional<Coupon> updateCoupon(Coupon coupon, long companyId) throws NoSuchCouponException {
        Optional<Company> company = companyRepository.findById(companyId);
        Optional<Coupon> optionalCoupon = couponRepository.findById(coupon.getId());
        if (company.isPresent() && optionalCoupon.isPresent() &&
                optionalCoupon.get().getCompany().getId() == companyId) {
            coupon.setCompany(company.get());
            return Optional.of(couponRepository.save(coupon));
        }
        throw new NoSuchCouponException("There is no coupon with id " + coupon.getId());
    }
}

