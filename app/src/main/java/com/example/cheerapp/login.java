package com.example.cheerapp;

import com.example.cheerapp.clases.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cheerapp.R;
import com.example.cheerapp.clases.UsuarioLocal;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText edTextUser;
    EditText edTextPass;
    Button btnLogin;
    TextView tViewUser, tViewPassword, txtVToRegister;

    UsuarioLocal usuarioLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        edTextUser = (EditText) (findViewById(R.id.edTxtUser));
        edTextPass = (EditText) (findViewById(R.id.edTxtPassword));
        btnLogin = (Button) (findViewById(R.id.btnLogin));
        tViewUser = (TextView) (findViewById(R.id.txtVUser));
        tViewPassword = (TextView) (findViewById(R.id.txtVPassword));
        txtVToRegister = (TextView) (findViewById(R.id.txtVGoToRegister));

        txtVToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, RegisterActivity.class));
                finish();
            }
        });

        edTextUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edTextUser.getText().toString().equals("")){
                        tViewUser.setVisibility(View.VISIBLE);
                    }
                }else{
                    clearUserTextWarning();
                }
            }
        });


        edTextPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (edTextPass.getText().toString().equals("")) {
                        tViewPassword.setVisibility(View.VISIBLE);
                    }
                }else{
                    clearPasswordTextWarning();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCampos()){
                    // Cambiar dirección ip según dónde tenga hosteado el Web Service.
                    validarUser("http://144.22.35.197/login.php");
                }

            }
        });

        usuarioLocal = new UsuarioLocal(this);

    }

    private void validarUser(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(login.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            // Método que evalúa la respuesta del Web Service según los datos ingresados.
            @Override
            public void onResponse(String response) {

                // La respuesta del PHP agrega espacios después del String, por eso se aplica un TRIM()
                response=response.trim();


                if(response.equals("USUARIO ENCONTRADO")){


                    Usuario usuario = new Usuario(edTextUser.getText().toString(), edTextPass.getText().toString());
                    usuarioLocal.setDatosUser(usuario);
                    usuarioLocal.logear(true);

                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                }else if(response.equals("USUARIO NO ENCONTRADO")){
                    Toast.makeText(login.this, "Datos erróneos", Toast.LENGTH_SHORT).show();


                }else{

                    Toast.makeText(login.this, "NO RESPONSE FROM PHP", Toast.LENGTH_SHORT).show();
                    System.out.println(response);



                }
            }

            // Método que captura errores de conexión (en relación al listener).
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            // Método que prepara los parámetros para ser enviados al Web Service
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("username", edTextUser.getText().toString());
                params.put("contra", edTextPass.getText().toString());
                return params;
            }
        };


        requestQueue.add(stringRequest);
    }

    private boolean validarCampos(){

        boolean validador = true;

        if(edTextUser.getText().toString().equals("")){

            tViewUser.setVisibility(View.VISIBLE);
            validador = false;
        }else{
            tViewUser.setVisibility(View.INVISIBLE);
        }

        if(edTextPass.getText().toString().equals("")){

            tViewPassword.setVisibility(View.VISIBLE);
            validador = false;

        }else{
            tViewPassword.setVisibility(View.INVISIBLE);
        }

        return validador;

    }

    private void clearUserTextWarning(){
        tViewUser.setVisibility(View.INVISIBLE);
    }

    private void clearPasswordTextWarning(){
        tViewPassword.setVisibility(View.INVISIBLE);
    }
}