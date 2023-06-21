package com.example.newsapp.OOP;

public class SavedList {
    private String tittle;
    private String source;
    private String time;
    private String desc;
    private String imageUrl;
    private String url;

    public SavedList() {
    }

    public SavedList(String tittle, String source, String time, String desc, String imageUrl, String url) {
        this.tittle = tittle;
        this.source = source;
        this.time = time;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
