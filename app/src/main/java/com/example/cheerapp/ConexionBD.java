package com.example.cheerapp;

import android.annotation.SuppressLint;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

@SuppressLint("NewApi")
public class ConexionBD {

    String URL = "jdbc:oracle:thin:@cheerappdb_high?TNS_ADMIN=app/Wallet_CheerAppDB";
    String user = "DEVELOPER";
    String password = "Cheerapp1234";
    Connection conexion = null;


    public Connection Conector(){

        try {

            conexion = DriverManager.getConnection(URL, user, password);
            System.out.println("Conectado a base de datos Oracle");


        }catch(SQLException e){
            System.out.println("Error de conexion");
            e.printStackTrace();

        }
    return conexion;

    }
}
