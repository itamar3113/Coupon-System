package com.jb.Couponsystem.data.repo;

import com.jb.Couponsystem.data.entity.Company;
import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndPassword(String email, String password);

}
