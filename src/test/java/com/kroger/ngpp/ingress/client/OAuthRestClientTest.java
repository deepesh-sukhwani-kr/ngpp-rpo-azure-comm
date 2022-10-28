package com.kroger.ngpp.ingress.client;

import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@ExtendWith(MockitoExtension.class)
class OAuthRestClientTest {

    private OptimizedDeliveryModel model;

    @Mock
    OAuth2RestTemplate template;

    @InjectMocks
    OAuthRestClient client;

    @Before
    void setUp() {
        client = new OAuthRestClient(template);
        model = new OptimizedDeliveryModel(Mockito.anyList());
    }

    @Test
    void invokeGetOperation() {
        Mockito.when(client.invokeGetOperation("URL", OptimizedDeliveryModel.class))
                .thenReturn(new ResponseEntity(model, HttpStatus.OK));

        ResponseEntity<OptimizedDeliveryModel> response = client.invokeGetOperation("URL",
                OptimizedDeliveryModel.class);
        Assert.assertEquals(model, response.getBody());
    }
}
