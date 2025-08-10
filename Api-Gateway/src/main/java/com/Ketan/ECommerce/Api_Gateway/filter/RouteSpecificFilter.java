package com.Ketan.ECommerce.Api_Gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RouteSpecificFilter extends AbstractGatewayFilterFactory<RouteSpecificFilter.Config> {

    public RouteSpecificFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(RouteSpecificFilter.Config config) {
        return (exchange , chain)->{
            log.info("Logging from a route specific filter : {}" , exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }
    public static class Config{

    }
}
