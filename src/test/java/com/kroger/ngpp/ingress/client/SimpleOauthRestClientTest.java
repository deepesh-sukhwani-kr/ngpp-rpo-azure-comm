package com.kroger.ngpp.ingress.client;

import com.kroger.ngpp.ingress.model.SimpleDeliveryModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
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
public class SimpleOauthRestClientTest {

    @Mock
    SimpleDeliveryModel model;

    @Mock
    OAuth2RestTemplate template;

    @InjectMocks
    SimpleOauthRestClient client;

    @BeforeEach
    void setUp() {
        client = new SimpleOauthRestClient(template);
        model = new SimpleDeliveryModel(Mockito.anyList());
    }

    @Test
    void invokeGetOperation() {
        Mockito.when(client.invokeGetOperation("URL",SimpleDeliveryModel.class))
                .thenReturn(new ResponseEntity(model, HttpStatus.OK));
        ResponseEntity<SimpleDeliveryModel> response = client.invokeGetOperation("URL",
                SimpleDeliveryModel.class);
        Assert.assertEquals(model, response.getBody());
    }

}
