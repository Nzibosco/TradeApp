package com.vanderbilt.tradeAPI.controller;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.market.TickerPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private Environment env;

    @GetMapping
    public String test(){
        System.out.println("\n ====== \n API KEY IS = " + env.getProperty("API_KEY"));
        return "Trade App Working";
    }

    @GetMapping("binance")
    public String testBinanceAPI(){
        
        BinanceApiClientFactory factory = BinanceApiClientFactory
                .newInstance(env.getProperty("API_KEY"), env.getProperty("SECRET_KEY"), false, false);
        BinanceApiRestClient client = factory.newRestClient();

        List<TickerPrice> allPrices = client.getAllPrices();
        System.out.println("===API KEY IS "+ env.getProperty("API_KEY"));
        Account acc = client.getAccount();


        System.out.println(acc.toString());
        System.out.println("\n=========\n=== NUMBER OF PRICES FETCHED : " + allPrices.size() + " ====  \n");
        return "Server time from Binance is: " + client.getServerTime().toString();
    }

}
