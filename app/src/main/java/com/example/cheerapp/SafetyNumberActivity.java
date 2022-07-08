package com.example.cheerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.cheerapp.clases.Usuario;
import com.example.cheerapp.clases.UsuarioLocal;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class SafetyNumberActivity extends AppCompatActivity {

    UsuarioLocal usuarioLocal;

    EditText edTxtNombreSN1, edTxtSN1;
    TextView txtVOmitirSN1;
    Button bttnAgregarSN1;

    String numeroUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_number);
        usuarioLocal = new UsuarioLocal(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edTxtNombreSN1 = (EditText) (findViewById(R.id.edTxtNombrePrimerSN));
        edTxtSN1 = (EditText) (findViewById(R.id.edTxtNroPrimerSN));

        txtVOmitirSN1 = (TextView) (findViewById(R.id.txtVOmitirSN));

        bttnAgregarSN1 = (Button) (findViewById(R.id.bttnAgregarSN));

        txtVOmitirSN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SafetyNumberActivity.this, MainActivity.class));
                finish();
            }
        });

        bttnAgregarSN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchNumeroUser("http://144.22.35.197/fetchNumero.php");

            }
        });

    }


    private void agregarSN(String URL) {
        RequestQueue requestQueue = Volley.newRequestQueue(SafetyNumberActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.trim();

                if (response.equals("SN INSERTADO")) {

                    startActivity(new Intent(SafetyNumberActivity.this, MainActivity.class));
                    finish();

                } else {

                    Toast.makeText(SafetyNumberActivity.this, "Safety Number ya existente", Toast.LENGTH_SHORT).show();
                    System.out.println(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SafetyNumberActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombreSN", edTxtNombreSN1.getText().toString());
                params.put("numeroUsuario", numeroUser);
                params.put("correoUsuario", usuario.emailUsuario);
                params.put("sfnumber", edTxtSN1.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    private void fetchNumeroUser(String URL) {


        RequestQueue requestQueue = Volley.newRequestQueue(SafetyNumberActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.trim();
                numeroUser=response;
                agregarSN("http://144.22.35.197/addSN.php");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SafetyNumberActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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