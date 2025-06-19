package com.example.bit.gateway.coindesk;

import com.example.bit.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.bit.constant.FeifnConst.CLIENT_COIN;

@FeignClient(name = CLIENT_COIN, url = "${coindesk.api.url}",
        configuration = FeignClientConfig.class)
public interface CoinDeskClient {

    @GetMapping("coindesk.json")
    CoinRes getAllCoinList();
}
