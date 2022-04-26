package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hsalf.smilerating.SmileRating;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    private Button btn_emo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_emo = findViewById(R.id.btn_emotion);
        btn_emo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainForm.class);
                startActivity(i);
            }
        });



    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_main);

        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);

        dialog.show();

    }

}