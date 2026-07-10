package com.ogbuilds.digitalbank.gateway.filter;

import com.ogbuilds.digitalbank.gateway.util.CorrelationIdUtil;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {

    public static final String HEADER = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(org.springframework.web.server.ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String correlationId = CorrelationIdUtil.generate();

        exchange = exchange.mutate()

                .request(builder -> builder.header(
                        HEADER,
                        correlationId))

                .build();

        exchange.getResponse()
                .getHeaders()
                .add(HEADER, correlationId);

        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {

        return -2;

    }

}