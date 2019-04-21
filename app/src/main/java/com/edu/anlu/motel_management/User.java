package com.edu.anlu.motel_management;

public class User {
    String userName;
    String address;
    String phoneNumber;
    int role;
    String idCard;
    String userId;
    boolean isFillAllInfor = false;
    String idRoom = "";

    public User(){

    }

    public User(String userName, String address, String phoneNumber, int role, String idCard, String userId, boolean isFillAllInfor) {
        this.userName = userName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.idCard = idCard;
        this.userId = userId;
        this.isFillAllInfor = isFillAllInfor;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRole() {
        return role;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isFillAllInfor() {
        return isFillAllInfor;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }
}
