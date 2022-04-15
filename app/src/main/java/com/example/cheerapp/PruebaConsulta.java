package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public class PruebaConsulta extends AppCompatActivity {

    Connection connect;
    public Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_consulta);


        boton = (Button) findViewById(R.id.btnConsulta);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                consultarDatos();
            }
        });
    }





    public void consultarDatos(){

        TextView txV1 = (TextView) findViewById(R.id.textView1);
        TextView txV2 = (TextView) findViewById(R.id.textView2);

        try {

            ConexionBD conector = new ConexionBD();
            connect = conector.Conector();

            if(connect!=null){

                String query = "SELECT * FROM CONSEJO";
                Statement stm = connect.createStatement();
                ResultSet rs = stm.executeQuery(query);

                while(rs.next()){

                    txV1.setText(rs.getString(1));
                    txV2.setText(rs.getString(2));

                }

            }


        }catch (SQLException e){
            e.printStackTrace();
        }



    }
}