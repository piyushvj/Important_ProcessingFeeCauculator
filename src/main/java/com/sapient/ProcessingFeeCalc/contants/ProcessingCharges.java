package com.sapient.ProcessingFeeCalc.contants;

public enum ProcessingCharges {
    NORMAL_BUY(50),
    NORMAL_DEPOSIT(50),
    PRIORITY_BUY(250),
    PRIORITY_DEPOSIT(250),
    NORMAL_SELL(100),
    NORMAL_WITHDRAW(100),
    PRIORITY_SELL(500),
    PRIORITY_WITHDRAW(500),
    INTRA_DAY(10),
    INTRA_DAY_CHARGE(10),
    HIGH_PRIORITY_CHARGE(500),
    NORMAL_PRIORITY_SALE_WITHDRAW(100),
    NORMAL_PRIORITY_BUY_DEPOSIT(50);

    private int charge;

    ProcessingCharges(int charge) {this.charge = charge;}

    public int getCharge(){
        return this.charge;
    }
}
