package com.kroger.ngpp.ingress.mapper;

import com.kroger.ngpp.common.logging.IRegularPriceOptimizationLogger;
import com.kroger.ngpp.common.logging.RegularPriceOptimizationLocalLogger;
import com.kroger.ngpp.ingress.model.OptimizedDeliveryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HttpResponseMapper.class})
class HttpResponseMapperTest {

    private OptimizedDeliveryModel model;

    @MockBean
    HttpResponseMapper              responseMapper;
    @Mock
    IRegularPriceOptimizationLogger logger = new RegularPriceOptimizationLocalLogger();

    @BeforeEach
    public void setUp() {
        responseMapper = new HttpResponseMapper(logger);
        List<String> arrayList = Arrays.asList("First message", "Second message", "Third message");
        model = new OptimizedDeliveryModel(arrayList);
    }

    @Test
    public void whenMapResponseBody() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.OK);
        OptimizedDeliveryModel rModel = (OptimizedDeliveryModel) responseMapper.mapResponseBody(response);

        assertEquals(response.getBody(), rModel);
    }

    @Test
    public void whenValidateResponseIsOk() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.OK);
        Boolean b = responseMapper.validateResponseIsOk(response);

        assertEquals(true, b);
    }

    @Test
    public void whenValidateResponseIsNotOk() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.NOT_FOUND);
        Boolean b = responseMapper.validateResponseIsOk(response);

        assertEquals(false, b);
    }

    @Test
    public void whenValidateResponseIsCreated() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.CREATED);
        Boolean b = responseMapper.validateResponseIsCreated(response);

        assertEquals(true, b);
    }

    @Test
    public void whenValidateResponseIsNotCreated() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.NOT_FOUND);
        Boolean b = responseMapper.validateResponseIsCreated(response);

        assertEquals(false, b);
    }

    @Test
    public void whenValidateResponseHasNoContent() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.NO_CONTENT);
        Boolean b = responseMapper.validateResponseHasNoContent(response);

        assertEquals(true, b);
    }

    @Test
    public void whenValidateResponseHasContent() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.NOT_FOUND);
        Boolean b = responseMapper.validateResponseHasNoContent(response);

        assertEquals(false, b);
    }

    @Test
    public void whenValidateResponseIsAccepted() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.ACCEPTED);
        Boolean b = responseMapper.validateResponseIsAccepted(response);

        assertEquals(true, b);
    }

    @Test
    public void whenValidateResponseIsNotAccepted() {
        ResponseEntity response = new ResponseEntity<>(model, HttpStatus.NOT_FOUND);
        Boolean b = responseMapper.validateResponseIsAccepted(response);

        assertEquals(false, b);
    }

}
