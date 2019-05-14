package com.sapient.ProcessingFeeCalc.FactoryUtil;

import com.sapient.ProcessingFeeCalc.model.TransactionData;
import java.util.List;

public interface CustomFileReader {
    public List<TransactionData> readData(String fileName);
}
