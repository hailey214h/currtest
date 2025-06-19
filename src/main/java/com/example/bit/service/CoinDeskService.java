package com.example.bit.service;

import com.example.bit.constant.CoinDeskConst;
import com.example.bit.gateway.coindesk.CoinDeskClient;
import com.example.bit.gateway.coindesk.CoinRes;
import com.example.bit.model.res.CoinDeskRes;
import com.example.bit.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CoinDeskService {

    @Autowired
    private CoinDeskClient coinDeskClient;

    /**
     * 取得coinDesk API 並資料轉換
     * @return
     */
    public CoinDeskRes getCoinDeskList() {
        CoinRes coinRes = coinDeskClient.getAllCoinList();

        CoinDeskRes coinDeskRes = new CoinDeskRes();
        coinDeskRes.setUpdatedTime(DateUtil.formatDateToStr(coinRes.getTime().getUpdatedISO(), "yyyy/MM/dd HH:mm:ss"));
        coinDeskRes.setChartName(coinRes.getChartName());
        coinDeskRes.setCurrencyList(buildCurrencyList(coinRes.getBpi()));

        return coinDeskRes;
    }

    private List<CoinDeskRes.CurrencyDetail> buildCurrencyList(Map<String, CoinRes.Currency> bpiMap){
        return bpiMap.values().stream()
                .map(this::buildCurrency).collect(Collectors.toList());
    }

    private CoinDeskRes.CurrencyDetail buildCurrency(CoinRes.Currency currency){
        CoinDeskRes.CurrencyDetail currencyDetail = new CoinDeskRes.CurrencyDetail();
        currencyDetail.setCode(currency.getCode());
        currencyDetail.setChineseName(CoinDeskConst.CurrType.findCurrToName(currency.getCode()));
        currencyDetail.setSymbol(currency.getSymbol());
        currencyDetail.setRate(currency.getRate());
        currencyDetail.setDescription(currency.getDescription());
        currencyDetail.setRateFloat(currency.getRateFloat());

        return currencyDetail;
    }
}
