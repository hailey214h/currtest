package com.example.bit.model.req;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class CurrencyReq {

    @NotBlank(message = "code cannot be blank")
    @Size(max = 5, message = "code must not exceed 5 characters")
    private String code;
    @NotBlank(message = "name cannot be blank")
    @Size(max = 50, message = "name must not exceed 50 characters")
    private String name;
    @Size(max = 10, message = "symbol must not exceed 10 characters")
    private String symbol;
    @NotNull(message = "exchangeRate cannot be null")
    @Digits(integer = 6, fraction = 4, message = "exchangeRate must be a valid decimal number with up to 10 digits")
    private BigDecimal exchangeRate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
