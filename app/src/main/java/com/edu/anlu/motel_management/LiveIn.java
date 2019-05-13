package com.edu.anlu.motel_management;

import java.util.ArrayList;
import java.util.List;

public class LiveIn {
    private String roomId;
    private String hostId;
    private String motelId;
    private List<Message> messages;

    public LiveIn(){
        messages = new ArrayList<>();

    }

    public LiveIn(String roomId, String hostId, String motelId) {
        this.roomId = roomId;
        this.hostId = hostId;
        this.motelId = motelId;
        this.messages = new ArrayList<>();
    }

    public LiveIn(String roomId, String hostId, String motelId, List<Message> messages) {
        this.roomId = roomId;
        this.hostId = hostId;
        this.motelId = motelId;
        this.messages = messages;
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

    public void addMessage(Message msg){
        this.messages.add(msg);
    }
}
