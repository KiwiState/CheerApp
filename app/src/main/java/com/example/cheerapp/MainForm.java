package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

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

        loadData();//crea el array List si no existe en la base de datos o recarga el array list con los previos guardados
        //ListaE.add(new JsonEmotion("me siento muy triste",0));
        for(int i = 0; i < ListaE.size(); i++){
            Log.d(TAG, "onCreate: desc: " + ListaE.get(i).getDesc()+" nivel de emocion: "+ListaE.get(i).getnEmotion());
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
        insertItem(mEditText.getText().toString(),ratingBar.getRating(),LocalDate.now());

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

    private void insertItem(String arg1, float arg2, LocalDate arg3) {
        ListaE.add(new JsonEmotion(arg1, arg2, arg3));
    }

    /// abajo es internal storage

    public  void save(View v) throws IOException {
        String text = mEditText.getText().toString();// texto de como te siente
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos.write(text.getBytes());
            mEditText.getText().clear();
            Toast.makeText(this,"Saved to"+ getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try{
                    fos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public  void load(View v) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(fis != null){
                try{
                    fis.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_main);
        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);
        dialog.show();

    }



}