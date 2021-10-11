package com.jb.Couponsystem.rest.controller;

import com.jb.Couponsystem.data.entity.Company;
import com.jb.Couponsystem.rest.common.ClientSession;
import com.jb.Couponsystem.service.TokensManager;
import com.jb.Couponsystem.service.login.LoginService;
import com.jb.Couponsystem.service.login.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final TokensManager tokensManager;

    @Autowired

    public LoginController(LoginService loginService, TokensManager tokensManager) {
        this.loginService = loginService;
        this.tokensManager = tokensManager;
    }

    @PostMapping("/company/login")
    public ResponseEntity<String> loginCompany(@RequestParam String email, @RequestParam String password) throws InvalidLoginException {
            String token = loginService.generateToken();
            ClientSession session = loginService.createSession(email, password, true);
            tokensManager.put(token, session);
            return ResponseEntity.ok(token);
    }

    @PostMapping("/customer/login")
    public ResponseEntity<String> loginCustomer(@RequestParam String email, @RequestParam String password) throws InvalidLoginException {
            String token = loginService.generateToken();
            ClientSession session = loginService.createSession(email, password, false);
            tokensManager.put(token, session);
            return ResponseEntity.ok(token);
    }
}
