package com.vanderbilt.tradeAPI.controller;

import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.vanderbilt.tradeAPI.dto.Order;
import com.vanderbilt.tradeAPI.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    ServiceImpl service;

    @PostMapping("market")
    public NewOrderResponse marketOrder (@RequestBody Order order) throws Exception {
        return service.marketOrder(order.getAsset(), order.getOrderType(), order.getQuantity());
    }

    @GetMapping("asset-balance/{asset}")
    public String getAssetBalance (@PathVariable String asset){
        return service.checkAssetBalance(asset);
    }

    @GetMapping("balances")
    public List<AssetBalance> getAllAssetBalances (){
        return service.getBalances();
    }
}
