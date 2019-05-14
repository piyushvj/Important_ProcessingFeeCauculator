package com.sapient.ProcessingFeeCalc.service;

import com.opencsv.CSVWriter;
import com.sapient.ProcessingFeeCalc.contants.ProcessingCharges;
import com.sapient.ProcessingFeeCalc.model.TransactionData;
import com.sapient.ProcessingFeeCalc.util.TransactionChargeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class TransactionCalculationImpl implements TransactionCalculation {

    LinkedList<TransactionData> intraDayTransaction = new LinkedList<>();

    @Autowired
    TransactionCalculation transactionCalculation;

    @Autowired
    TransactionChargeUtil transactionChargeUtil;

    private final Logger LOG= LoggerFactory.getLogger(TransactionCalculationImpl.class);

    public void calculateTransaction(List<TransactionData> transactionDataList){

        List<String> transactionType = Arrays.asList("BUY", "SALE");

        transactionDataList.forEach( transactionData -> {
            if(transactionType.contains(transactionData.getTransactionType())){
                int intraIndex = getSameDateClientTransactionIndex(transactionData);
                if (intraIndex != -1 && !transactionData.getTransactionType().equalsIgnoreCase(intraDayTransaction.get(intraIndex).getTransactionType())) {
                    transactionChargeUtil.chargeForTransaction(transactionData, ProcessingCharges.valueOf("INTRA_DAY_CHARGE").getCharge());
                    transactionChargeUtil.chargeForTransaction(intraDayTransaction.get(intraIndex), ProcessingCharges.valueOf("INTRA_DAY_CHARGE").getCharge());
                    intraDayTransaction.remove(intraIndex);
                } else if( intraIndex != -1 && transactionData.getTransactionType().equalsIgnoreCase(intraDayTransaction.get(intraIndex).getTransactionType())){
                    TransactionData prevTransactionDetails = intraDayTransaction.get(intraIndex);
                    intraDayTransaction.remove(intraIndex);
                    intraDayTransaction.add(transactionData);
                } else {
                    intraDayTransaction.add(transactionData);
                }
            } else {
                transactionChargeUtil.chargeForTransaction(transactionData, getChargeForTransaction(transactionData));
            }
        });

        if(!intraDayTransaction.isEmpty()) {
            for(TransactionData transactionData : intraDayTransaction) {
                transactionChargeUtil.chargeForTransaction(transactionData, getChargeForTransaction(transactionData));
            }
        }
        intraDayTransaction.clear();
    }

    private int getSameDateClientTransactionIndex(TransactionData transactionData){
        for(int i=0;i<intraDayTransaction.size();i++){
            if(intraDayTransaction.get(i).equals(transactionData))
                return i;
        }
        return -1;
    }

    private int getChargeForTransaction(TransactionData transactionData){
        String priority=transactionData.getPRIORITY();
        String transactionType=transactionData.getTransactionType().toUpperCase();
        return ProcessingCharges.valueOf(priority+"_"+transactionType).getCharge();
    }

    @Override
    public File createReportForTransaction() {
        List<TransactionData> transactionDetailsList=transactionChargeUtil.getAllClientTransactions();
        File file=new File(Thread.currentThread().getName()+"_report.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = { "Client Id", "Transaction Type", "Transaction Date","Priority","Processing Fee" };
            writer.writeNext(header);
            List<String[]> dataList=new ArrayList<String[]>();
            for(TransactionData transactionData:transactionDetailsList){
                String[] data=new String[5];
                data[0]=transactionData.getClientId();
                data[1]=transactionData.getTransactionType();
                data[2]=transactionData.getTransactionDate();
                data[3]=transactionData.getPriorityFlag();
                data[4]=String.valueOf(transactionChargeUtil.getChargeForTransactionDetails(transactionData));
                dataList.add(data);
            }
            writer.writeAll(dataList);
            writer.close();
            return file;
        }
        catch (IOException e) {
            LOG.error("IOException while writing CSV report",e);
            return null;
        }
    }


}