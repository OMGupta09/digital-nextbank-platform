package com.ogbuilds.digitalbank.ai_service.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogbuilds.digitalbank.ai_service.client.TransactionClient;
import com.ogbuilds.digitalbank.ai_service.util.UserContext;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionTools {

    private final TransactionClient transactionClient;
    private final ObjectMapper objectMapper;

    @Tool(description = """
            Returns transaction history of the authenticated customer.

            Use this whenever the user asks about:
            - transactions
            - transaction history
            - recent transactions
            - deposits
            - withdrawals
            - transfers
            - expenses
            - spending
            """)
    public String getMyTransactions() {

        Long authUserId = UserContext.getUserId();

        try {

            var transactions = transactionClient
                    .getMyTransactions(authUserId)
                    .getData();

            return objectMapper.writeValueAsString(transactions);

        } catch (FeignException.NotFound ex) {

            log.warn("No customer profile linked to authUserId={}", authUserId);

            return "ERROR: This user has no customer profile set up yet. "
                    + "Tell the user their account setup is incomplete and they should contact support "
                    + "instead of saying no transactions were found.";

        } catch (FeignException ex) {

            log.error("Failed to fetch transactions for authUserId={}", authUserId, ex);

            return "ERROR: The transaction service is temporarily unavailable. "
                    + "Tell the user to try again shortly instead of saying no transactions were found.";

        } catch (Exception ex) {

            log.error("Failed to serialize transactions for authUserId={}", authUserId, ex);

            return "ERROR: Something went wrong reading transaction data. Tell the user to try again shortly.";

        }

    }

}