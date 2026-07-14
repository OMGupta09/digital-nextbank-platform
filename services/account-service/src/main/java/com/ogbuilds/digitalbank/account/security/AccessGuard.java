package com.ogbuilds.digitalbank.account.security;

import com.ogbuilds.digitalbank.account.client.CustomerClient;
import com.ogbuilds.digitalbank.account.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessGuard {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final CustomerClient customerClient;

    public void requireAdmin(String role) {

        if (!ADMIN_ROLE.equals(role)) {
            throw new AccessDeniedException("This action requires admin privileges.");
        }

    }

    public void requireOwnedAccountOrAdmin(Long resourceCustomerId, Long requesterAuthUserId, String role) {

        if (ADMIN_ROLE.equals(role)) {
            return;
        }

        Long requesterCustomerId = customerClient
                .getCustomerByAuthUserId(requesterAuthUserId)
                .getData()
                .getId();

        if (!resourceCustomerId.equals(requesterCustomerId)) {
            throw new AccessDeniedException("You are not allowed to access another customer's account.");
        }

    }

}