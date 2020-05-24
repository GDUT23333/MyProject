package com.mjh.www.po;

/**
 * 评论类
 */
public class Comment {
    private int id;
    private String userNameTwo;
    private String content;
    private int routeId;
    private String userHeadPitcher;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Comment(String userNameTwo, String content, int routeId, String userHeadPitcher) {
        this.userNameTwo = userNameTwo;
        this.content = content;
        this.routeId = routeId;
        this.userHeadPitcher = userHeadPitcher;
    }

    public String getUserHeadPitcher() {
        return userHeadPitcher;
    }

    public void setUserHeadPitcher(String userHeadPitcher) {
        this.userHeadPitcher = userHeadPitcher;
    }

    public Comment() {
    }

    public Comment(int id, String userNameTwo, String content, int routeId) {
        this.id = id;
        this.userNameTwo = userNameTwo;
        this.content = content;
        this.routeId = routeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNameTwo() {
        return userNameTwo;
    }

    public void setUserNameTwo(String userNameTwo) {
        this.userNameTwo = userNameTwo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
