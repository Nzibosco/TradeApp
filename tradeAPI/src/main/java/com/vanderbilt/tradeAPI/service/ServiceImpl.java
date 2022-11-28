package com.vanderbilt.tradeAPI.service;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.market.TickerPrice;
import com.vanderbilt.tradeAPI.config.BinanceApiConfig;
import com.vanderbilt.tradeAPI.entity.Action;
import com.vanderbilt.tradeAPI.validation.OrderValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.binance.api.client.domain.account.NewOrder.marketBuy;
import static com.binance.api.client.domain.account.NewOrder.marketSell;

@Service
public class ServiceImpl implements ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
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
        logger.info("...........Getting balances ...");
        List<AssetBalance> balances = client.getAccount().getBalances();
        logger.info("....Successfully retrieved balances. Proceeding with filtering the crypto assets we need");
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
                return "Getting the balance for you ... \n The balance is " + aggregateBalance();
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

    public double aggregateBalance(){
        logger.info("..................PROCESSING GET BALANCE MESSENGER REQUEST ");
        double balance;
        //Map with assets and their respective prices
        HashMap<String, Double> map = assetBalances();
        logger.info("............Successfully retrieved assets and their monetary balances "+ map);
        balance = map.values().stream().reduce(0d, Double::sum);
        return balance;
    }

    public HashMap<String, Double> assetBalances (){
        logger.info("..................PROCESSING GET BALANCE MESSENGER REQUEST ");
        List<TickerPrice> prices = getLatestPrices();
        List<AssetBalance> balances = getBalances();
        //Map with assets and their respective prices
        HashMap<String, Double> map = new HashMap<>();
        for(TickerPrice ticker: prices){
            map.putIfAbsent(ticker.getSymbol().replace("USD", ""), Double.parseDouble(ticker.getPrice()));
        }
        //Multiple prices with asset balance
        logger.info(".... MAP WITH ASSETS AND PRICES BEFORE MULTIPLYING WITH BALANCES ==== " + map);
        for(AssetBalance bal: balances){
            if (map.containsKey(bal.getAsset())) {
                map.put(bal.getAsset(), map.get(bal.getAsset()) * Double.parseDouble(bal.getFree()));
            } else {
                map.put(bal.getAsset(), Double.parseDouble(bal.getFree()));
                logger.info(" ..... Asset names from balances and prices lists differ");
            }
        }
        logger.info(".......MAP UPDATED, PROCEEDING WITH AGGREGATING THE BALANCES");
        logger.info(".... UPDATED MAP = " + map);
        return map;
    }
}
