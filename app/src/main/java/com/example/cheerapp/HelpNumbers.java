package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HelpNumbers extends AppCompatActivity {
    private ArrayList<JsonHelp> ListaE;

    private List<JsonHelp> mList = new ArrayList<>();
    private ListAdapterNumber tAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_numbers);
        TextView textAyu = (TextView) findViewById(R.id.textAyu);
        textAyu.setPaintFlags(textAyu.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        HelpBD();
        //Nuevo codigo
        mListView = findViewById(R.id.HNBS);

        for(int i = 0; i < ListaE.size(); i++) {
            mList.add(new JsonHelp(ListaE.get(i).getTitulo(),ListaE.get(i).getDescripcion(),ListaE.get(i).getContacto(),ListaE.get(i).getURL()));
        }

        tAdapter = new ListAdapterNumber(HelpNumbers.this,R.layout.helprow,mList);
        mListView.setAdapter(tAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoUrl(ListaE.get(i).getURL());
            }
        });
    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void HelpBD(){
        ListaE = new ArrayList<>();
        ListaE.add(new JsonHelp("FUNDACION JOSE IGNACIO","www.fundacionjoseignacio.org","Fundación sin fines de lucro dedicada a la prevención del suicidio en niñes y jóvenes.","https://www.fundacionjoseignacio.org/"));
        ListaE.add(new JsonHelp("LINEA LIBRE","www.linealibre.cl","Es un Programa que se desarolla por la fundación para la confianza el cual ofrece ayuda Psicologica a niñes y jovenes que necesiten ser escuchados.","https://www.linealibre.cl"));
        ListaE.add(new JsonHelp("CORPORACIÓN CASA DEL CERRO","www.casadelcerro.cl","Corporación que ofrece atención psicológica individual a quienes quieran iniciar un proceso psicoterapéutico realizan intervención psicosocial a nivel familiar y en instituciones privadas y públicas en relación a temática de vulnerabilidad, riesgo social, violencia, derechos humanos y adicciones, entre otras.","https://www.casadelcerro.cl/consultorio/solicitar-hora-de-atencion/"));
        ListaE.add(new JsonHelp("FUNDACIÓN TODO MEJORA ","www.todomejora.org","Atención de temáticas en relación a la discriminación por orientación sexual, identidad y expresión de genero (bullying y conducta suicida).","https://todomejora.org/que-hacemos"));
        ListaE.add(new JsonHelp("PROGRAMA AMANOZ","www.amanoz.cl","Fundación AMANOZ contribuye al bienestar emocional y afectivo de las personas mayores con programas de acompañamiento.","https://www.amanoz.cl"));

    }

}