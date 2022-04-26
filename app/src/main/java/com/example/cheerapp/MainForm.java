package com.example.cheerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainForm extends AppCompatActivity {
    private Button btn_enviar;
    private EditText edit_text;
    private String strDesc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        btn_enviar = findViewById(R.id.btns_save);
        edit_text = (EditText) findViewById(R.id.edittext_desc);
        strDesc = edit_text.getText().toString();

        btn_enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
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