package com.example.cheerapp.vistas;

import com.example.cheerapp.clases.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText edTextUser;
    EditText edTextPass;
    Button btnLogin;

    UsuarioLocal usuarioLocal;

    /* String que contiene los datos de auth
    public static String PREFS_NAME="MyPrefsFile";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edTextUser = (EditText) (findViewById(R.id.edTxtUser));
        edTextPass = (EditText) (findViewById(R.id.edTxtPassword));
        btnLogin = (Button) (findViewById(R.id.btnLogin));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Cambiar dirección ip según dónde tenga hosteado el Web Service.

                validarUser("http://192.168.1.94:80/login.php");
            }
        });

        usuarioLocal = new UsuarioLocal(this);

    }

    private void validarUser(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            // Método que evalúa la respuesta del Web Service según los datos ingresados.
            @Override
            public void onResponse(String response) {


                if(response.equals("USUARIO ENCONTRADO\t  ")){

                    /* // Parámetros que editan el PREFS_NAME para indicar que hay una cuenta logeada.
                    SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Usuario", edTextUser.getText().toString());
                    editor.putBoolean("estaLog", true);
                    editor.commit();

                    */

                    Usuario usuario = new Usuario(edTextUser.getText().toString(), edTextPass.getText().toString());
                    usuarioLocal.setDatosUser(usuario);
                    usuarioLocal.logear(true);


                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();


                }else if(response.equals("USUARIO NO ENCONTRADO\t  ")){
                    Toast.makeText(LoginActivity.this, "Datos erróneos", Toast.LENGTH_SHORT).show();


                }else{

                    Toast.makeText(LoginActivity.this, "NO RESPONSE FROM PHP", Toast.LENGTH_SHORT).show();



                }
            }

            // Método que captura errores de conexión (en relación al listener).
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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
}