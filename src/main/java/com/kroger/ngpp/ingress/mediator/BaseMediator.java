package com.kroger.ngpp.ingress.mediator;

import com.kroger.dcp.echoclient.EventType;
import com.kroger.desp.events.effo.data.ready.EffoDataReady;
import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.common.utils.OptimizedPriceUtil;
import com.kroger.ngpp.ingress.client.invoker.OptimizedDeliveryServiceInvoker;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.BATCH_ID;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.RUN_TYPE;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.SERVICE_LOG_KEY;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationServiceLogKeys.DATA_READY_MESSAGE_VALIDATION_FAILED;
import static com.kroger.ngpp.common.utils.OptimizedPriceConstants.BATCH_INPUT_VALIDATION_MESSAGE;
import static com.kroger.ngpp.common.utils.OptimizedPriceConstants.INGRESS_INVOCATION_MESSAGE;
import static com.kroger.ngpp.common.utils.OptimizedPriceConstants.OAUTH_CREDENTIAL_MESSAGE;

public abstract class BaseMediator {

    protected OptimizedDeliveryServiceInvoker serviceInvoker;
    protected OptimizedPriceUtil utils;
    protected IRegularPriceOptimizationLogger logger;

    public BaseMediator(OptimizedDeliveryServiceInvoker serviceInvoker, OptimizedPriceUtil utils, IRegularPriceOptimizationLogger logger) {
        this.serviceInvoker = serviceInvoker;
        this.utils = utils;
        this.logger = logger;
    }

    public abstract void processRegularPriceOptimization(EffoDataReady dataReady);

    public abstract String getType();

    protected ResponseEntity<OptimizedDeliveryModel> getIngressResponse(EffoDataReady dataReady) {
        ResponseEntity<OptimizedDeliveryModel> response;
        if(!utils.isDataReadyMessageValid(dataReady)) {
            logger.sendMessage(EventType.INFO,
                    BATCH_INPUT_VALIDATION_MESSAGE +dataReady,
                    DATA_READY_MESSAGE_VALIDATION_FAILED,
                    BaseMediator.class.getSimpleName(), new HashMap<>() {{
                        put(SERVICE_LOG_KEY.asString(), DATA_READY_MESSAGE_VALIDATION_FAILED.getText());
                        put(RUN_TYPE.asString(), dataReady.getPayload().getRunType());
                        put(BATCH_ID.asString(), dataReady.getPayload().getBatchId());
                    }}
            );
            throw new RuntimeException(BATCH_INPUT_VALIDATION_MESSAGE +dataReady);
        }
        try {
            response = serviceInvoker.getOptimizedDeliveryResponse(dataReady);
        } catch (NullPointerException ne) {
            throw new RuntimeException(OAUTH_CREDENTIAL_MESSAGE +ne);
        } catch (Exception e) {
            throw new RuntimeException(INGRESS_INVOCATION_MESSAGE +e);
        }
        return response;
    }

}
