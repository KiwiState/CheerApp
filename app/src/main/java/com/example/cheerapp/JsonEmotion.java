package com.example.cheerapp;

import java.time.LocalDate;

public class JsonEmotion {
    private String desc;
    private float nEmotion;
    private LocalDate fecha;

    public JsonEmotion(String desc, float nEmotion,LocalDate fecha) {
        this.desc = desc;
        this.nEmotion = nEmotion;
        this.fecha = fecha;
    }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

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

