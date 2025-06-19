package com.example.bit.controller;

import com.example.bit.model.res.CoinDeskRes;
import com.example.bit.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coin/coinDesk")
public class CoinDeskController {

    @Autowired
    private CoinDeskService coinDeskService;

    @GetMapping("/getAllCoinDesk")
    public ResponseEntity<CoinDeskRes> getAllCoinDesk() {
        return ResponseEntity.ok(coinDeskService.getCoinDeskList());
    }
}
