package com.jb.Couponsystem.rest.controller;

import com.jb.Couponsystem.data.entity.Coupon;
import com.jb.Couponsystem.rest.common.ClientSession;
import com.jb.Couponsystem.rest.controller.ex.NoSuchCouponException;
import com.jb.Couponsystem.rest.controller.ex.TokenInvalidOrExpiredException;
import com.jb.Couponsystem.service.CompanyService;
import com.jb.Couponsystem.service.TokensManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {

    public final CompanyService companyService;
    private final TokensManager tokensManager;

    @Autowired
    public CompanyController(CompanyService companyService, TokensManager tokensManager) {
        this.companyService = companyService;
        this.tokensManager = tokensManager;
    }

    @PostMapping("/companies/add-coupon")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon, @RequestParam String token) throws TokenInvalidOrExpiredException, NoSuchCouponException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (clientSession.isCompany()) {
            return ResponseEntity.of(companyService.addCoupon(clientSession.getClientId(), coupon));
        }
        throw new TokenInvalidOrExpiredException("This action is available for customers");
    }

    @DeleteMapping("/companies/delete-coupon/{couponId}")
    public ResponseEntity<String> removeCoupon(@RequestParam String token, @PathVariable long couponId) throws TokenInvalidOrExpiredException, NoSuchCouponException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (clientSession.isCompany()) {
            if (companyService.deleteCoupon(clientSession.getClientId(), couponId)) {
                return new ResponseEntity<>("Coupon deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Coupon not found", HttpStatus.NOT_FOUND);
        }
        throw new TokenInvalidOrExpiredException("This action is available for customers");
    }



    @PostMapping("/companies/update-coupon")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon, @RequestParam String token) throws TokenInvalidOrExpiredException, NoSuchCouponException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (clientSession.isCompany()) {
            return ResponseEntity.of(companyService.updateCoupon(coupon, clientSession.getClientId()));
        }
        throw new TokenInvalidOrExpiredException("This action is available for customers");
    }

    @GetMapping("/companies/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (clientSession.isCompany()) {
            List<Coupon> allCoupons = companyService.getAllCoupons(clientSession.getClientId());
            if (!allCoupons.isEmpty()) {
                return ResponseEntity.ok(allCoupons);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new TokenInvalidOrExpiredException("This action is available for customers");
    }
}
