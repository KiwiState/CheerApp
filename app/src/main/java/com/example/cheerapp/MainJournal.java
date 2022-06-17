package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainJournal extends AppCompatActivity {

    private ArrayList<JsonEmotion> ListaE;
    private ListView LvDatos;
    ListAdapter mAdapter;

    private List<JsonEmotion> mList = new ArrayList<>();
    private ListAdapter tAdapter;
    private ListView mListView;
    private TextView textEmo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_journal);

        textEmo = (TextView) findViewById(R.id.textEmo);
        //LvDatos = findViewById(R.id.lvDatos);
        loadData();
        //ArrayAdapter<JsonEmotion> adaptador = new ArrayAdapter<JsonEmotion>(MainJournal.this, android.R.layout.simple_list_item_1,ListaE);
        //LvDatos.setAdapter(adaptador);

        textEmo.setPaintFlags(textEmo.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //Nuevo codigo
        mListView = findViewById(R.id.LV);


        for(int i = 0; i < ListaE.size(); i++) {
            mList.add(new JsonEmotion(ListaE.get(i).getDesc(),ListaE.get(i).getnEmotion(),ListaE.get(i).getFecha()));
        }

        tAdapter = new ListAdapter(MainJournal.this,R.layout.item_row,mList);
        mListView.setAdapter(tAdapter);

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
}