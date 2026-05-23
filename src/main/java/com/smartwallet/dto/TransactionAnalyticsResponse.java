/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartwallet.dto;

/**
 *
 * @author ABC
 */
public class TransactionAnalyticsResponse {
    private long totalSuccessTransactions;
    private long totalFailedTransactions;

    public TransactionAnalyticsResponse(long totalSuccessTransactions, long totalFailedTransactions) {
        this.totalSuccessTransactions = totalSuccessTransactions;
        this.totalFailedTransactions = totalFailedTransactions;
    }

    public long getTotalSuccessTransactions() {
        return totalSuccessTransactions;
    }

    public long getTotalFailedTransactions() {
        return totalFailedTransactions;
    }
    
    
    
}
