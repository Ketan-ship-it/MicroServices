package com.Ketan.ECommerce.Api_Gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ExampleFilter implements GlobalFilter , Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Logging from Global Pre filter :{}" , exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            log.info("Logging from Global Post filter : {}" , exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        return 6;
    }
}
