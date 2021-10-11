package com.jb.Couponsystem.service.login;

import com.jb.Couponsystem.rest.common.ClientSession;
import com.jb.Couponsystem.service.login.ex.InvalidLoginException;

public interface LoginService {

    ClientSession createSession(String email, String password,boolean isCompany) throws InvalidLoginException;

    String generateToken();
}
