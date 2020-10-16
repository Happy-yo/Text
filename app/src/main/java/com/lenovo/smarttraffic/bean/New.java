package com.lenovo.smarttraffic.bean;

public class New {
    private String tile;
    private String time;
    private String context;

    public New(String tile, String time, String context) {
        this.tile = tile;
        this.time = time;
        this.context = context;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
