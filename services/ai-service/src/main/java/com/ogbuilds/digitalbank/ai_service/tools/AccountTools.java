package com.ogbuilds.digitalbank.ai_service.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogbuilds.digitalbank.ai_service.client.AccountClient;
import com.ogbuilds.digitalbank.ai_service.util.UserContext;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountTools {

    private final AccountClient accountClient;
    private final ObjectMapper objectMapper;

    @Tool(description = """
            Returns all bank accounts belonging to the authenticated customer.

            Use this whenever the user asks about:
            - accounts
            - balance
            - account number
            - savings account
            - current account
            """)
    public String getMyAccounts() {

        Long authUserId = UserContext.getUserId();

        try {

            var accounts = accountClient
                    .getMyAccounts(authUserId)
                    .getData();

            return objectMapper.writeValueAsString(accounts);

        } catch (FeignException.NotFound ex) {

            log.warn("No customer profile linked to authUserId={}", authUserId);

            return "ERROR: This user has no customer profile set up yet. "
                    + "Tell the user their account setup is incomplete and they should contact support "
                    + "instead of saying no accounts were found.";

        } catch (FeignException ex) {

            log.error("Failed to fetch accounts for authUserId={}", authUserId, ex);

            return "ERROR: The account service is temporarily unavailable. "
                    + "Tell the user to try again shortly instead of saying no accounts were found.";

        } catch (Exception ex) {

            log.error("Failed to serialize accounts for authUserId={}", authUserId, ex);

            return "ERROR: Something went wrong reading account data. Tell the user to try again shortly.";

        }

    }

}