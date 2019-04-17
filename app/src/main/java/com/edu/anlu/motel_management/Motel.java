package com.edu.anlu.motel_management;

public class Motel {
    int numberFloor;
    String address;
    String ruleDescription;
    int kind;
    double electroPerMonth;
    double waterPerMonth;
    double roomPerMonth;
    double otherFee;

    public Motel(){

    }


    public Motel(int numberFloor, String address, String ruleDescription, int kind, double electroPerMonth, double waterPerMonth, double roomPerMonth, double otherFee) {
        this.numberFloor = numberFloor;
        this.address = address;
        this.ruleDescription = ruleDescription;
        this.kind = kind;
        this.electroPerMonth = electroPerMonth;
        this.waterPerMonth = waterPerMonth;
        this.roomPerMonth = roomPerMonth;
        this.otherFee = otherFee;
    }


    public int getNumberFloor() {
        return numberFloor;
    }

    public void setNumberFloor(int numberFloor) {
        this.numberFloor = numberFloor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public double getElectroPerMonth() {
        return electroPerMonth;
    }

    public void setElectroPerMonth(double electroPerMonth) {
        this.electroPerMonth = electroPerMonth;
    }

    public double getWaterPerMonth() {
        return waterPerMonth;
    }

    public void setWaterPerMonth(double waterPerMonth) {
        this.waterPerMonth = waterPerMonth;
    }

    public double getRoomPerMonth() {
        return roomPerMonth;
    }

    public void setRoomPerMonth(double roomPerMonth) {
        this.roomPerMonth = roomPerMonth;
    }

    public double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }
}
