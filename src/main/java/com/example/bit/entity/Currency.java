package com.example.bit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CURRENCY")
public class Currency {

    @Id
    @Column( name = "code",length = 5,nullable = false, unique = true)
    private String code;

    @Column(name = "name",length = 50, nullable = false)
    private String name;

    @Column(name = "symbol",length = 10)
    private String symbol;

    @Column(name = "exchange_rate",precision = 10, scale = 4,nullable = false)
    private BigDecimal exchangeRate;

    @Column(name = "created_time",nullable = true)
    private LocalDateTime createdTime;

    @Column(name = "update_time",nullable = true)
    private LocalDateTime updateTime;

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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
