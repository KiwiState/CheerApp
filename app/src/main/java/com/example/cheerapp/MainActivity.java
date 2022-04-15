package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.secondBttn);
        boton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSecondView();
            }
        });


    }

    public void openSecondView(){
        Intent intent = new Intent(this, PruebaConsulta.class);
        startActivity(intent);
    }
}