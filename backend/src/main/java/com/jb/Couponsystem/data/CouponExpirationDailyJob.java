package com.jb.Couponsystem.data;

import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.data.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CouponExpirationDailyJob {

    private final CouponRepository couponRepository;
    private static final long COUPON_EXPIRED_RATE = 86_000_000;

    @Autowired
    public CouponExpirationDailyJob(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Scheduled(fixedRate = COUPON_EXPIRED_RATE)
    public void DeleteExpiredCoupons() {
       List<Coupon> coupons = couponRepository.findEndDateBefore(LocalDate.now());
        for (Coupon coupon : coupons) {
            couponRepository.deleteById(coupon.getId());
        }
    }
}
