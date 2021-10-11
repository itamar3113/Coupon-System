package com.jb.Couponsystem.service.impl;

import com.jb.Couponsystem.data.repo.CouponRepository;
import com.jb.Couponsystem.data.repo.CustomerRepository;
import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.data.entity.Customer;
import com.jb.Couponsystem.rest.controller.ex.CouponOutOfStockException;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;
import com.jb.Couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    @Override
    public Optional<Customer> getCustomer(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer create(Customer customer) {
        if (customer != null) {
            customer.setId(0);
            return customerRepository.save(customer);
        }
        return null;
    }

    @Override
    public Optional<Customer> update(Customer customer) {
        if (customer != null) {
            return Optional.of(customerRepository.save(customer));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public Optional<Coupon> addCoupon(long customerId, long couponId) throws CouponOutOfStockException, NoSuchCouponException {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            if (coupon.get().getAmount() > 0) {
                coupon.get().setAmount(coupon.get().getAmount() - 1);
                couponRepository.insertIntoCustomerCoupon(customerId,couponId);
                return coupon;
            }
            throw new CouponOutOfStockException();
        }
       throw new NoSuchCouponException("There is no coupon with id " + couponId);
    }

    @Override
    public List<Coupon> getAllCoupons(long customerId) {
        return couponRepository.findByCustomerId(customerId);
    }

    public List<Coupon> getNotPurchasedCoupons(long id) {
        List<Coupon> allCoupons = couponRepository.findAll();
        List<Coupon> customerCoupons = getAllCoupons(id);
        allCoupons.removeAll(customerCoupons);
        return allCoupons;
    }

    public List<Coupon> getExpiredCoupons(long customerId, LocalDate date) {
       return couponRepository.findCouponExpiredInWeek(customerId,date);
    }
}
