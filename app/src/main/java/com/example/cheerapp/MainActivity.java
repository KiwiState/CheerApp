package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.Period;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hsalf.smilerating.SmileRating;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private Button btn_emo;
    private Button btn_his;

    private Button btn_safetyn;
    private Button btn_nayuda;
    private Button btn_DBI;


    ArrayList<JsonEmotion> ListaE;
    //btn_historial
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListaE = new ArrayList<>();
        btn_emo = findViewById(R.id.btn_emotion);
        btn_his = findViewById(R.id.btn_historial);
        btn_safetyn = findViewById(R.id.btn_SafetyN);
        btn_nayuda = findViewById(R.id.btn_Nayudas);
        btn_DBI = findViewById(R.id.btn_DBI_II);
        loadData();
        patronEm();

        btn_emo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainForm.class);
                startActivity(i);
            }
        });
        btn_his.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainJournal.class);
                startActivity(i);
            }
        });
        btn_safetyn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SafetyNumber.class);
                startActivity(i);
            }
        });
        btn_nayuda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,HelpNumbers.class);
                startActivity(i);
            }
        });
        btn_DBI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SafetyNumber.class);
                startActivity(i);
            }
        });


    }
    private void patronEm(){
        int counterD = 0;                                                                            //Contadores de Dia
        int sumE = 0;                                                                                //Suma de todos los valore de la emociones
        int promE = 0;

                                                                                                     //Toast.makeText(MainActivity.this,"Diffrence between dates is : "+diffDays + "days",Toast.LENGTH_SHORT).show();// se crea el array **solo testeo**
        LocalDate fecha = LocalDate.now();                                                           // se guarda la fecha de hoy

        Collections.reverse(ListaE);                                                                 //dara vuelta el array List para leer de el ultimo a el primero

        for(int i = 0; i < ListaE.size(); i++){                                                      //se hace un for para pasar por todos los valores de el array

            String ArrDate = ListaE.get(i).getFecha();
            LocalDate d1 = LocalDate.parse(ArrDate, DateTimeFormatter.ISO_LOCAL_DATE);
                                                                                                     //dentro de el array se recupera la fecha de el primer puesto del array
            Duration diff = Duration.between(d1.atStartOfDay(), fecha.atStartOfDay());          // se calcula los dias de diferencia entre la fecha guardad y el dia de hoy
            long diffDays = diff.toDays();                                                           //la diferencia de fechas se transforma a dias de diff

            if( diffDays > 13 && i != 0){                                                            //si la differencia de dias es mayor a 13 y i diferente 0 se saldra de el for
                i =ListaE.size();
            } else {
                counterD += 1;                                                                       //se contaran los dias que se registro su nivel de mocion
                sumE += ListaE.get(i).getnEmotion();                                                 //se sumaran todos los estados emocionales en una variable
            }


        }

        if(counterD >= 10){
            promE = sumE/counterD;
            if(promE <5){
                //dar consejos y numeros de ayuda
                //esta animicamente el mal usuario :
                btn_nayuda.setVisibility (View.VISIBLE);
                Toast.makeText(MainActivity.this,"El usuario presenta un nivel animico bajo",Toast.LENGTH_SHORT).show();
            }
            if (promE <= 2.5){
                btn_DBI.setVisibility (View.VISIBLE);
                // dar formulario DBI
                //esta critico el usuario >;c #noBoobis
                Toast.makeText(MainActivity.this,"El usuario presenta un nivel animico pauperrimo",Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(MainActivity.this,"No hay suficientes datos para hacer un patron",Toast.LENGTH_SHORT).show();
        }
        Collections.reverse(ListaE);                                                                //revierte a estado original el orden del array
    }

    private void loadData(){ // esto es con shared preference

        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<JsonEmotion>>() {}.getType();
        ListaE = gson.fromJson(json,type);
        if(ListaE == null){
            ListaE = new ArrayList<>();
        }



    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_main);

        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);

        dialog.show();

    }

}