package com.example.cheerapp.clases;
import org.json.JSONArray;
public class Emocion {
    private JSONArray arrayEmocion;
    private EmocionCallback callBack;


    public Emocion() {
    }



    public JSONArray getArrayEmocion() {
        return arrayEmocion;
    }

    public void setArrayEmocion(JSONArray arrayEmocion) {
        this.arrayEmocion = arrayEmocion;
    }

    public EmocionCallback getCallBack() {
        return callBack;
    }

    public void setCallBack(EmocionCallback callBack){
        this.callBack=callBack;
    }


    public interface EmocionCallback{
        void displayEmocion(JSONArray emocion);
    }
}
