package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.dto.LoginRequest;
import com.ogbuilds.digitalbank.auth.dto.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}