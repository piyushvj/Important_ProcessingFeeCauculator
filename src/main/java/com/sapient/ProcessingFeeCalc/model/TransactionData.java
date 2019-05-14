package com.sapient.ProcessingFeeCalc.model;

import java.util.Objects;

public class TransactionData {

    private String attributeName;
    private String extTransactionId;
    private String clientId;
    private String securityId;
    private String transactionType;
    private String transactionDate;
    private String marketValue;
    private String priorityFlag;
    private final String NORMAL="NORMAL";
    private final String PRIORITY="PRIORITY";

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getExtTransactionId() {
        return extTransactionId;
    }

    public void setExtTransactionId(String extTransactionId) {
        this.extTransactionId = extTransactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(String priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public String getNORMAL() {
        return NORMAL;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public boolean equals(Object object) {
        if(object instanceof TransactionData && object != null){
            TransactionData td = (TransactionData) object;
            return Objects.equals(clientId, this.clientId)
                    && Objects.equals(securityId, this.securityId)
                    && Objects.equals(transactionDate, this.transactionDate);
        } else {
            return false;
        }
    }

    public String toString(){
        System.out.println(""+ attributeName +","+
        extTransactionId +","+
        clientId +","+
        securityId +","+
        transactionType +","+
        transactionDate +","+
        marketValue +","+
        priorityFlag+"");
        return "";
    }
}
