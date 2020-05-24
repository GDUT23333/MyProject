package com.mjh.www.po;

/**
 * 登录时的类，一开始不知道是商家还是用户
 */
public class Base {
    private String userName;
    private String password;
    private String power;
    private String name;
    private String sex;
    private String phone;
    private String sign;
    private String nameTwo;
    private String status;

    public Base(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Base(String name, String sex, String phone, String sign, String nameTwo) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.sign = sign;
        this.nameTwo = nameTwo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public void setNameTwo(String nameTwo) {
        this.nameTwo = nameTwo;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
