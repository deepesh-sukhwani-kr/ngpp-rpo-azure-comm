package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;

public interface OptimizedDeliveryServiceInvoker {

    OptimizedDeliveryModel getOptimizedDeliveryResponse(EffoDataReady model);

}
