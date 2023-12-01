package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.ngpp.ingress.model.RpoSmsParam;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import org.springframework.http.ResponseEntity;

public interface SimpleServiceInvoker {

    ResponseEntity<SimpleDeliveryModel> getSimplicityResponse(RpoSmsParam model);

}
