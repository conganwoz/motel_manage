package com.edu.anlu.motel_management;

public class LiveIn {
    private String roomId;
    private String hostId;
    private String motelId;


    public LiveIn(){

    }

    public LiveIn(String roomId, String hostId, String motelId) {
        this.roomId = roomId;
        this.hostId = hostId;
        this.motelId = motelId;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getMotelId() {
        return motelId;
    }

    public void setMotelId(String motelId) {
        this.motelId = motelId;
    }
}
