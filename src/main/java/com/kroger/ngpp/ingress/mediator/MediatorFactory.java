package com.kroger.ngpp.ingress.mediator;

import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.common.utils.OptimizedPriceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kroger.ngpp.common.logging.RegularPriceOptimizationLogFieldType.SERVICE_LOG_KEY;
import static com.kroger.ngpp.common.logging.RegularPriceOptimizationServiceLogKeys.MEDIATOR_FACTORY_RUNTYPE_CHECK_FAILED;
import static com.kroger.ngpp.common.utils.OptimizedPriceConstants.ECHO_CHECKPOINT;

@Component
public class MediatorFactory {
    private static final Map<String, BaseMediator> mediatorsCache = new HashMap<>();
    private static IRegularPriceOptimizationLogger logger;

    @Autowired
    private MediatorFactory(List<BaseMediator> mediators) {
        for (BaseMediator mediator : mediators) {
            mediatorsCache.put(mediator.getType(), mediator);
        }
    }

    public static BaseMediator getMediator(String type, IRegularPriceOptimizationLogger log) {
        logger = log;
        if (ObjectUtils.isEmpty(type)) type = OptimizedPriceConstants.INMARKET_RUN_TYPE;
        BaseMediator mediator = mediatorsCache.get(type);
        if (mediator == null) {
            String errorMessage = ECHO_CHECKPOINT + "Unknown run type: " + type;
            logger.sendMessage("ERROR",
                    "Error: " + errorMessage,
                    MediatorFactory.class.getSimpleName(), null
            );
            throw new RuntimeException(errorMessage);
        }
        return mediator;
    }

}
