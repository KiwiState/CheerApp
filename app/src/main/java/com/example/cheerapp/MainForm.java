package com.example.cheerapp;

import androidx.annotation.Nullable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cheerapp.clases.Usuario;
import com.example.cheerapp.clases.UsuarioLocal;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainForm extends AppCompatActivity {
    ArrayList<JsonEmotion> ListaE;
    private static final String FILE_NAME = "example.txt";
    private static final String TAG = "MainForm";
    private Button btn_enviar;
    private Button btn_dbug;
    private EditText mEditText;
    private RatingBar ratingBar;
    private String numeroUser;
    UsuarioLocal usuarioLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        btn_enviar = findViewById(R.id.btns_save);
        mEditText =  findViewById(R.id.edittext_desc);//ID del texto de como te siente
        ratingBar = findViewById(R.id.rating);
        btn_enviar = findViewById(R.id.btns_save);
        btn_dbug = findViewById(R.id.btns_load);
        usuarioLocal = new UsuarioLocal(this);
        fetchNumeroUser("http://144.22.35.197/fetchNumero.php");

        loadData();//crea el array List si no existe en la base de datos o recarga el array list con los previos guardados
        for(int i = 0; i < ListaE.size(); i++){
        //  Log.d(TAG, "onCreate: desc: " + ListaE.get(i).getDesc()+" nivel de emocion: "+ListaE.get(i).getnEmotion() + " Fecha :" +ListaE.get(i).getFecha());
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
                //saveData();
                Toast.makeText(MainForm.this,"funko pop ",Toast.LENGTH_SHORT).show();
                ingresarEstadoEmocional("http://144.22.35.197/AgregarEmocion.php");
            }
        });

        btn_dbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveData();
                //deleted_and_save();
                //Toast.makeText(MainForm.this,"deleted",Toast.LENGTH_SHORT).show();
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

    // Método para ingresar un estado emocional a la base de datos
    private void ingresarEstadoEmocional(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(MainForm.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response=response.trim();
                if(response.equals("EMOCION INSERTADA")){

                    Toast.makeText(MainForm.this, "EMOCION INSERTADA CON ÉXITO", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(MainForm.this, "ERROR EN LA INSERCIÓN", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainForm.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                float f = ratingBar.getRating();
                params.put("estadoEmocional",Float.toString(f));
                params.put("dscpEstado", mEditText.getText().toString());
                params.put("numeroInsertEmocion", numeroUser);
                params.put("correoInsertEmocion", usuario.emailUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    //Método que obtiene el número de celular del usuario logeado.
    //Se utiliza para hacer el INSERT de la emoción, ya que requiere de ese dato (clave foránea).
    private void fetchNumeroUser(String URL) {


        RequestQueue requestQueue = Volley.newRequestQueue(MainForm.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.trim();
                numeroUser=response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainForm.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("correoConsulta", usuario.emailUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }



}