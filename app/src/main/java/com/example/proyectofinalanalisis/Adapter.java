package com.example.proyectofinalanalisis;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.QuejaViewHolder> {

    private List<Quejas> listaQuejas;
    private Context mCtx;

    public Adapter (Context mCtx, List<Quejas> listaQuejas){
        this.mCtx = mCtx;
        this.listaQuejas = listaQuejas;
    }

    @Override
    public QuejaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new QuejaViewHolder (view);
    }

    @Override
    public void onBindViewHolder(QuejaViewHolder holder, int position){
        Quejas queja = listaQuejas.get(position);

        Glide.with(mCtx)
                .load(queja.getImagen())
                .into(holder.imageView);
        holder.textViewDesc.setText(String.valueOf(queja.getDescripcion()));
        holder.textViewEstado.setText(String.valueOf(queja.getEstado()));
        holder.textViewFecha.setText(String.valueOf(queja.getFecha()));
        holder.textViewCategoria.setText(String.valueOf(queja.getIdCategoria()));
    }
    @Override
    public int getItemCount() {return listaQuejas.size();}

    class QuejaViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDesc, textViewEstado, textViewFecha, textViewCategoria;
        ImageView imageView;

        public QuejaViewHolder(View itemView){
            super(itemView);
            textViewDesc = itemView.findViewById(R.id.textDescripcion);
            textViewEstado = itemView.findViewById(R.id.textEstado);
            textViewFecha = itemView.findViewById(R.id.textFecha);
            textViewCategoria = itemView.findViewById(R.id.textCategoria);
            imageView = itemView.findViewById(R.id.imageView7);
        }

    }
}

