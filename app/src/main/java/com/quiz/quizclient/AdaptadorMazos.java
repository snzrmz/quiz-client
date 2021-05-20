package com.quiz.quizclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Mazo;

import java.util.List;

public class AdaptadorMazos extends RecyclerView.Adapter<AdaptadorMazos.ViewHolderDatos> {

    public interface OnItemClickListener {
        void onItemClick(Mazo mazo);
    }

    private final List<Mazo> mazos;
    private final OnItemClickListener listener;

    public AdaptadorMazos(List<Mazo> mazos, OnItemClickListener listener) {
        this.mazos = mazos;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adaptador, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(mazos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mazos.size();
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView contador;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreMazo);
            contador = itemView.findViewById(R.id.txtContador);
        }

        public void asignarDatos(Mazo mazo, OnItemClickListener listener) {
            nombre.setText(mazo.getNombre());
            contador.setText(String.valueOf(mazo.getContador()));
            itemView.setOnClickListener(view -> listener.onItemClick(mazo));
        }
    }
}
