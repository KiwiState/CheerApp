package com.example.cheerapp;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class ConexionBD {

    String URL = "jdbc:oracle:thin:@cheerapdb_high?TNS_ADMIN=app/Wallet_CheerAppDB";
    String user = "DEVELOPER";
    String password = "Cheerapp1234";

    public void Conector(){

        try {

            Connection conexion = DriverManager.getConnection(URL, user, password);
            System.out.println("Conectado a base de datos Oracle");

        }catch(SQLException e){
            System.out.println("Error de conexion");
            e.printStackTrace();

        }
    }
}
