package com.ogbuilds.digitalbank.ai_service.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogbuilds.digitalbank.ai_service.client.CustomerClient;
import com.ogbuilds.digitalbank.ai_service.util.UserContext;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerTools {

    private final CustomerClient customerClient;
    private final ObjectMapper objectMapper;

    @Tool(description = """
            Returns the profile of the currently authenticated customer,
            including their name, phone number, date of birth, and gender.

            Use this whenever the user asks:
            - who am I
            - what is my name
            - my profile / my details
            - is my account verified
            """)
    public String getMyProfile() {

        Long authUserId = UserContext.getUserId();

        try {

            var profile = customerClient
                    .getCustomerByAuthUserId(authUserId)
                    .getData();

            return objectMapper.writeValueAsString(profile);

        } catch (FeignException.NotFound ex) {

            log.warn("No customer profile linked to authUserId={}", authUserId);

            return "ERROR: This user has no customer profile set up yet. "
                    + "Tell the user their account setup is incomplete and they should contact support "
                    + "instead of saying you don't know who they are.";

        } catch (FeignException ex) {

            log.error("Failed to fetch profile for authUserId={}", authUserId, ex);

            return "ERROR: The customer service is temporarily unavailable. "
                    + "Tell the user to try again shortly.";

        } catch (Exception ex) {

            log.error("Failed to serialize profile for authUserId={}", authUserId, ex);

            return "ERROR: Something went wrong reading profile data. Tell the user to try again shortly.";

        }

    }

}