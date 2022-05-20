package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;
import java.time.Period;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainForm extends AppCompatActivity {
    ArrayList<JsonEmotion> ListaE;
    private static final String FILE_NAME = "example.txt";
    private static final String TAG = "MainForm";
    private Button btn_enviar;
    private EditText mEditText;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        btn_enviar = findViewById(R.id.btns_save);
        mEditText =  findViewById(R.id.edittext_desc);//ID del texto de como te siente
        ratingBar = findViewById(R.id.rating);
        btn_enviar = findViewById(R.id.btns_save);
/*
        ListaE = new ArrayList<>();
        ListaE.add(new JsonEmotion("normal", 5, "2022-05-03"));
        ListaE.add(new JsonEmotion("Mas o menos", 3, "2022-05-04"));
        ListaE.add(new JsonEmotion("mal", 2, "2022-05-05"));
        ListaE.add(new JsonEmotion("terrible", 1, "2022-05-06"));
        ListaE.add(new JsonEmotion("nefasto", 0, "2022-05-08"));
        ListaE.add(new JsonEmotion("terrible", 1, "2022-05-09"));
        ListaE.add(new JsonEmotion("mal", 2, "2022-05-10"));
        ListaE.add(new JsonEmotion("Mas o menos", 3, "2022-05-11"));
        ListaE.add(new JsonEmotion("ni fu ni fa", 4, "2022-05-12"));
        ListaE.add(new JsonEmotion("normal", 5, "2022-05-13"));
        ListaE.add(new JsonEmotion("ni fu ni fa", 4, "2022-05-14"));
        ListaE.add(new JsonEmotion("Mas o menos", 3, "2022-05-15"));
        ListaE.add(new JsonEmotion("mal", 2, "2022-05-16"));
        ListaE.add(new JsonEmotion("terrible", 1, "2022-05-17"));
*/

        loadData();//crea el array List si no existe en la base de datos o recarga el array list con los previos guardados


        for(int i = 0; i < ListaE.size(); i++){
          Log.d(TAG, "onCreate: desc: " + ListaE.get(i).getDesc()+" nivel de emocion: "+ListaE.get(i).getnEmotion() + " Fecha :" +ListaE.get(i).getFecha());
        }



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Testeo
                //Toast.makeText(MainForm.this,"rating : "+rating,Toast.LENGTH_SHORT).show();
            }
        });

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
              //  Toast.makeText(MainForm.this,"funko pop ",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveData(){

        mEditText =  findViewById(R.id.edittext_desc);
        ratingBar = findViewById(R.id.rating);
        insertItem(mEditText.getText().toString(),ratingBar.getRating());

        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ListaE);
        editor.putString("task list",json);
        editor.apply();
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

    private void insertItem(String arg1, float arg2 ) {
        LocalDate today = LocalDate.now();
        LocalDate d1 = LocalDate.parse(ListaE.get(ListaE.size()-1).getFecha(), DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(d1, today);
        int days = Math.abs(period.getDays());

        if(days >= 1) {
            ListaE.add(new JsonEmotion(arg1, arg2, today.toString()));
        } else {
            ListaE.get(ListaE.size()-1).setDesc(arg1);
            ListaE.get(ListaE.size()-1).setnEmotion(arg2);
            ListaE.get(ListaE.size()-1).setFecha(today.toString());
        }


    }


    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_main);
        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);
        dialog.show();

    }



}