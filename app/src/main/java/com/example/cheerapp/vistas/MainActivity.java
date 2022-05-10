package com.example.cheerapp.vistas;

import com.example.cheerapp.clases.Usuario;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cheerapp.R;
import com.example.cheerapp.clases.UsuarioLocal;

public class MainActivity extends AppCompatActivity {

    Button btnLoginView;
    TextView tVDatos;
    UsuarioLocal usuarioLocal;

    private static int SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        usuarioLocal = new UsuarioLocal(this);

        tVDatos = (TextView) findViewById(R.id.tViewDatos);

        // Handler que controla si está logeado un usuario.
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME,0);
                boolean estaLog = sharedPreferences.getBoolean("estaLog", false);

                // Si no está logeado, se abre la vista de login.
                if(!estaLog){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();


                // Si está logeado, hace lo siguiente:
                }else{

                    Toast.makeText(MainActivity.this, sharedPreferences.getString("Usuario", null), Toast.LENGTH_SHORT).show();

                }
            }
        }, SPLASH_TIME_OUT=0);*/

        btnLoginView=(Button) (findViewById(R.id.btnLoginView));

        btnLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogear();
            }
        });

        if(autentificar()==true){
            mostrarDatosUsuario();
        }else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }




    }




    private boolean autentificar(){
        return usuarioLocal.getLoggedUser();
    }

    private void mostrarDatosUsuario(){

        Usuario usuario = usuarioLocal.getDatosUser();
        tVDatos.setText(usuario.emailUsuario);
    }




    public void deslogear(){

        usuarioLocal.limpiarDatosUser();
        usuarioLocal.logear(false);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}