package com.mjh.www.po;

/**
 * 商家类
 */
public class MerChant {
    private String email;
    private String password;
    private String sex;
    private String phone;
    private String name;
    private String nameTwo;
    private String code;//激活码
    private String status;//激活状态
    private String sign;
    private String headPitcher;
    private String addr;

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


    public MerChant(String sex, String phone, String name, String nameTwo, String sign,String email,String password,String headPitcher) {
        this.sex = sex;
        this.phone = phone;
        this.name = name;
        this.nameTwo = nameTwo;
        this.sign = sign;
        this.email = email;
        this.password = password;
        this.headPitcher = headPitcher;
    }
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getHeadPitcher() {
        return headPitcher;
    }

    public void setHeadPitcher(String headPitcher) {
        this.headPitcher = headPitcher;
    }
    public MerChant() {
    }

    public MerChant(String email, String password, String sex, String phone, String name, String code, String status, String nameTwo, String sign) {
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.name = name;
        this.code = code;
        this.status = status;
        this.nameTwo = nameTwo;
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
