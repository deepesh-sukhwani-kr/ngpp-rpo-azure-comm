package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.springframework.http.ResponseEntity;

public interface OptimizedDeliveryServiceInvoker {

    ResponseEntity<OptimizedDeliveryModel> getOptimizedDeliveryResponse(EffoDataReady model);

}
