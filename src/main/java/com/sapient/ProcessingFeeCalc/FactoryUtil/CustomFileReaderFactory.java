package com.sapient.ProcessingFeeCalc.FactoryUtil;

import com.sapient.ProcessingFeeCalc.contants.FileTypes;
import org.springframework.stereotype.Component;

@Component
public class CustomFileReaderFactory {

    public static CustomFileReader getCustomFileReader(String fileType) throws  Exception{
        System.out.println(" file Type : "+fileType);
        switch (fileType) {
            case "CSV":
                return new CVSFileReaderImpl();
            case "TEXT":
                return null; // TODO: return textImpl object
            default:
                    throw new Exception("inappropriate file type");
        }
    }





}
