package com.kroger.ngpp.ingress.client.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;

import static com.kroger.ngpp.testutils.SimpleUtils.getRPOparam;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SimpleUrlBuilder.class)
public class SimpleUrlBuilderTest {

    @InjectMocks
    SimpleUrlBuilder urlBuilder;

    private final String expectedUrlError = "Input url String is Null or Empty";
    private static final String mockURL
            = "https://b2bservices-stage.kroger.com/stg-internal-8451/settings-management-service/api/v1/transform/rpo";
    private static final String expected
            = "https://b2bservices-stage.kroger.com/stg-internal-8451/settings-management-service/api/v1/transform/rpo?execution_date=2023-01-01&run_type=objective&snapshot=true";
    private static final String mockSASUrl
            = "https://sa8451pricepromotst.blob.core.windows.net/optimization/output/delivery/rpo/batch_id=fd296702-a438-47d6-b780-845862a98493/part-00000-75ffcf6a-66d4-484b-a4e6-1637667b9131-c000.json?se=2021-12-29T00%3A34%3A16Z&sp=rt&sv=2020-10-02&sr=b&sig=Sq%2B/zeExW7zxz1Y64D80Xasadmcbbv8%2Bi83mBgnawAY%3D";
    private static final String expectedSASUrl
            = "https://sa8451pricepromotst.blob.core.windows.net/optimization/output/delivery/rpo/batch_id=fd296702-a438-47d6-b780-845862a98493/part-00000-75ffcf6a-66d4-484b-a4e6-1637667b9131-c000.json?se=2021-12-29T00:34:16Z&sp=rt&sv=2020-10-02&sr=b&sig=Sq+/zeExW7zxz1Y64D80Xasadmcbbv8+i83mBgnawAY=";
    private static final String expectedMapString
            = "{execution_date=[2023-01-01], run_type=[objective], snapshot=[true]}";
    private static final String expectedMockWithNullMap
            = "https://b2bservices-stage.kroger.com/stg-internal-8451/settings-management-service/api/v1/transform/rpo";

    @Test
    public void testSimpleControllerUrlBuilderWithMock() {
        String actual = urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(getRPOparam()), mockURL);
        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleControllerUrlBuilderWithMockSASUrl() {
        String actual = urlBuilder.decode(mockSASUrl);
        assertEquals(expectedSASUrl, actual);
    }

    @Test
    public void testSimpleControllerUrlBuilderWithMockNullMap() {
        String actual = urlBuilder.buildIngresControllerURL(null, mockURL);
        assertEquals(expectedMockWithNullMap, actual);
    }

    @Test
    public void testIngresControllerUrlBuilderWithNull() {
        NullPointerException actual = Assertions.assertThrows(NullPointerException.class,
                () -> urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(getRPOparam()), null));
        assertEquals(expectedUrlError, actual.getLocalizedMessage());
    }

    @Test
    public void testIngresControllerUrlBuilderWithEmptyString() {
        NullPointerException actual = Assertions.assertThrows(NullPointerException.class,
                () -> urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(getRPOparam()), ""));
        assertEquals(expectedUrlError, actual.getLocalizedMessage());
    }

    @Test
    public void testIngresControllerUrlBuilderWithBlankString() {
        NullPointerException actual = Assertions.assertThrows(NullPointerException.class,
                () -> urlBuilder.buildIngresControllerURL(urlBuilder.mapQueryParameters(getRPOparam()), ""));
        assertEquals(expectedUrlError, actual.getLocalizedMessage());
    }

    @Test
    public void testUrlBuilderMultiValueMap() {
        MultiValueMap<String, String> queryParameters = urlBuilder.mapQueryParameters(getRPOparam());
        assertEquals(expectedMapString, queryParameters.toString());
    }

    @Test
    public void testUrlBuilderMultiValueMapNullObject() {
        MultiValueMap<String, String> queryParameters = urlBuilder.mapQueryParameters(null);
        assertNull(queryParameters);
    }

}
