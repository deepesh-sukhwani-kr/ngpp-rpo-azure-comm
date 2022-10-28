package com.kroger.ngpp.ingress.mock;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.ingress.client.invoker.OptimizedDeliveryServiceInvoker;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class MockUrlSuccess implements OptimizedDeliveryServiceInvoker {

    private final String[] urls;

    public MockUrlSuccess(String[] urls) {
        this.urls = urls;
    }

    @Override
    public ResponseEntity<OptimizedDeliveryModel> getOptimizedDeliveryResponse(
            EffoDataReady model) {
        OptimizedDeliveryModel response = OptimizedDeliveryModel.builder()
                .blobUrlList(Arrays.asList(urls))
                .build();
        return ResponseEntity.ok(response);
    }
}
