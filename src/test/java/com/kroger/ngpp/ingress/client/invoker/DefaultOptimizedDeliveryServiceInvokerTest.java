package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.boot.autoconfigure.security.oauth.WebClientWrapper;
import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.ingress.client.builder.UrlBuilder;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import com.kroger.ngpp.testutils.Utils;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultOptimizedDeliveryServiceInvokerTest {

    private OptimizedDeliveryModel optimizedDeliveryModel;


    @InjectMocks
    DefaultOptimizedDeliveryServiceInvoker invoker;

    @Mock
    UrlBuilder builder;

    @Mock
    IRegularPriceOptimizationLogger logger;

    @Mock
    private WebClientWrapper webClientWrapper;

    private Object NullPointerException;

    @BeforeEach
    public void setUp() {
        optimizedDeliveryModel = new OptimizedDeliveryModel(
                Arrays.asList("First message", "Second message", "Third message"));
    }

    @Test
    void getOptimizedDeliveryResponseTest() throws Exception {
        EffoDataReady mock = Utils.mockBatchOutput();
        when(builder.buildIngresControllerURL(builder.mapQueryParameters(mock), null))
                .thenReturn("test");
        optimizedDeliveryModel = new OptimizedDeliveryModel(
                Arrays.asList("First message", "Second message", "Third message"));

        Mono<OptimizedDeliveryModel> mono = Mono.just(optimizedDeliveryModel);
        String clientResistrationId = "optimization-service";
        when(webClientWrapper.GET(clientResistrationId,
                "test", OptimizedDeliveryModel.class)).thenReturn(mono);
        OptimizedDeliveryModel response = invoker.getOptimizedDeliveryResponse(mock);
        Assertions.assertEquals(optimizedDeliveryModel, response);
    }

    @Test
    void testInvalidtOptimizedDeliveryResponse() {
        when(builder.buildIngresControllerURL(builder.mapQueryParameters(null), null))
                .thenThrow(new NullPointerException());
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> invoker.getOptimizedDeliveryResponse(null));
        assertNull(exception.getMessage());
    }
}
