package com.kroger.ngpp.ingress.mediator;

import com.kroger.ngpp.ingress.client.invoker.SimpleServiceInvoker;
import com.kroger.ngpp.ingress.model.RpoSmsParam;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class SimpleMediator {

    protected SimpleServiceInvoker serviceInvoker;

    public SimpleMediator(SimpleServiceInvoker serviceInvoker) {
        this.serviceInvoker = serviceInvoker;
    }


    protected ResponseEntity<SimpleDeliveryModel> getSettingsResponse(RpoSmsParam model) {
        ResponseEntity<SimpleDeliveryModel> response;
        try {
            response = serviceInvoker.getSimplicityResponse(model);
        } catch (NullPointerException nex) {
            throw new RuntimeException("Nullpointer exception while invoking the service " + nex);
        } catch (Exception ex) {
            throw new RuntimeException("Exception while execution " + ex);
        }
        return response;
    }
    public abstract String getType();

}
