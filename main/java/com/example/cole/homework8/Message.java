/*
    Cole Howell, Manoj Bompada
    Message.java
    ITCS 4180
 */

package com.example.cole.homework8;

import java.security.Timestamp;

/**
 * Created by Manoj on 4/15/2016.
 */
public class Message {

    String timestamp;
    Boolean msg_read = false;
    String msg_txt = "", receiver="", sender="";

    public Message() {
    }

    public Message(String timestamp, Boolean msg_read, String msg_txt, String receiver, String sender) {
        this.timestamp = timestamp;
        this.msg_read = msg_read;
        this.msg_txt = msg_txt;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getMsg_read() {
        return msg_read;
    }

    public void setMsg_read(Boolean msg_read) {
        this.msg_read = msg_read;
    }

    public String getMsg_txt() {
        return msg_txt;
    }

    public void setMsg_txt(String msg_txt) {
        this.msg_txt = msg_txt;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp='" + timestamp + '\'' +
                ", msg_read=" + msg_read +
                ", msg_txt='" + msg_txt + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
