package com.edu.anlu.motel_management;

import java.util.ArrayList;
import java.util.List;

public class LiveIn {
    private String roomId;
    private String hostId;
    private String motelId;
    private String guestId;
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


    public LiveIn(String roomId, String hostId, String motelId, String guestId) {
        this.roomId = roomId;
        this.hostId = hostId;
        this.motelId = motelId;
        this.guestId = guestId;
        this.messages = new ArrayList<>();
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
        if(this.messages == null){
            this.messages = new ArrayList<>();
        }
        this.messages.add(msg);
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
