package com.vanderbilt.tradeAPI.validation;

import java.util.Arrays;
import java.util.List;

/**
 * Methods to validate orders
 */
public class OrderValidation {

    public static final List<String> allowedAssets = Arrays.asList("DOGE", "BNB", "ADA", "ETH");

    /**
     * Check if the asset being sold or bought is allowed
     */
    public static boolean checkAssetAllowed (String asset){
       return allowedAssets.contains(asset);
    }
}
