package com.example.bit.service;

import com.example.bit.entity.Currency;
import com.example.bit.exception.CurrencyAlreadyExistsException;
import com.example.bit.exception.CurrencyException;
import com.example.bit.model.req.CurrencyBaseReq;
import com.example.bit.model.req.CurrencyReq;
import com.example.bit.model.res.CurrencyRes;
import com.example.bit.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;


    /**
     * 取得所有資料
     *
     * @return
     */
    public List<CurrencyRes> findAll() {
        List<Currency> currencyList = currencyRepository.findAll();

        // 沒資料回傳空陣列
        if (currencyList.isEmpty()) {
            return new ArrayList<>();
        }

        return currencyList.stream()
                .map(this::buildCurrToRes)
                .collect(Collectors.toList());
    }

    private CurrencyRes buildCurrToRes(Currency currency) {
        CurrencyRes currencyRes = new CurrencyRes();

        currencyRes.setCode(currency.getCode());
        currencyRes.setName(currency.getName());
        currencyRes.setSymbol(currency.getSymbol());
        currencyRes.setExchangeRate(currency.getExchangeRate());
        currencyRes.setCreatedTime(currency.getCreatedTime());
        currencyRes.setUpdateTime(currency.getUpdateTime());

        return currencyRes;
    }

    /**
     * 查詢某筆資料
     *
     * @param req
     * @return
     */
    public CurrencyRes findByCode(CurrencyBaseReq req) {
        Currency currency = currencyRepository.findById(req.getCode())
                .orElseThrow(()-> new CurrencyException(req.getCode()));

        return buildCurrToRes(currency);

    }

    /**
     * 新增資料
     *
     * @param req
     * @return
     */
    public CurrencyRes addCurr(CurrencyReq req) {
        Optional<Currency> currencyOptional = currencyRepository.findById(req.getCode());

        // 資料已存在不能新增
        if (currencyOptional.isPresent()) {
            throw new CurrencyAlreadyExistsException(currencyOptional.get().getCode());
        }

        Currency currency = new Currency();
        currency.setCode(req.getCode());
        currency.setName(req.getName());
        currency.setSymbol(req.getSymbol());
        currency.setExchangeRate(req.getExchangeRate());
        currency.setCreatedTime(LocalDateTime.now());
        currency.setUpdateTime(LocalDateTime.now());

        currencyRepository.save(currency);

        return buildCurrToRes(currency);
    }

    /**
     * 修改資料
     *
     * @param req
     * @return
     */
    public CurrencyRes updateCurr(CurrencyReq req) {
        Currency currency = currencyRepository.findById(req.getCode())
                .orElseThrow(()-> new CurrencyException(req.getCode()));

        currency.setCode(req.getCode());
        currency.setName(req.getName());
        currency.setSymbol(req.getSymbol());
        currency.setExchangeRate(req.getExchangeRate());
        currency.setUpdateTime(LocalDateTime.now());

        currencyRepository.save(currency);

        return buildCurrToRes(currency);
    }

    /**
     * 刪除資料
     *
     * @param req
     */
    public void deleteCurr(CurrencyBaseReq req) {
        Currency currency = currencyRepository.findById(req.getCode())
                .orElseThrow(()-> new CurrencyException(req.getCode()));

        currencyRepository.deleteById(currency.getCode());

    }
}
