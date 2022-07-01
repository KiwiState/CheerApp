package com.example.cheerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.Period;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cheerapp.clases.Emocion;
import com.example.cheerapp.clases.Usuario;
import com.example.cheerapp.clases.UsuarioLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Emocion.EmocionCallback{
    private TextView  txtconsejo;
    private CardView cardcon;
    private Button btn_emo;

    private Button btn_his;
    ArrayList<String> consejitos;
    private Button btn_nayuda;
    private Button btn_DBI;

    private ImageView btn_out;
    private TextView txtVNombreSaludo, txtVSaludo;
    private String num = "+11122223333";

    private String text;
    private String nombre = "Vic";
    private String apellido = "Gan" ;
    private int consejo = 0; // si es uno aparecera un popup consejo aleatoreo
    private int DBI = 0;     // si es positivo se le recomendara al usuario buscar ayuda profesional
    private String checkdbi;

    /* El UsuarioLocal sirve para almacenar un objetio de tipo Usuario y luego acceder a sus atributos para
    poder tener la sesión logeada entre clases y activities */
    UsuarioLocal usuarioLocal;

    //En este objeto se guarda el JSONArray que contiene los datos que me pediste (Nombre, Apellido, Estado, Descripcion_Estado y Fecha).
    //Se guarda en el atributo arrayEmocion.
    Emocion emotion = new Emocion();
    ArrayList<JsonEmotion> ListaE;

    private Integer DBISHOWED = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListaE = new ArrayList<>();
        btn_emo = findViewById(R.id.btn_emotion);
        btn_his = findViewById(R.id.btn_historial);
       // btn_safetyn = findViewById(R.id.btn_SafetyN);
        btn_nayuda = findViewById(R.id.btn_Nayudas);
        btn_DBI = findViewById(R.id.btn_DBI_II);
        btn_out = findViewById(R.id.btn_logout);
        cardcon = findViewById(R.id.cartasconsejos);
        txtconsejo = findViewById(R.id.consejo_id);
        txtVNombreSaludo = findViewById(R.id.txtVSaludo2);
        txtVSaludo = findViewById(R.id.txtVSaludo1);

        fetchNombre("http://144.22.35.197/fetchNombre.php");

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        usuarioLocal = new UsuarioLocal(this);

        ListaE = new ArrayList<>();


        consejosBD();

        if(!autentificar()){

            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();

        }

        btn_emo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                if(btn_emo.getAlpha() != 1){
                    Toast.makeText(MainActivity.this, "La emoción de este día ya fue registrada", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this,MainForm.class);
                    startActivity(i);
                }

            }
        });
        btn_his.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainJournal.class);
                startActivity(i);
            }
        });

        btn_nayuda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,HelpNumbers.class);
                startActivity(i);
            }
        });
        btn_DBI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DBIForm.class);
                startActivity(i);
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deslogear();
            }
        });

        cardcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(cardcon.getAlpha() == 1){
                    animationfadeout();
                }
            }
        });


    }


    private void patronEm(){
        int counterD = 0;                                                                            //Contadores de Dia
        int sumE = 0;                                                                                //Suma de todos los valore de la emociones
        int promE = 0;

                                                                                                     //Toast.makeText(MainActivity.this,"Diffrence between dates is : "+diffDays + "days",Toast.LENGTH_SHORT).show();// se crea el array **solo testeo**
        LocalDate fecha = LocalDate.now();                                                           // se guarda la fecha de hoy

                                                                                                     //dara vuelta el array List para leer de el ultimo a el primero

        for(int i = 0; i < ListaE.size(); i++){                                                      //se hace un for para pasar por todos los valores de el array

            String ArrDate = ListaE.get(i).getFecha();
            LocalDate d1 = LocalDate.parse(ArrDate, DateTimeFormatter.ISO_LOCAL_DATE);
                                                                                                     //dentro de el array se recupera la fecha de el primer puesto del array
            Duration diff = Duration.between(d1.atStartOfDay(), fecha.atStartOfDay());          // se calcula los dias de diferencia entre la fecha guardad y el dia de hoy
            long diffDays = diff.toDays();                                                           //la diferencia de fechas se transforma a dias de diff

            if( diffDays > 13 && i != 0){                                                            //si la differencia de dias es mayor a 13 y i diferente 0 se saldra de el for
                i =ListaE.size();
            } else {
                counterD += 1;                                                                       //se contaran los dias que se registro su nivel de mocion
                sumE += ListaE.get(i).getnEmotion();                                                 //se sumaran todos los estados emocionales en una variable
            }


        }

        if(counterD >= 10){
            promE = sumE/counterD;
            if(promE < 3){
                //dar consejos y numeros de ayuda
                //esta animicamente el mal usuario :
                btn_nayuda.setVisibility (View.VISIBLE);


                //Toast.makeText(MainActivity.this,"El usuario presenta un nivel animico bajo",Toast.LENGTH_SHORT).show();
            }
            if (promE <= 2){
                btn_DBI.setVisibility (View.VISIBLE);
                if(DBISHOWED == 0){
                    animationfadeinDBIAlert();
                }
                // dar formulario DBI
                //esta critico el usuario >;c #noBoobis
                //Toast.makeText(MainActivity.this,"El usuario presenta un nivel animico pauperrimo",Toast.LENGTH_LONG).show();
            }
        } else {

            //Toast.makeText(MainActivity.this,"No hay suficientes datos para hacer un patron",Toast.LENGTH_SHORT).show();
        }
                                                                                                    //revierte a estado original el orden del array
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

    private void consultarEstadoEmocional(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                try {
                    //Transformación del String que se recibe como respuesta del Web Service a JSONArray.
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("emociones");
                    for (int i = 0; i <jsonArray.length(); i++){
                        String desc;
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("NOMBRE");
                        String lastn = object.getString("APELLIDO");
                        String state = object.getString("ESTADO");
                        if(object.getString("DESCRIPCION_ESTADO").equals("null")){
                            desc = "";
                        }else{
                            desc = object.getString("DESCRIPCION_ESTADO");
                        }

                        String ddate = object.getString("FECHA");
                        float f = Float.valueOf(state);
                        ListaE.add(new JsonEmotion(desc, f, ddate));
                    }
                    saveData();
                    patronEm();
                    borrarbtnemocion();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
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


    }

    private void showDialog(){


    }

    private void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences("shared preference",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ListaE);
        editor.putString("task list",json);
        editor.apply();

    }

    private void guardarconsejos(){
        SharedPreferences pf =getSharedPreferences("nconsejo",MODE_PRIVATE);
        SharedPreferences.Editor editor = pf.edit();
        editor = editor.putInt("consejo",0);
        editor.commit();
    }

    private void cargarconsejo(){
        SharedPreferences pf =getSharedPreferences("nconsejo",MODE_PRIVATE);
        consejo = pf.getInt("consejo",0);
    }

    private void guardarDBI(){
        SharedPreferences pf =getSharedPreferences("nDBI",MODE_PRIVATE);
        SharedPreferences.Editor editor = pf.edit();
        editor = editor.putInt("DBI",0);
        editor.commit();
    }

    private void cargarDBI(){
        SharedPreferences pf =getSharedPreferences("nDBI",MODE_PRIVATE);
        DBI = pf.getInt("DBI",0);
    }

    public void cargarNAN(){
        SharedPreferences pf =getSharedPreferences("NAN",MODE_PRIVATE);
        nombre = pf.getString("Nombre","Vicente");
        apellido = pf.getString("Apellido","Gandolfo");
        num = pf.getString("Numero","+00011112222");
    }

    private void borrarbtnemocion(){

            LocalDate today = LocalDate.now();
            LocalDate d1 = LocalDate.parse(ListaE.get(0).getFecha(), DateTimeFormatter.ISO_LOCAL_DATE);
            Period period = Period.between(d1, today);
            int days = Math.abs(period.getDays());
            if(days >= 1) {
                //dia es mayor a uno
            } else {

                btn_emo.setAlpha(0.25f);

            }



    }

    @Override
    protected void onStart() {
        super.onStart();
        ListaE.clear();
        cargarNAN();
        //Toast.makeText(this, nombre +  apellido + num, Toast.LENGTH_SHORT).show();
        consultarEstadoEmocional("http://144.22.35.197/consulta.php");

        cargarconsejo();
        if(consejo > 0) {
            animationfadein();
            guardarconsejos();
        }

        cargarDBI();
        if(DBI > 0){
            text = "Somos Cheer-App, le comentamos que su amigo "+ nombre+ " " + apellido +" lo ha registrado como su número de confianza.\n" +
                    "En estos momentos "+ nombre+ " " + apellido +" no se encuentra emocionalmente bien, le sugerimos que hable con él.";
            //showDialog();
            animationfadeinDBI(DBI);
            if(DBI == 2){
                sendhelp();
            }
            guardarDBI();
        }






    }

    private boolean autentificar(){
        return usuarioLocal.getLoggedUser();
    }

    public void deslogear(){

        usuarioLocal.limpiarDatosUser();
        usuarioLocal.logear(false);
        ListaE.clear();
        saveData();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

    public void sendhelp(){
        boolean installed = isAppInstalled("com.whatsapp");

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+ text));
            startActivity(intent);


    }

    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }

    private void consejosBD(){
        consejitos = new ArrayList<>();
        consejitos.add("Adoptar buenos hábitos de autocuidado y mantener una buena higiene personal mejora el estado animico.");
        consejitos.add("Una buena alimentacion ayuda a mejorar el estado de animo, procure no saltarse las 3 comidas importantes del día (desayuno, almuerzo, cena)");
        consejitos.add("Dormir afecta directamente al estado animico , se recomienda dormir las horas recomendadas para que su estado fisico y mental no se vean afectado.");
        consejitos.add("Recuerde hacer ejercicio regularmente, esto le ayudará a despejar la mente  y mejorará su estado fisico y animico");
        consejitos.add("Manténgase en comunicación, estar alejado de sus cercanos empeora gradualmente su estado emocional.");
        consejitos.add("Tome recesos por cada actividad que haga, en estos descansos haga cualquier tipo de actividad que le paresca placentera.");
        consejitos.add("Intente organizar todas sus actividades por día, mantener un orden en este campo ayudará a no verse influenciado por el estres.");
        consejitos.add("Establesca limites en las actividades que a ud le provoquen diferentes tipos de tension. (trabajo, escuela, universidad)");
        consejitos.add("Si ud lo siente necesario, practique actividades de respiración. Estas funcionan para reducir cualquier tipo de tension y aliviar el estres presente.");
        consejitos.add("No dude en pedir ayuda y en recibir ayuda. Por muy dificil que esto parezca en realidad es la forma más rapida de hallar el problema y resolverlo.");
    }

    private void animationfadein(){
        cardcon.setVisibility (View.VISIBLE);
        cardcon.setAlpha(1);
        Random random = new Random();
        int val = random.nextInt(9);
        txtconsejo.setText(consejitos.get(val));
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fadein);
        cardcon = findViewById(R.id.cartasconsejos);
        anim.reset();
        cardcon.clearAnimation();
        cardcon.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void animationfadeout(){
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fadeout);
        anim.reset();
        cardcon.clearAnimation();
        cardcon.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardcon.setAlpha(0);
                cardcon.setVisibility (View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void animationfadeinDBI(int s){
        cardcon.setVisibility (View.VISIBLE);
        cardcon.setAlpha(1);
        if(s == 2){
            txtconsejo.setText("¡Hola! Hemos detectado que tienes un nivel de depresión alto, te recomendamos  buscar ayuda profesional para evitar que tu estado actual empeore.");
        } else {
            txtconsejo.setText("¡Hola! No hemos detectado un nivel grave de depresión aun si necesitas apoyo consulta nuestros números de apoyo.");
        }

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fadein);
        cardcon = findViewById(R.id.cartasconsejos);
        anim.reset();
        cardcon.clearAnimation();
        cardcon.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void animationfadeinDBIAlert(){
        cardcon.setVisibility (View.VISIBLE);
        cardcon.setAlpha(1);
        txtconsejo.setText("¡Hola!, hemos detectado que has tenido semanas difíciles. El equipo de Cheer-App te recomienda realizar nuestro \"formulario emocional\" para mejorar el tipo de ayuda.");
        DBISHOWED = 1;
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.fadein);
        cardcon = findViewById(R.id.cartasconsejos);
        anim.reset();
        cardcon.clearAnimation();
        cardcon.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

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
}