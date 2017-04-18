package ru.set66.operatorchat.model;

/**
 * Created by Alex on 17.04.2017.
 */

public class Person {
    private String mName;
    private String mPhoneNumber;
    private String mUDID;
    private String mCountMessages;

    public Person(String name, String phone,String udid,String count){
        mName=name;
        mPhoneNumber=phone;
        mUDID=udid;
        mCountMessages=count;
    }

    public String getmName() {
        return mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmUDID() {
        return mUDID;
    }

    public String getmCountMessages() {
        return mCountMessages;
    }
}
