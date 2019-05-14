package com.sapient.ProcessingFeeCalc.util;

import com.sapient.ProcessingFeeCalc.model.TransactionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionChargeUtil {

    private ConcurrentHashMap<TransactionData, Integer> transactionCharge = new ConcurrentHashMap();

    public void chargeForTransaction(TransactionData transactionData, Integer charge){
        Integer currCharge = transactionCharge.get(transactionData);

        if(currCharge == null) {
            transactionCharge.put(transactionData, charge);
        } else {
            currCharge = currCharge + charge;
            transactionCharge.put(transactionData, currCharge);
        }
    }


    public List<TransactionData> getAllClientTransactions(){
        List<TransactionData> transactionDetailsList=new ArrayList<TransactionData>();
        transactionCharge.forEach((transactionDetails,charge)->{
            transactionDetailsList.add(transactionDetails);
        });
        transactionDetailsList.sort(new TransactionDetailsComparator());
        return transactionDetailsList;
    }

    public Integer getChargeForTransactionDetails(TransactionData transactionDetails){
        return transactionCharge.get(transactionDetails);
    }
}
