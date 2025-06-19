package com.example.bit.model.res;

import java.math.BigDecimal;
import java.util.List;

public class CoinDeskRes {

    private String updatedTime;
    private String chartName;
    private List<CurrencyDetail> currencyDetailList;

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public List<CurrencyDetail> getCurrencyList() {
        return currencyDetailList;
    }

    public void setCurrencyList(List<CurrencyDetail> currencyDetailList) {
        this.currencyDetailList = currencyDetailList;
    }

    public static class CurrencyDetail {
        private String code;
        private String chineseName;
        private String symbol;
        private String rate;
        private String description;
        private BigDecimal rateFloat;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getChineseName() {
            return chineseName;
        }

        public void setChineseName(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getRateFloat() {
            return rateFloat;
        }

        public void setRateFloat(BigDecimal rateFloat) {
            this.rateFloat = rateFloat;
        }
    }
}
