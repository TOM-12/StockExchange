/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t.stock.model;

/**
 *
 * @author TOM
 */
public enum TransactionStatusEnum {
    NO_CONNECTION("Exchange rate service is down. Transaction couldn't be processed."),
    NOT_ENOUGH_MONEY("You don't have money available to buy that amount of this stock. Transaction couldn't be processed."),
    ERROR("There was error in handling Your transaction. Transaction couldn't be processed."),
    NOT_AVAILABLE("You are trying to buy more stock that is available. Transaction couldn't be processed."),
    TOO_MUCH("You are trying to sell more stock that You have. Transaction couldn't be processed."),
    NO_STOCK_IN_WALLET("You are trying to sell stock that You don't have. Transaction couldn't be processed."),
    NEGATIVE("Can't buy negative amount of stock."),
    SUCCESS("Transaction ended in success.");

    private final String description;

    private TransactionStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
