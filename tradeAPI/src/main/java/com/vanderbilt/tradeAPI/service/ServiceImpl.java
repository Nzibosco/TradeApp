package com.vanderbilt.tradeAPI.service;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.market.TickerPrice;
import com.vanderbilt.tradeAPI.config.BinanceApiConfig;
import com.vanderbilt.tradeAPI.entity.Action;
import com.vanderbilt.tradeAPI.validation.OrderValidation;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.binance.api.client.domain.account.NewOrder.marketBuy;
import static com.binance.api.client.domain.account.NewOrder.marketSell;

@Service
public class ServiceImpl implements ApiService {

    private final BinanceApiRestClient client;

    public ServiceImpl(){
        BinanceApiConfig apiConfig = new BinanceApiConfig();
        client = apiConfig.binanceClient();
    }

    @Override
    public NewOrderResponse marketOrder(String asset, String orderType, String quantity) throws Exception {
        if(OrderValidation.checkAssetAllowed(asset)) {
            String BUY = "BUY";
            String SELL = "SELL";
            String APPENDER = "USD";
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

    public List<TickerPrice> getLatestPrices() {
        List<TickerPrice> prices = client.getAllPrices();
        return prices.stream().filter(pr ->
                pr.getSymbol().equals("DOGEUSD") ||
                pr.getSymbol().equals("BNBUSD") ||
                pr.getSymbol().equals("ADAUSD") ||
                pr.getSymbol().equals("ETHUSD")).collect(Collectors.toList());
    }

    /**
     * Method that processes text message from UI or messenger chat
     */
    public String processChatRequest (String message, Action action){
        /**
         * Scenarios:
         * - User request prices
         * - Request balance
         * - Request trading history
         * - Place a sell or buy order
         * SAMPLE TEXT:
         * > current prices
         * > Sell 2 etheriums
         * > buy 100 dogecoin
         * > get history
         * >
         *
         */
        switch(action){
            case BALANCE :
                return "Getting the balance for you ... \n The balance is " + sendBalance();
            case PRICES:
                return "Getting the latest prices for you!";
            case BUY:
                return "About to place a buy order";
            case SELL:
                return "About to sell crypto for you";
            case HISTORY:
                return "Retrieving your trade history";
            default:
                return "Sorry, I couldn't understand your request. Try again";
        }

    }

    private double sendBalance(){
        List<TickerPrice> prices = getLatestPrices();
        List<AssetBalance> balances = getBalances();
        double balance = 0.00;
        Iterator<TickerPrice> iter = prices.iterator();
        while(iter.hasNext()){
            TickerPrice price = iter.next();
            AssetBalance asset = balances.stream().filter(bal -> bal.getAsset().equals(price.getSymbol())).collect(Collectors.toList()).get(0);
            balance+= (Double.parseDouble(price.getPrice()) * Double.parseDouble(asset.getFree()));
            iter.remove();
            balances.remove(asset);
        }
        return balance;
    }
}
