package com.ogbuilds.digitalbank.auth.service;

import com.ogbuilds.digitalbank.auth.dto.LoginResponse;
import com.ogbuilds.digitalbank.auth.dto.RefreshTokenRequest;

public interface RefreshTokenService {

    LoginResponse refresh(RefreshTokenRequest request);

}