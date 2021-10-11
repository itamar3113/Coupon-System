package com.jb.Couponsystem.rest.controller;

import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.data.entity.Customer;
import com.jb.Couponsystem.data.repo.CouponRepository;
import com.jb.Couponsystem.rest.common.ClientSession;
import com.jb.Couponsystem.rest.controller.ex.CouponOutOfStockException;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;
import com.jb.Couponsystem.rest.controller.ex.TokenInvalidOrExpiredException;
import com.jb.Couponsystem.service.CustomerService;
import com.jb.Couponsystem.service.TokensManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;
    private final TokensManager tokensManager;


    @Autowired
    public CustomerController(CustomerService customerService, CouponRepository couponRepository, TokensManager tokensManager) {
        this.customerService = customerService;
        this.tokensManager = tokensManager;
    }

    @GetMapping("/customers")
    public ResponseEntity<Customer> getCustomer(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (!clientSession.isCompany()) {
            Optional<Customer> customer = customerService.getCustomer(clientSession.getClientId());
            return customer.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        throw new TokenInvalidOrExpiredException("This action is available for companies");
    }

    @GetMapping("/customers/coupons")
    public ResponseEntity<List<Coupon>> getCoupons(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (!clientSession.isCompany()) {
            List<Coupon> coupons = customerService.getAllCoupons(clientSession.getClientId());
            if (!coupons.isEmpty()) {
                return ResponseEntity.ok(coupons);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new TokenInvalidOrExpiredException("This action is available for companies");
    }

    @GetMapping("/customers/coupons/expired-in-week")
    public ResponseEntity<List<Coupon>> getAllCouponsExpiredInWeek(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (!clientSession.isCompany()) {
            LocalDate localDate = LocalDate.now().plusDays(7);
            List<Coupon> couponsExpiredInWeek = customerService.getExpiredCoupons(clientSession.getClientId(),localDate);
            if (!couponsExpiredInWeek.isEmpty()) {
                return ResponseEntity.ok(couponsExpiredInWeek);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new TokenInvalidOrExpiredException("This action is available for companies");
    }

    @GetMapping("/customers/purchase/{couponId}")
    public ResponseEntity<Coupon> purchaseCoupon(@RequestParam String token, @PathVariable long couponId) throws TokenInvalidOrExpiredException, CouponOutOfStockException, NoSuchCouponException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (!clientSession.isCompany()) {
            Optional<Coupon> coupon = customerService.addCoupon(clientSession.getClientId(), couponId);
            return coupon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        throw new TokenInvalidOrExpiredException("This action is available for companies");
    }

    @GetMapping("/customers/coupons/not-purchased")
    public ResponseEntity<List<Coupon>> notPurchasedCoupons(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (!clientSession.isCompany()) {
            List<Coupon> notPurchasedCoupons = customerService.getNotPurchasedCoupons(clientSession.getClientId());
            if (!notPurchasedCoupons.isEmpty()) {
                return ResponseEntity.ok(notPurchasedCoupons);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new TokenInvalidOrExpiredException("This action is available for companies");
    }
}
