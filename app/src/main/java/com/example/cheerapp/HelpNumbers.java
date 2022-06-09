package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HelpNumbers extends AppCompatActivity {
    private ArrayList<JsonHelp> ListaE;
    private ListView LvDatos;
    ListAdapterNumber mAdapter;

    private List<JsonHelp> mList = new ArrayList<>();
    private ListAdapterNumber tAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_numbers);
        HelpBD();
        //Nuevo codigo
        mListView = findViewById(R.id.HNBS);

        for(int i = 0; i < ListaE.size(); i++) {
            mList.add(new JsonHelp(ListaE.get(i).getTitulo(),ListaE.get(i).getDescripcion(),ListaE.get(i).getContacto()));
        }

        tAdapter = new ListAdapterNumber(HelpNumbers.this,R.layout.helprow,mList);
        mListView.setAdapter(tAdapter);
    }

    private void HelpBD(){
        ListaE = new ArrayList<>();
        ListaE.add(new JsonHelp("FUNDACION JOSE IGNACIO","contacto@fundacionjoseignacio.org","Fundación sin fines de lucro dedicada a la prevención del suicidio en niñes y jóvenes."));
        ListaE.add(new JsonHelp("LINEA LIBRE","Llamar al 1515 www.linealibre.cl ","Es un Programa que se desarolla por la fundación para la confianza el cual ofrece ayuda Psicologica a niñes y jovenes que necesiten ser escuchados."));
        ListaE.add(new JsonHelp("CORPORACIÓN CASA DEL CERRO","https://www.casadelcerro.cl/consultorio/solicitar-hora-de-atencion/","Corporación que ofrece atención psicológica individual a quienes quieran iniciar un proceso psicoterapéutico realizan intervención psicosocial a nivel familiar y en instituciones privadas y públicas en relación a temática de vulnerabilidad, riesgo social, violencia, derechos humanos y adicciones, entre otras."));
        ListaE.add(new JsonHelp("FUNDACIÓN TODO MEJORA ","apoyo@todomejora.org","Atención de temáticas en relación a la discriminación por orientación sexual, identidad y expresión de genero (bullying y conducta suicida)."));
        ListaE.add(new JsonHelp("CAPS, CLINICA DE LA UNIVERSIDAD MAYOR","estrella.contreras@umayor.cl 22 555 2992","Los servicios que ofrece CAPs son gratuitos para la comunidad, independiente de su situación previsional y están dirigidos \n" +
                "a adultos, niños, parejas y familias."));
    }

}