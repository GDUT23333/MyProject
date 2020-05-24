package com.mjh.www.po;

import java.security.PrivateKey;
import java.util.List;

/**
 * 商品类
 */
public class Route {
    private int category;
    private String img;
    private float price;
    private String introduce;
    private String title;
    private int id;
    private List<Pitcher> picture;
    private String sellerAddr;
    private String sellerPhone;
    private String sellerName;
    private boolean isFavourite;
    private List<Comment> comments;
    private int totalCount;//评论的总条数

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public Route(int category, String img, float price, String introduce, String title) {
        this.category = category;
        this.img = img;
        this.price = price;
        this.introduce = introduce;
        this.title = title;
    }

    public Route(String img, float price, String introduce, String title, int id) {
        this.img = img;
        this.price = price;
        this.introduce = introduce;
        this.title = title;
        this.id = id;
    }

    public String getSellerAddr() {
        return sellerAddr;
    }

    public void setSellerAddr(String sellerAddr) {
        this.sellerAddr = sellerAddr;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Route(String img, float price, String introduce, String title, int id, int category) {
        this.img = img;
        this.price = price;
        this.introduce = introduce;
        this.title = title;
        this.id = id;
        this.category = category;
    }

    public Route(String img, float price, String introduce, String title, int id, List<Pitcher> picture) {
        this.img = img;
        this.price = price;
        this.introduce = introduce;
        this.title = title;
        this.id = id;
        this.picture = picture;
    }

    public List<Pitcher> getPicture() {
        return picture;
    }

    public void setPicture(List<Pitcher> picture) {
        this.picture = picture;
    }

    public Route() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float prize) {
        this.price = prize;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
