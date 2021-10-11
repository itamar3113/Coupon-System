package com.jb.Couponsystem.data.repo;

import com.jb.Couponsystem.data.Converter;
import com.jb.Couponsystem.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Convert;
import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT coupon FROM Customer customer JOIN customer.coupons coupon" +
            " WHERE customer.id = :customerId")
    List<Coupon> findByCustomerId(long customerId);

    @Query("SELECT c FROM Coupon c WHERE c.company.id = :companyId")
    List<Coupon> findByCompanyId(long companyId);

    @Modifying
    @Query(value = "INSERT into customer_coupon (customer_id, coupon_id) Values (:customerId,:couponId)", nativeQuery = true)
    void insertIntoCustomerCoupon(@Param("customerId") long customerId, @Param("couponId") long couponId);


    @Query("SELECT c FROM Coupon c WHERE c.endDate <= :date")
    @Convert(converter = Converter.class)
    List<Coupon> findEndDateBefore(@Param("date") LocalDate date);

    @Query("SELECT coupon FROM Customer customer JOIN customer.coupons coupon" +
            " WHERE customer.id = :customerId AND coupon.endDate < :date")
    List<Coupon> findCouponExpiredInWeek(@Param("customerId") long customerId, @Param("date") LocalDate date);
}
