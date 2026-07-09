package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.dto.RegisterRequest;
import com.ogbuilds.digitalbank.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

}