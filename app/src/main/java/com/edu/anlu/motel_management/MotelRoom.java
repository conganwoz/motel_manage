package com.edu.anlu.motel_management;

public class MotelRoom {
    String userId;
    double area;
    double electroMonth;
    double waterMonth;
    double roomMonth;
    double otherFee;
    double notPaidTillNow = 0;
    String locate;


    public MotelRoom(){

    }


    public MotelRoom(String userId, double area, double electroMonth, double waterMonth, double roomMonth, double otherFee, double notPaidTillNow, String locate) {
        this.userId = userId;
        this.area = area;
        this.electroMonth = electroMonth;
        this.waterMonth = waterMonth;
        this.roomMonth = roomMonth;
        this.otherFee = otherFee;
        this.notPaidTillNow = notPaidTillNow;
        this.locate = locate;
    }


    public MotelRoom(double area, double electroMonth, double waterMonth, double roomMonth, double otherFee, String locate) {
        this.area = area;
        this.electroMonth = electroMonth;
        this.waterMonth = waterMonth;
        this.roomMonth = roomMonth;
        this.otherFee = otherFee;
        this.locate = locate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getElectroMonth() {
        return electroMonth;
    }

    public void setElectroMonth(double electroMonth) {
        this.electroMonth = electroMonth;
    }

    public double getWaterMonth() {
        return waterMonth;
    }

    public void setWaterMonth(double waterMonth) {
        this.waterMonth = waterMonth;
    }

    public double getRoomMonth() {
        return roomMonth;
    }

    public void setRoomMonth(double roomMonth) {
        this.roomMonth = roomMonth;
    }

    public double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }

    public double getNotPaidTillNow() {
        return notPaidTillNow;
    }

    public void setNotPaidTillNow(double notPaidTillNow) {
        this.notPaidTillNow = notPaidTillNow;
    }
}
