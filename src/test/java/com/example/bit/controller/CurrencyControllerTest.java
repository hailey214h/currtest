package com.example.bit.controller;

import com.example.bit.model.req.CurrencyBaseReq;
import com.example.bit.model.req.CurrencyReq;
import com.example.bit.model.res.CurrencyRes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("CurrencyController Test")
public class CurrencyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/coin/currency";
    }

    @Test
    @Order(1)
    @DisplayName("POST /addCurr - Add Currency")
    void testAddCurrency() throws JsonProcessingException {
        CurrencyReq req = new CurrencyReq();
        req.setCode("TWD");
        req.setName("台幣");
        req.setSymbol("NT$");
        req.setExchangeRate(new BigDecimal("30.1234"));

        ResponseEntity<CurrencyRes> response = restTemplate.postForEntity(baseUrl + "/addCurr", req, CurrencyRes.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("TWD", response.getBody().getCode());

        showResult(response.getBody());

    }

    @Test
    @Order(2)
    @DisplayName("GET /getAllCurrList - List All Currencies")
    void testGetAllCurrencies() throws JsonProcessingException {
        ResponseEntity<CurrencyRes[]> response = restTemplate.getForEntity(baseUrl + "/getAllCurrList",CurrencyRes[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().length >= 1);

        showResult(response.getBody());

    }

    @Test
    @Order(3)
    @DisplayName("POST /getCurr - Get Currency by Code")
    void testGetCurrency() throws JsonProcessingException {
        CurrencyBaseReq req = new CurrencyBaseReq();
        req.setCode("TWD");

        ResponseEntity<CurrencyRes> response = restTemplate.postForEntity(baseUrl + "/getCurr", req, CurrencyRes.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("TWD", response.getBody().getCode());

        showResult(response.getBody());

    }

    @Test
    @Order(4)
    @DisplayName("POST /updateCurr - Update Currency")
    void testUpdateCurrency() throws JsonProcessingException {
        CurrencyReq req = new CurrencyReq();
        req.setCode("TWD");
        req.setName("新台幣");
        req.setSymbol("NTD");
        req.setExchangeRate(new BigDecimal("31.4567"));

        ResponseEntity<CurrencyRes> response = restTemplate.postForEntity(baseUrl + "/updateCurr", req, CurrencyRes.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("新台幣", response.getBody().getName());

        showResult(response.getBody());

    }

    @Test
    @Order(5)
    @DisplayName("POST /deleteCurr - Delete Currency")
    void testDeleteCurrency() throws JsonProcessingException {
        CurrencyBaseReq req = new CurrencyBaseReq();
        req.setCode("TWD");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CurrencyBaseReq> entity = new HttpEntity<>(req, headers);

        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/deleteCurr",HttpMethod.POST,entity,Void.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        showResult(response.getBody());

    }

    /**
     * 報錯
     */
    @Test
    @Order(6)
    @DisplayName("POST /addCurr - Add Currency valid exchangeRate format")
    void testGetCurrencyBadReqForNotBlank() throws JsonProcessingException {
        CurrencyReq req = new CurrencyReq();
        req.setCode("TWD");
        req.setName("台幣");
        req.setSymbol("NT$");
        req.setExchangeRate(new BigDecimal("30.123456"));

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/addCurr", req, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertThat(response.getBody()).contains("exchangeRate must be a valid decimal number with up to 10 digits");

        showResult(response.getBody());

    }

    @Test
    @Order(7)
    @DisplayName("POST /getCurr - Get Currency by Code valid not blank")
    void testAddCurrencyBadReqForRateFormat() throws JsonProcessingException {
        CurrencyBaseReq req = new CurrencyBaseReq();
        req.setCode("");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/getCurr", req, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertThat(response.getBody()).contains("code cannot be blank");

        showResult(response.getBody());

    }

    private void showResult(Object res) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("Response JSON:" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
    }
}
