package com.example.bit.service;

import com.example.bit.constant.CoinDeskConst;
import com.example.bit.gateway.coindesk.CoinDeskClient;
import com.example.bit.gateway.coindesk.CoinRes;
import com.example.bit.model.res.CoinDeskRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoinDeskServiceTest {

    @Mock
    private CoinDeskClient coinDeskClient;

    @InjectMocks
    private CoinDeskService coinDeskService;

    @Test
    public void testFindAll() {
        // 取得mock資料
        CoinRes coinRes = new CoinRes();

        CoinRes.TimeData timeData = new CoinRes.TimeData();
        timeData.setUpdatedISO("2024-09-02T07:07:20+00:00");

        Map<String, CoinRes.Currency> bpiMap = new HashMap<>();
        CoinRes.Currency usd = new CoinRes.Currency();
        usd.setCode("USD");
        usd.setSymbol("&#36;");
        usd.setRate("57,756.298");
        usd.setDescription("United States Dollar");
        usd.setRateFloat(BigDecimal.valueOf(57756.2984));

        CoinRes.Currency gbp = new CoinRes.Currency();
        gbp.setCode("GBP");
        gbp.setSymbol("&pound;");
        gbp.setRate("43,984.02");
        gbp.setDescription("British Pound Sterling");
        gbp.setRateFloat(BigDecimal.valueOf(43984.0203));

        CoinRes.Currency eur = new CoinRes.Currency();
        eur.setCode("EUR");
        eur.setSymbol("&euro;");
        eur.setRate("52,243.287");
        eur.setDescription("Euro");
        eur.setRateFloat(BigDecimal.valueOf(52243.2865));

        bpiMap.put("USD", usd);
        bpiMap.put("GBP", gbp);
        bpiMap.put("EUR", eur);

        coinRes.setTime(timeData);
        coinRes.setDisclaimer("just for test");
        coinRes.setChartName("Bitcoin");
        coinRes.setBpi(bpiMap);

        Mockito.when(coinDeskClient.getAllCoinList()).thenReturn(coinRes);

        // 執行
        CoinDeskRes result = coinDeskService.getCoinDeskList();

        //驗證
        assertNotNull(result);
        assertNotNull(result.getCurrencyList());
        assertEquals("2024/09/02 07:07:20", result.getUpdatedTime());
        assertEquals("Bitcoin", result.getChartName());
        assertEquals(3, result.getCurrencyList().size());

        // 驗證三種幣別
        Map<String, CoinDeskRes.CurrencyDetail> currencyMap =
                result.getCurrencyList().stream()
                        .collect(Collectors.toMap(CoinDeskRes.CurrencyDetail::getCode, c -> c));

        assertEquals(3, currencyMap.size());

        assertAll(
                () -> {
                    CoinDeskRes.CurrencyDetail detailUsd = currencyMap.get("USD");
                    assertEquals(CoinDeskConst.CurrType.USD.getName(), detailUsd.getChineseName());
                    assertEquals("&#36;", detailUsd.getSymbol());
                    assertEquals("57,756.298", detailUsd.getRate());
                    assertEquals("United States Dollar", detailUsd.getDescription());
                    assertEquals(BigDecimal.valueOf(57756.2984), detailUsd.getRateFloat());
                },
                () -> {
                    CoinDeskRes.CurrencyDetail detailGbp = currencyMap.get("GBP");
                    assertEquals(CoinDeskConst.CurrType.GBP.getName(), detailGbp.getChineseName());
                    assertEquals("&pound;", detailGbp.getSymbol());
                    assertEquals("43,984.02", detailGbp.getRate());
                    assertEquals("British Pound Sterling", detailGbp.getDescription());
                    assertEquals(BigDecimal.valueOf(43984.0203), detailGbp.getRateFloat());
                },
                () -> {
                    CoinDeskRes.CurrencyDetail detailEur = currencyMap.get("EUR");
                    assertEquals(CoinDeskConst.CurrType.EUR.getName(), detailEur.getChineseName());
                    assertEquals("&euro;", detailEur.getSymbol());
                    assertEquals("52,243.287", detailEur.getRate());
                    assertEquals("Euro", detailEur.getDescription());
                    assertEquals(BigDecimal.valueOf(52243.2865), detailEur.getRateFloat());
                }
        );

        // 驗證 mockClient.getCurrentPrice() 被呼叫一次
        verify(coinDeskClient, times(1)).getAllCoinList();

        // 確保 mockClient 沒有被多餘呼叫
        verifyNoMoreInteractions(coinDeskClient);

    }
}
