package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.ingress.mock.MockUrlSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static com.kroger.ngpp.common.logging.RegularPriceOptimizationServiceLogKeys.OPTIMIZED_DELIVERY_INVOCATION_SUCCESS;

@Configuration
public class OptimizedDeliveryServiceConfig {

    @Autowired
    IRegularPriceOptimizationLogger logger;

    @Bean
    @ConditionalOnProperty(name = "rpo.optimizedDelivery.mock.enabled", havingValue = "true")
    OptimizedDeliveryServiceInvoker mockService(
            @Value("${rpo.optimizedDelivery.mock.urls:https://test.kroger.com}") String[] urls
    ) {
        logger.sendMessage("INFO",
                "Provided OptimizedDeliveryServiceInvoker is MockSingleUrlSuccess",
                OptimizedDeliveryServiceConfig.class.getSimpleName(), new HashMap<>()
        );
        return new MockUrlSuccess(urls);
    }

    @Bean
    @ConditionalOnProperty(name = "rpo.optimizedDelivery.mock.enabled", matchIfMissing = true, havingValue = "false")
    OptimizedDeliveryServiceInvoker service() {
        logger.sendMessage("INFO",
                "Provided OptimizedDeliveryServiceInvoker is DefaultOptimizedDeliveryServiceInvoker",
                OptimizedDeliveryServiceConfig.class.getSimpleName(), new HashMap<>()
        );
        return new DefaultOptimizedDeliveryServiceInvoker();
    }

}
