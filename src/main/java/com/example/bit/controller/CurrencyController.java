package com.example.bit.controller;

import com.example.bit.model.req.CurrencyBaseReq;
import com.example.bit.model.req.CurrencyReq;
import com.example.bit.model.res.CurrencyRes;
import com.example.bit.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("coin/currency")
class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/getAllCurrList")
    public ResponseEntity<List<CurrencyRes>> getAllCurrList() {
        return ResponseEntity.ok(currencyService.findAll());
    }

    @PostMapping("/getCurr")
    public ResponseEntity<CurrencyRes> getCurrByCode(@RequestBody @Valid CurrencyBaseReq req) {
        return ResponseEntity.ok(currencyService.findByCode(req));
    }

    @PostMapping("/addCurr")
    public ResponseEntity<CurrencyRes> addCurr(@RequestBody @Valid  CurrencyReq req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(currencyService.addCurr(req));
    }

    @PostMapping("/updateCurr")
    public ResponseEntity<CurrencyRes> updateCurr(@RequestBody @Valid  CurrencyReq req) {
        return ResponseEntity.ok(currencyService.updateCurr(req));
    }

    @PostMapping("/deleteCurr")
    public ResponseEntity<Void> deleteCurr(@RequestBody @Valid  CurrencyBaseReq req) {

        currencyService.deleteCurr(req);

        return ResponseEntity.noContent().build();
    }
}
