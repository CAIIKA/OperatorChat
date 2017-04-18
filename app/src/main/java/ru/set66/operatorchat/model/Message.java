package ru.set66.operatorchat.model;

/**
 * Created by Alex on 17.04.2017.
 */

public class Message {
   private String date;
    private String name;
    private String body;
    private String udid;
    private String phone;

    public Message(String date, String name, String body, String udid, String phone, String type) {
        this.date = date;
        this.name = name;
        this.body = body;
        this.udid = udid;
        this.phone = phone;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getUdid() {
        return udid;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    private String type;
}
