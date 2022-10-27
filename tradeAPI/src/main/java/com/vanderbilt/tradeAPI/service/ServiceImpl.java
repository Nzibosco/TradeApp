package com.vanderbilt.tradeAPI.service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.vanderbilt.tradeAPI.config.BinanceApiConfig;
import com.vanderbilt.tradeAPI.validation.OrderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.binance.api.client.domain.account.NewOrder.marketBuy;
import static com.binance.api.client.domain.account.NewOrder.marketSell;

@Service
public class ServiceImpl implements ApiService {

    private final String BUY = "BUY";
    private final String SELL = "SELL";
    private final String APPENDER = "USD";
    private final BinanceApiRestClient client;

    public ServiceImpl(){
        BinanceApiConfig apiConfig = new BinanceApiConfig();
        client = apiConfig.binanceClient();
    }

    @Override
    public NewOrderResponse marketOrder(String asset, String orderType, String quantity) throws Exception {
        if(OrderValidation.checkAssetAllowed(asset)) {
            if (orderType.equals(BUY)) {
                return client.newOrder(marketBuy(asset + APPENDER, quantity));
            } else if (orderType.equals(SELL)) {
                return client.newOrder(marketSell(asset + APPENDER, quantity));
            } else {
                throw new Exception("Invalid order type. Only BUY or SELL orders are allowed");
            }
        } else {
            throw new Exception("Asset ordered is invalid. Please pass a valid asset");
        }
    }

    @Override
    public String checkAssetBalance(String asset){
        AssetBalance balance = client.getAccount().getAssetBalance(asset);
        return balance.getFree();
    }

    @Override
    public List<AssetBalance> getBalances(){
        List<AssetBalance> balances = client.getAccount().getBalances();
        return balances.stream().filter(bal -> bal.getAsset().equals("USD") ||
                bal.getAsset().equals("DOGE") ||
                bal.getAsset().equals("BNB") ||
                bal.getAsset().equals("ADA") ||
                bal.getAsset().equals("ETH")).collect(Collectors.toList());
    }
}
