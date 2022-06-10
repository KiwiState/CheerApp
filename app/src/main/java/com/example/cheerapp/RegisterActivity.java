package com.example.cheerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edTxtNombre, edTxtApellido, edTxtNumero, edTxtCorreo, edTxtPassword;
    TextView txtVToLogin;
    Button bttnRegistro;

    UsuarioLocal usuarioLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        edTxtNombre = (EditText) (findViewById(R.id.edTxtNombreRegistro));
        edTxtApellido = (EditText) (findViewById(R.id.edTxtApellidoRegistro));
        edTxtNumero = (EditText)  (findViewById(R.id.edTxtNumeroRegistro));
        edTxtCorreo = (EditText) (findViewById(R.id.edTxtCorreoRegistro));
        edTxtPassword = (EditText) (findViewById(R.id.edTxtPasswordRegistro));

        txtVToLogin = (TextView) (findViewById(R.id.txtVGoToLogin));
        txtVToLogin.setPaintFlags(txtVToLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtVToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, login.class));
                finish();
            }
        });

        bttnRegistro = (Button) (findViewById(R.id.bttnRegistro));

        bttnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarCamposRegistro()){
                    registrarUsuario("http://144.22.35.197/crearUsuario.php");
                }
            }
        });

        usuarioLocal = new UsuarioLocal(this);
    }


    private void registrarUsuario(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.trim();

                if(response.equals("USUARIO CREADO")){

                    Usuario usuario = new Usuario(edTxtCorreo.getText().toString(), edTxtPassword.getText().toString());
                    usuarioLocal.setDatosUser(usuario);
                    usuarioLocal.logear(true);

                    startActivity(new Intent(RegisterActivity.this, SafetyNumberActivity.class));
                    finish();

                }else {
                    Toast.makeText(RegisterActivity.this, "Email y número ya registrados", Toast.LENGTH_SHORT).show();
                    System.out.println(response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("nombre", edTxtNombre.getText().toString());
                params.put("apellido", edTxtApellido.getText().toString());
                params.put("numero", edTxtNumero.getText().toString());
                params.put("correo", edTxtCorreo.getText().toString());
                params.put("password", edTxtPassword.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private boolean validarCamposRegistro(){

        if(TextUtils.isEmpty(edTxtNombre.getText().toString()) || TextUtils.isEmpty(edTxtApellido.getText().toString()) || TextUtils.isEmpty(edTxtNumero.getText().toString()) || TextUtils.isEmpty(edTxtCorreo.getText().toString()) ||
                TextUtils.isEmpty(edTxtPassword.getText().toString())){

            Toast.makeText(this, "No deje campos vacíos", Toast.LENGTH_SHORT).show();

            return false;

        }else{
            return true;
        }
    }
}