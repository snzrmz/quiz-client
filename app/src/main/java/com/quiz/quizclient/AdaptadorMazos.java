package com.quiz.quizclient;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Mazo;

import java.util.List;

public class AdaptadorMazos extends RecyclerView.Adapter<AdaptadorMazos.ViewHolderDatos> {

    List<Mazo> mazos;
    Context context;

    //Set de colores y diseño basado en:
    //https://stackoverflow.com/questions/51244866/set-recyclerview-item-background-color-repeatedly
    public String[] mColors = {"#3F51B5", "#FF9800", "#009688", "#673AB7"};

    public AdaptadorMazos(Context context, List<Mazo> mazos) {
        this.context = context;
        this.mazos = mazos;
    }

    public void setMazoList(List<Mazo> mazos) {
        this.mazos = mazos;
        notifyDataSetChanged();
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
        holder.nombre.setText(mazos.get(position).getNombre());
        holder.contador.setText(String.valueOf(mazos.get(position).getContador()));
        holder.itemView.setLongClickable(true);
        holder.cardview.setCardBackgroundColor(Color.parseColor(mColors[position % mColors.length]));
        Log.println(Log.DEBUG, "LOG_ADAPTER", mazos.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        if (mazos != null) {
            return mazos.size();
        }
        return 0;
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView contador;
        CardView cardview;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtNombreMazo);
            contador = (TextView) itemView.findViewById(R.id.txtContador);
            cardview = itemView.findViewById(R.id.contenedorView);
        }
    }
}
