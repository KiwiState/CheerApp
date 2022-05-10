package com.example.cheerapp.clases;

import com.example.cheerapp.clases.Usuario;

import android.content.Context;
import android.content.SharedPreferences;

public class UsuarioLocal {

    private static final String NOMBRE_PREF="datosUsuario";
    SharedPreferences usuarioLogeado;

    public UsuarioLocal (Context context){
        usuarioLogeado = context.getSharedPreferences(NOMBRE_PREF, 0);
    }

    public void setDatosUser(Usuario usuario){
        SharedPreferences.Editor editor = usuarioLogeado.edit();
        editor.putString("email", usuario.emailUsuario);
        editor.putString("password", usuario.contrasenia);
        editor.commit();
    }

    public Usuario getDatosUser(){
        String email = usuarioLogeado.getString("email", "");
        String pass = usuarioLogeado.getString("password", "");

        Usuario usuarioLogeado = new Usuario(email, pass);

        return usuarioLogeado;
    }

    public void logear(boolean logeado){
        SharedPreferences.Editor editor = usuarioLogeado.edit();
        editor.putBoolean("estaLogeado", logeado);
        editor.commit();

    }

    public boolean getLoggedUser(){
        if(usuarioLogeado.getBoolean("estaLogeado", false)==true){
            return true;
        }else{
            return false;
        }
    }

    public void limpiarDatosUser(){
        SharedPreferences.Editor editor = usuarioLogeado.edit();
        editor.clear();
        editor.commit();
    }
}
