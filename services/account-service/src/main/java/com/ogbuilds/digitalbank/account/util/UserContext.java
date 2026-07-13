package com.ogbuilds.digitalbank.account.util;

import org.springframework.stereotype.Component;

@Component
public class UserContext {

    public Long getUserId(String header) {
        return Long.parseLong(header);
    }

}