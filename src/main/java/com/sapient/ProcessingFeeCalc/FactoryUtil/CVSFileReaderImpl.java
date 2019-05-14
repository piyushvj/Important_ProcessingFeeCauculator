package com.sapient.ProcessingFeeCalc.FactoryUtil;

import com.sapient.ProcessingFeeCalc.model.TransactionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class CVSFileReaderImpl implements CustomFileReader {

    private final Logger LOG= LoggerFactory.getLogger(CVSFileReaderImpl.class);
    @Value("${csv.delimiter}")
    private String delimiter;

    @Override
    public List<TransactionData> readData(String fileName) {
        List<TransactionData> transactionDetailsList=new ArrayList<>();
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName))){
            bufferedReader.readLine();
            String line=null;
            while((line=bufferedReader.readLine())!=null){
                String[] lineArray=line.split(",");
                TransactionData transactionDetails=new TransactionData();
                transactionDetails.setExtTransactionId(lineArray[0]);
                transactionDetails.setClientId(lineArray[1]);
                transactionDetails.setSecurityId(lineArray[2]);
                transactionDetails.setTransactionType(lineArray[3]);
                transactionDetails.setTransactionDate(lineArray[4]);
                transactionDetails.setMarketValue(lineArray[5]);
                transactionDetails.setPriorityFlag(lineArray[6]);
                transactionDetailsList.add(transactionDetails);
            }
            System.out.println("done returning");
            return transactionDetailsList;
        }
        catch(IOException e){
            LOG.error("IOException while reading data from CSV {}",e);
            return transactionDetailsList;
        }
    }
}
