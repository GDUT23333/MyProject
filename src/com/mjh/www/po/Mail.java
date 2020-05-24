package com.mjh.www.po;

/**
 * 邮件类
 */
public class Mail {
    private String to;
    private String content;
    private String title;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Mail(String to, String content, String title) {
        this.to = to;
        this.content = content;
        this.title = title;
    }
}
