package com.ogbuilds.digitalbank.gateway.filter;

import com.ogbuilds.digitalbank.gateway.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.startsWith("/auth/login") || path.startsWith("/auth/register") || path.startsWith("/actuator")) {

            return chain.filter(exchange);

        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();

        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();

        }

        ServerHttpRequest request = exchange.getRequest().mutate()

                .header("X-User-Id", String.valueOf(jwtService.extractUserId(token)))

                .header("X-User-Email", jwtService.extractUsername(token))

                .header("X-User-Role", jwtService.extractRole(token))

                .build();

        return chain.filter(exchange.mutate().request(request).build());

    }

}