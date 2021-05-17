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

    private final List<Mazo> mazos;

    public AdaptadorMazos(List<Mazo> mazos) {
        this.mazos = mazos;
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
        holder.asignarDatos(mazos.get(position));
    }

    @Override
    public int getItemCount() {
        return mazos.size();
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView autor;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreMazo);
            //autor= itemView.findViewById(R.id.txtAutor);
        }

        public void asignarDatos(Mazo mazo) {
            nombre.setText(mazo.getNombre());
            //TODO sacar autor
            //autor.setText(mazo.getAutor());
        }
    }
}
