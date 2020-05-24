package com.mjh.www.po;

/**
 * 用户类
 */
public class User {
    private String headPitcher;

    public String getHeadPitcher() {
        return headPitcher;
    }

    public void setHeadPitcher(String headPitcher) {
        this.headPitcher = headPitcher;
    }

    private String email;
    private String password;
    private String name;
    private String sex;
    private String phone;
    private String nameTwo;
    private String sign;
    private String code;
    private String status;
    private Float money;

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User() {
    }


    public User(String email, String password, String name, String sex, String phone, String nameTwo) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.nameTwo = nameTwo;
    }


    public User(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public User(String headPitcher, String email, String password, String name, String sex, String phone, String nameTwo, String sign) {
        this.headPitcher = headPitcher;
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.nameTwo = nameTwo;
        this.sign = sign;
    }

    public User(String email, String password, String name, String sex, String phone, String headPitcher, String nameTwo, String sign, String code, String status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.nameTwo = nameTwo;
        this.sign = sign;
        this.code = code;
        this.status = status;
        this.headPitcher = headPitcher;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public void setNameTwo(String nameTwo) {
        this.nameTwo = nameTwo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
