package com.sapient.ProcessingFeeCalc.util;

import com.sapient.ProcessingFeeCalc.model.TransactionData;

import java.util.Comparator;

public class TransactionDetailsComparator implements Comparator<TransactionData> {
    @Override
    public int compare(TransactionData o1, TransactionData o2) {
        int clientCompare=o1.getClientId().compareTo(o2.getClientId());
        if(clientCompare==0){
            int transactionTypeCompare=o1.getTransactionType().compareTo(o2.getTransactionType());
            if(transactionTypeCompare==0){
                int transactionDateCompare=o1.getTransactionDate().compareTo(o2.getTransactionDate());
                if(transactionDateCompare==0)
                    return o1.getPriorityFlag().compareTo(o2.getPriorityFlag());
                else
                    return transactionDateCompare;
            }
            else
                return transactionTypeCompare;
        }
        else
            return clientCompare;
    }
}