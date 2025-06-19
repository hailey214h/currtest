package com.example.bit.controller;

import com.example.bit.model.res.CoinDeskRes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoinDeskControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/coin/coinDesk/getAllCoinDesk";
    }

    @Test
    @DisplayName("GET /coin/coinDesk/getAllCoinDesk - should return valid CoinDeskInfoRes")
    void testGetCoinDeskData() throws JsonProcessingException {
        // 執行 GET 請求
        ResponseEntity<CoinDeskRes> response = restTemplate.getForEntity(baseUrl, CoinDeskRes.class);

        // 驗證 HTTP 狀態
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        // 驗證返回資料欄位
        CoinDeskRes res = response.getBody();
        Assertions.assertNotNull(res);
        Assertions.assertNotNull(res.getChartName(), "chartName should not be null");
        Assertions.assertNotNull(res.getUpdatedTime(), "updatedTime should not be null");
        Assertions.assertFalse(res.getCurrencyList().isEmpty(), "currencyList should not be empty");

        showResult(response.getBody());
    }

    private void showResult(Object res) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("Response JSON:" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
    }
}
