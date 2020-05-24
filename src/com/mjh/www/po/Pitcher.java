package com.mjh.www.po;

/**
 * 商品的首图
 */
public class Pitcher {
    private String href;
    private int id;

    public Pitcher(String href, int id) {
        this.href = href;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pitcher(String href) {
        this.href = href;
    }

    public Pitcher() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
