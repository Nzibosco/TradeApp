package com.vanderbilt.tradeAPI.service;

import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.NewOrderResponse;

import java.util.List;

/**
 * Definition of REST API methods available for client
 */
public interface ApiService {
    /**
     * Method to place a buy or sell order
     * @param asset (mandatory)
     * @param orderType (mandatory)
     * @param quantity (mandatory)
     * @return NewOrderResponse
     */
    NewOrderResponse marketOrder (String asset, String orderType, String quantity) throws Exception;

    /**
     * Check individual asset balance
     * @param asset (mandatory)
     * @return String
     */
    String checkAssetBalance(String asset);

    /**
     * Get balances for all assets in an account.
     * A filter should be made if we desire the list to include only assets where balance is more than 0.00
     * or specific assets we are concerned about
     * @return
     */
    List<AssetBalance> getBalances();

}
