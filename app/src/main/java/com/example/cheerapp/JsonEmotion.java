package com.example.cheerapp;


public class JsonEmotion {
    private String desc;
    private float nEmotion;
    private String fecha;

    public JsonEmotion(String desc, float nEmotion,String fecha) {
        this.desc = desc;
        this.nEmotion = nEmotion;
        this.fecha = fecha;
    }

    public String getFecha() { return fecha;}

    public void setFecha(String fecha) {this.fecha = fecha; }

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

    @Override
    public String toString() {
        return "Estado animico :"+nEmotion+" Fecha : "+fecha;
    }
}

