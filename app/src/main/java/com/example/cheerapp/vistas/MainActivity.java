package com.example.cheerapp.vistas;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cheerapp.clases.Usuario;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cheerapp.R;
import com.example.cheerapp.clases.UsuarioLocal;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnLoginView, btnEmotionTest;
    TextView txtVNombreSaludo;
    UsuarioLocal usuarioLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioLocal = new UsuarioLocal(this);

        txtVNombreSaludo = (TextView) (findViewById(R.id.txtVNombreSaludo));
        btnLoginView = (Button) (findViewById(R.id.btnLoginView));
        btnEmotionTest = (Button) (findViewById(R.id.bttnEmocionTest));

        btnEmotionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, EmotionTest.class));
                finish();
            }
        });

        btnLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deslogear();
            }
        });

        if(!autentificar()){

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        fetchNombre("http://144.22.35.197/fetchNombre.php");

    }
    //Método para consultar el nombre según el correo del usuario logeado.
    //Invocación con URL >> fetchNombre("http://144.22.35.197/fetchNombre.php");
    private void fetchNombre(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.trim();

                if(!response.equals("")){

                    txtVNombreSaludo.setText(response.toString() + "!");
                }else{
                    System.out.println(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("correoNombre", usuario.emailUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    //Método para consultar el safety number según el correo del usuario logeado.
    //Invocación con URL >> consultarSN("http://144.22.35.197/ConsultarSN.php");
    private void consultarSN(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response=response.trim();

                if(!response.equals("")){

                    //El response es el safety number

                }else{

                    Toast.makeText(MainActivity.this, "No SN registered", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("correoSN", usuario.emailUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }
    //Método para consultar apellido según el correo del usuario logeado.
    //Invocación con URL >> consultarApellido("http://144.22.35.197/fetchApellido.php");
    private void consultarApellido(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response=response.trim();

                if(!response.equals("")){

                    //El response es el apellido

                }else{

                    Toast.makeText(MainActivity.this, "Error al consultar apellido", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("correoApellido", usuario.emailUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    //Métodos para mantener la sesión logeada y deslogearse.
    private boolean autentificar(){
        return usuarioLocal.getLoggedUser();
    }

    public void deslogear(){

        usuarioLocal.limpiarDatosUser();
        usuarioLocal.logear(false);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}