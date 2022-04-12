package com.example.cheerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;

public class ConnectionClass {

    String URL = "jdbc:oracle:thin:@cheerappdb_high?TNS_ADMIN=app/Wallet_CheerAppDB/";
    String username = "ADMIN";
    String password = "Cheerapp1234";


    public void Conector(){


        try {

            Connection conexion = DriverManager.getConnection(URL, username, password);
            System.out.println("Conectado a Base de Datos Oracle 19c");

        } catch (SQLException e) {

            System.out.println("Error de conexión");
            e.printStackTrace();
        }



    }
}
