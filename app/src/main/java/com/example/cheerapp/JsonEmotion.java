package com.example.cheerapp;

public class JsonEmotion {
    private String desc;
    private float nEmotion;

    public JsonEmotion(String desc, float nEmotion) {
        this.desc = desc;
        this.nEmotion = nEmotion;
    }

    public String getDesc() {
        return desc;
    }

    public float getnEmotion() {
        return nEmotion;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setnEmotion(float nEmotion) {
        this.nEmotion = nEmotion;
    }
}
