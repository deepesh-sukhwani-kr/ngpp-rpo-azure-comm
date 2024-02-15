package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.boot.autoconfigure.security.oauth.WebClientWrapper;
import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.ingress.client.builder.UrlBuilder;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.BATCH_ID;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.RUN_TYPE;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.SERVICE_LOG_KEY;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationServiceLogKeys.OPTIMIZED_DELIVERY_RESPONSE;

public class DefaultOptimizedDeliveryServiceInvoker implements OptimizedDeliveryServiceInvoker {

    @Value("${rpo.ingress-controller.url: test}")
    private String urlString;

    @Autowired
    private WebClientWrapper webClient;

    @Autowired
    private UrlBuilder builder;

    @Autowired
    IRegularPriceOptimizationLogger logger;

    @Override
    public OptimizedDeliveryModel getOptimizedDeliveryResponse(
            EffoDataReady model) {
        String qualifiedUrl = builder.buildIngresControllerURL(
                builder.mapQueryParameters(model),
                urlString
        );
        logger.sendMessage("INFO", "OptimizedDeliveryServiceInvoker##  batchId =  " + model.getPayload().getBatchId()
                        + " runType = " + model.getPayload().getRunType() + ", QualifiedURL: " + qualifiedUrl,
                 DefaultOptimizedDeliveryServiceInvoker.class.getSimpleName(), new HashMap<>() {{
                    put(SERVICE_LOG_KEY.asString(), OPTIMIZED_DELIVERY_RESPONSE.getText());
                    put(BATCH_ID.asString(), model.getPayload().getBatchId());
                    put(RUN_TYPE.asString(), model.getPayload().getRunType());
                }}
        );
        OptimizedDeliveryModel response = webClient.GET("optimizationService",
                qualifiedUrl, OptimizedDeliveryModel.class).block();
        return response;
    }

}
