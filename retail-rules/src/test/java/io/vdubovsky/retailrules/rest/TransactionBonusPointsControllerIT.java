package io.vdubovsky.retailrules.rest;

import io.vdubovsky.retailrules.RetailRulesApplication;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.charset.Charset;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RetailRulesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionBonusPointsControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @ValueSource(strings = {"amount-missing", "executed-at-missing", "id-missing", "period-violation", "amount-missing"})
    @ParameterizedTest
    public void testBadRequestScenario(String scenarioName) throws Exception {
        // Given
        String requestFileName = "request-" +scenarioName +".json";
        String responseFileName = "response-" +scenarioName +".json";
        HttpEntity<String> entity = new HttpEntity<>(getFromFile(requestFileName), getPostHeaders());

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/transactions/bonuses"),
                HttpMethod.POST, entity, String.class);

        // Then
        JSONAssert.assertEquals(getFromFile(responseFileName), response.getBody(), false);
    }

    @ValueSource(strings = {"all-transaction-lower-min-sum", "some-transaction-lower-min-sum", "one-user", "multiple-users" })
    @ParameterizedTest
    public void testSuccessScenario(String scenarioName) throws Exception {
        // Given
        String requestFileName = "request-" +scenarioName +".json";
        String responseFileName = "response-" +scenarioName +".json";
        HttpEntity<String> entity = new HttpEntity<>(getFromFile(requestFileName), getPostHeaders());

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/transactions/bonuses"),
                HttpMethod.POST, entity, String.class);

        // Then
        JSONAssert.assertEquals(getFromFile(responseFileName), response.getBody(), false);
    }

    private String getFromFile(String fileName) throws Exception {
        File mock = new File(getClass().getClassLoader().getResource("mock").getPath(), fileName);
        return IOUtils.toString(mock.toURI(), Charset.defaultCharset());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private HttpHeaders getPostHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        return headers;
    }
}
