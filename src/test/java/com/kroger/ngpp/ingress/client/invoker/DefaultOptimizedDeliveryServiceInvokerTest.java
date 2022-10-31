package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.ingress.client.OAuthRestClient;
import com.kroger.ngpp.ingress.client.builder.UrlBuilder;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import com.kroger.ngpp.testutils.Utils;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultOptimizedDeliveryServiceInvokerTest {

    private OptimizedDeliveryModel optimizedDeliveryModel;

    @Mock
    OAuthRestClient client;

    @InjectMocks
    DefaultOptimizedDeliveryServiceInvoker invoker;

    @Mock
    UrlBuilder builder;

    @Mock
    IRegularPriceOptimizationLogger logger;

    private Object NullPointerException;

    @Before
    void setUp() {
        invoker = new DefaultOptimizedDeliveryServiceInvoker();
        optimizedDeliveryModel = new OptimizedDeliveryModel(
                Arrays.asList("First message", "Second message", "Third message"));
    }

    @Test
    void getOptimizedDeliveryResponseTest() throws Exception {
        EffoDataReady mock = Utils.mockBatchOutput();
        when(builder.buildIngresControllerURL(builder.mapQueryParameters(mock), null))
                .thenReturn("test");
        when(invoker.getOptimizedDeliveryResponse(mock))
                .thenReturn(new ResponseEntity<>(optimizedDeliveryModel, HttpStatus.OK));
        ResponseEntity<OptimizedDeliveryModel> response = invoker.getOptimizedDeliveryResponse(mock);
        Assert.assertEquals(optimizedDeliveryModel, response.getBody());
    }

    @Test
    void testInvalidtOptimizedDeliveryResponse() {
        when(builder.buildIngresControllerURL(builder.mapQueryParameters(null), null))
                .thenThrow(NullPointerException.class);
        val exception =   Assertions.assertThrows(Exception.class, () ->invoker.getOptimizedDeliveryResponse(null));
        assertEquals(NullPointerException, exception.getLocalizedMessage());
    }
}
