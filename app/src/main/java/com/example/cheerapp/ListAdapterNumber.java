package com.example.cheerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ListAdapterNumber extends ArrayAdapter<JsonHelp> {
    private List<JsonHelp> mList;
    private Context mContext;
    private int resourceLayout;

    public ListAdapterNumber(@NonNull Context context, int resource, List<JsonHelp> objects){
        super(context,resource,objects);
        this.mList = objects;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(resourceLayout,null);
        }

        JsonHelp array = mList.get(position);
        TextView Texttitulo = view.findViewById(R.id.Tituloh);
        TextView Textcontacto= view.findViewById(R.id.contactoh);
        TextView Textdesc= view.findViewById(R.id.desch);

        Texttitulo.setText(array.getTitulo());
        Textcontacto.setText("Contacto: " + array.getDescripcion());
        Textdesc.setText(array.getContacto());

        return view;
    }
}
