package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Prueba de conexión hacia base de datos.
    /*public static void main(String[] args){

        ConnectionClass conexion = new ConnectionClass();
        conexion.Conector();
    }*/
}