package com.sapient.ProcessingFeeCalc.service;

import com.sapient.ProcessingFeeCalc.contants.ProcessingCharges;
import com.sapient.ProcessingFeeCalc.model.TransactionData;

import java.io.File;
import java.util.List;

public interface TransactionCalculation {
    public void calculateTransaction(List<TransactionData> transactionDataList);
    public File createReportForTransaction();
}
