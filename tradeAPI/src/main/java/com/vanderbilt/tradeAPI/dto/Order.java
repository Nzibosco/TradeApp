package com.vanderbilt.tradeAPI.dto;

/**
 * This DTO is to organize an order placed from the client using a front end structured form
 */
public class Order {
    private String asset;
    private String quantity;
    private String orderType; //Buy or Sell

    public Order() {
    }

    public Order (String asset, String quantity, String orderType){
        this.asset = asset;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
