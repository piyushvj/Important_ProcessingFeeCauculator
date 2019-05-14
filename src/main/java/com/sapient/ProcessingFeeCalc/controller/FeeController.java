package com.sapient.ProcessingFeeCalc.controller;

import com.sapient.ProcessingFeeCalc.FactoryUtil.CustomFileReader;
import com.sapient.ProcessingFeeCalc.FactoryUtil.CustomFileReaderFactory;
import com.sapient.ProcessingFeeCalc.model.TransactionData;
import com.sapient.ProcessingFeeCalc.service.TransactionCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/processing")
public class FeeController {

    @Autowired
    private Environment env;

    @Autowired
    private CustomFileReaderFactory customFileReaderFactory;

    @Autowired
    private TransactionCalculation transactionCalculation;

    private final Logger LOG= LoggerFactory.getLogger(FeeController.class);

    @RequestMapping(value = "/calculateFee", method = RequestMethod.GET)
    public ResponseEntity calculateTransactionFee(){
        String fileName = env.getProperty("source.file.path");
        System.out.println("File Name : " + fileName);
        String fileType = getFileType(fileName);
        try {
            // read transaction from source
            CustomFileReader customFileReader = customFileReaderFactory.getCustomFileReader(fileType);

            if(customFileReader!= null) {
                List<TransactionData> transactionData = customFileReader.readData(fileName);
                System.out.println(transactionData);
                transactionCalculation.calculateTransaction(transactionData);
                return ResponseEntity.ok("Transaction charges calculated successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid File");
            }
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().body("Invalid File");
        }
    }

    public String getFileType(String fileName){
        String fileExtn=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        return fileExtn.toUpperCase();
    }


    @GetMapping("/v1/downloadReport")
    public void downloadReport(HttpServletResponse response){
        try {
            File csvReport = transactionCalculation.createReportForTransaction();
            OutputStream out = response.getOutputStream();
            out.write(Files.readAllBytes(csvReport.toPath()));
            out.flush();
            out.close();
        }
        catch(IOException e){
            LOG.error("IOException while ouputting file",e);
        }
    }
}
