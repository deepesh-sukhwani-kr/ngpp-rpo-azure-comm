package com.kroger.ngpp.ingress.client.builder;

import com.kroger.ngpp.testutils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UrlBuilder.class})
public class UrlBuilderTest {

    @Autowired
    UrlBuilder urlBuilder;

    Utils utils;

    private static final String mockURL = "https://b2bservices-stage.kroger.com/dev-aks-8451/optimization-delivery-api-dev/api/v1/optimization/sas";
    private static final String expected = "https://b2bservices-stage.kroger.com/dev-aks-8451/optimization-delivery-api-dev/api/v1/optimization/sas?science=rpo&batch_id=80414c93-6d59-4dc5-a277-fed0fb30d09d";
    private static final String mockSASUrl = "https://sa8451pricepromotst.blob.core.windows.net/optimization/output/delivery/rpo/batch_id=fd296702-a438-47d6-b780-845862a98493/part-00000-75ffcf6a-66d4-484b-a4e6-1637667b9131-c000.json?se=2021-12-29T00%3A34%3A16Z&sp=rt&sv=2020-10-02&sr=b&sig=Sq%2B/zeExW7zxz1Y64D80Xasadmcbbv8%2Bi83mBgnawAY%3D";
    private static final String expectedSASUrl = "https://sa8451pricepromotst.blob.core.windows.net/optimization/output/delivery/rpo/batch_id=fd296702-a438-47d6-b780-845862a98493/part-00000-75ffcf6a-66d4-484b-a4e6-1637667b9131-c000.json?se=2021-12-29T00:34:16Z&sp=rt&sv=2020-10-02&sr=b&sig=Sq+/zeExW7zxz1Y64D80Xasadmcbbv8+i83mBgnawAY=";
    private static final String expectedMapString = "{science=[rpo], batch_id=[80414c93-6d59-4dc5-a277-fed0fb30d09d]}";
    private static final String expectedMockWithNullMap = "https://b2bservices-stage.kroger.com/dev-aks-8451/optimization-delivery-api-dev/api/v1/optimization/sas";

    @Test
    public void testIngresControllerUrlBuilderWithMock() throws Exception {
        String actual = urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(utils.mockBatchOutput()), mockURL);
        assertEquals(expected, actual);
    }

    @Test
    public void testIngresControllerUrlBuilderWithMockSASUrl() throws Exception {
        String actual = urlBuilder.decode(mockSASUrl);
        assertEquals(expectedSASUrl, actual);
    }

    @Test
    public void testIngresControllerUrlBuilderWithMockNullMap() throws Exception {
        String actual = urlBuilder.buildIngresControllerURL(null, mockURL);
        assertEquals(expectedMockWithNullMap, actual);
    }

    @Test
    public void testIngresControllerUrlBuilderWithNull() throws Exception {
        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(utils.mockBatchOutput()), null));
        assertEquals("Input url String is Null or Empty", actual.getLocalizedMessage());
    }

    @Test
    public void testIngresControllerUrlBuilderWithEmptyString() throws Exception {
        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(utils.mockBatchOutput()), ""));
        assertEquals("Input url String is Null or Empty", actual.getLocalizedMessage());
    }

    @Test
    public void testIngresControllerUrlBuilderWithBlankString() throws Exception {
        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(utils.mockBatchOutput()), ""));
        assertEquals("Input url String is Null or Empty", actual.getLocalizedMessage());
    }

    @Test
    public void testUrlBuilderMultiValueMap() {
        MultiValueMap<String, String> queryParameters = urlBuilder.mapQueryParameters(utils.mockBatchOutput());
        assertEquals(expectedMapString, queryParameters.toString());
    }

    @Test
    public void testUrlBuilderMultiValueMapNullObject() {
        MultiValueMap<String, String> queryParameters = urlBuilder.mapQueryParameters(null);
        assertEquals(null, queryParameters);
    }

}
