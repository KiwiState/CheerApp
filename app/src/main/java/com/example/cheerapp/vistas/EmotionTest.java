package com.example.cheerapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cheerapp.R;
import com.example.cheerapp.clases.Emocion;
import com.example.cheerapp.clases.Usuario;
import com.example.cheerapp.clases.UsuarioLocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmotionTest extends AppCompatActivity implements Emocion.EmocionCallback{

    /* El UsuarioLocal sirve para almacenar un objetio de tipo Usuario y luego acceder a sus atributos para
    poder tener la sesión logeada entre clases y activities */
    UsuarioLocal usuarioLocal;

    Button btnTest;
    TextView txtVTest;

    //En este objeto se guarda el JSONArray que contiene los datos que me pediste (Nombre, Apellido, Estado, Descripcion_Estado y Fecha).
    //Se guarda en el atributo arrayEmocion.
    Emocion emotion = new Emocion();

    String numeroUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_test);
        usuarioLocal = new UsuarioLocal(this);
        btnTest = (Button) (findViewById(R.id.bttnTest));
        txtVTest = (TextView) (findViewById(R.id.txtVTest));
        fetchNumeroUser("http://144.22.35.197/fetchNumero.php");


        //Acción que se ejecuta al apretar el botón de la vista
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamado al método que consigue la información requerida, recibe como parámetro la URL del Web Service que ejecuta la query en SQL.
                //consultarEstadoEmocional("http://144.22.35.197/consulta.php");
                ingresarEstadoEmocional("http://144.22.35.197/AgregarEmocion.php");

            }
        });

    }

    //Método que consigue la información según el correo de la cuenta que esté logeada
    private void consultarEstadoEmocional(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(EmotionTest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Transformación del String que se recibe como respuesta del Web Service a JSONArray.
                    emotion.setArrayEmocion(new JSONArray(response));
                    //Se le pasa el JSONArray que se acaba de transformar a este método (está más abajo) para que ejecute alguna acción con éste.
                    displayEmocion(emotion.getArrayEmocion());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(EmotionTest.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("correoEmocion", usuario.emailUsuario);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    // Método de tipo Callback que recibe el JSONArray desde el Web Service.
    // Este método se ejecuta al momento que se ejecuta el método de arriba, no se ejecuta por si solo.
    @Override
    public void displayEmocion(JSONArray emocion) {
        txtVTest.setText(emocion.toString());

    }


    // Método para ingresar un estado emocional a la base de datos
    private void ingresarEstadoEmocional(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(EmotionTest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response=response.trim();
                if(response.equals("EMOCION INSERTADA")){

                    Toast.makeText(EmotionTest.this, "EMOCION INSERTADA CON ÉXITO", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(EmotionTest.this, "ERROR EN LA INSERCIÓN", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmotionTest.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Usuario usuario = usuarioLocal.getDatosUser();
                Map<String, String> params = new HashMap<String, String>();
                params.put("estadoEmocional", "INGRESAR ACÁ EL VALOR DEL ESTADO EMOCIONAL");
                params.put("dscpEstado", "INGRESAR ACÁ LA DESCRIPCIÓN DEL ESTADO EMOCIONAL");
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


        RequestQueue requestQueue = Volley.newRequestQueue(EmotionTest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.trim();
                numeroUser=response;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(EmotionTest.this, error.toString(), Toast.LENGTH_SHORT).show();

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