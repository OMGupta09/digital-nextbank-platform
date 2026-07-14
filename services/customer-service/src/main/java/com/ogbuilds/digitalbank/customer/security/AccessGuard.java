package com.ogbuilds.digitalbank.customer.security;

import com.ogbuilds.digitalbank.customer.exception.ForbiddenException;
import org.springframework.stereotype.Component;

@Component
public class AccessGuard {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    public void requireAdmin(String role) {

        if (!ADMIN_ROLE.equals(role)) {
            throw new ForbiddenException("This action requires admin privileges.");
        }

    }

    public void requireSelfOrAdmin(Long resourceAuthUserId, Long requesterAuthUserId, String role) {

        if (ADMIN_ROLE.equals(role)) {
            return;
        }

        if (!resourceAuthUserId.equals(requesterAuthUserId)) {
            throw new ForbiddenException("You are not allowed to access another customer's data.");
        }

    }

}