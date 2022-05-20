package com.example.cheerapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;



public class ListAdapter extends ArrayAdapter<JsonEmotion> {
    private List<JsonEmotion> mList;
    private Context mContext;
    private int resourceLayout;

    public ListAdapter(@NonNull Context context, int resource,List<JsonEmotion> objects){
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

        JsonEmotion array = mList.get(position);
       // ImageView imagen = view.findViewById(R.id.icon);//no todavia
       // imagen.setImageResource(jsonEmotion.getnEmotion());

        TextView TextFecha = view.findViewById(R.id.str_fecha);
        TextFecha.setText("Fecha : "+array.getFecha());

        TextView TextDesc = view.findViewById(R.id.secondLine);
        TextDesc.setText(array.getDesc());

        ImageView img = view.findViewById(R.id.icon);

        if(array.getnEmotion() >= 4.5){
            img.setImageResource(R.drawable.spr_faces_1);
        } else if( array.getnEmotion() >= 3.5) {
            img.setImageResource(R.drawable.spr_faces_2);
        } else if( array.getnEmotion() >= 2.5) {
            img.setImageResource(R.drawable.spr_faces_3);
        } else if( array.getnEmotion() >= 1.5) {
            img.setImageResource(R.drawable.spr_faces_4);
        } else  {
            img.setImageResource(R.drawable.spr_faces_5);
        }


        return view;
    }
}
