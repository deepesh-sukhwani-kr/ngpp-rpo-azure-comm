package com.kroger.ngpp.ingress.client.invoker;

import com.kroger.ngpp.ingress.client.SimpleOauthRestClient;
import com.kroger.ngpp.ingress.client.builder.SimpleUrlBuilder;
import com.kroger.ngpp.ingress.model.RpoSmsParam;
import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import com.kroger.ngpp.ingress.model.SimpleModel;
import com.kroger.ngpp.testutils.SimpleUtils;
import lombok.val;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimplicityServiceInvokerTest {

    SimpleDeliveryModel simpleDeliveryModel = new SimpleDeliveryModel();
    SimpleModel simpleModel = new SimpleModel();

    @Mock
    SimpleOauthRestClient client;

    @InjectMocks
    SimplicityServiceInvoker invoker;

    @Mock
    SimpleUrlBuilder urlBuilder;

    private Object NullPointerException;

    @Before
     void setUp() {
        invoker = new SimplicityServiceInvoker(client,urlBuilder);
        simpleModel = new SimpleModel("primaryZone1",
                "premium_zone_exempt_programs","primary_zone","cats");
        simpleDeliveryModel = new SimpleDeliveryModel(List.of(simpleModel));
       simpleModel =  SimpleModel.builder()
               .primaryZone("primaryZone1")
               .premiumZoneExemptPrograms("premium_zone_exempt_programs")
               .premiumZones("primary_zone")
               .build();
    }

    @Test
    void getSimplicityResponseTest() {
        RpoSmsParam param = SimpleUtils.getRPOparam();
        when(urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(param), null))
                .thenReturn("test");
        when(invoker.getSimplicityResponse(param))
                .thenReturn(new ResponseEntity<>(simpleDeliveryModel, HttpStatus.OK));
        ResponseEntity<SimpleDeliveryModel> response = invoker.getSimplicityResponse(param);
        assertEquals(simpleDeliveryModel, response.getBody());
    }

    @Test
    void testInvalidSimplicityResponse() {
        simpleDeliveryModel = SimpleDeliveryModel.builder().build();
        when(urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(null), null))
                .thenThrow(NullPointerException.class);
        val exception =   Assertions.assertThrows(Exception.class, () ->invoker.getSimplicityResponse(null));
        assertEquals(NullPointerException, exception.getLocalizedMessage());
    }

}
