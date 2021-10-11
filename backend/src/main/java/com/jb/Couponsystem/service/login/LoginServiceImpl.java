package com.jb.Couponsystem.service.login;

import com.jb.Couponsystem.data.entity.Company;
import com.jb.Couponsystem.data.entity.Customer;
import com.jb.Couponsystem.data.repo.CompanyRepository;
import com.jb.Couponsystem.data.repo.CustomerRepository;
import com.jb.Couponsystem.rest.common.ClientSession;
import com.jb.Couponsystem.service.login.ex.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    private static final int LENGTH_TOKEN = 15;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LoginServiceImpl(CompanyRepository companyRepository, CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public ClientSession createSession(String email, String password, boolean isCompany) throws InvalidLoginException {
        if (isCompany) {
            Optional<Company> optionalCompany = companyRepository.findByEmailAndPassword(email, password);
            if (optionalCompany.isPresent()) {
                return ClientSession.of(optionalCompany.get().getId(), true);
            }
        } else {
            Optional<Customer> optionalCustomer = customerRepository.findByEmailAndPassword(email, password);
            if (optionalCustomer.isPresent()) {
                return ClientSession.of(optionalCustomer.get().getId(), false);
            }
        }
        throw new InvalidLoginException();
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, LENGTH_TOKEN);
    }
}
