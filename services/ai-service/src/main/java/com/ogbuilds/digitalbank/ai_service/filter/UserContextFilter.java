package com.ogbuilds.digitalbank.ai_service.filter;

import com.ogbuilds.digitalbank.ai_service.util.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        String userId =
                httpRequest.getHeader("X-User-Id");

        try {

            if (userId != null) {

                UserContext.setUserId(
                        Long.parseLong(userId)
                );

            }

            chain.doFilter(request, response);

        } finally {

            UserContext.clear();

        }

    }

}