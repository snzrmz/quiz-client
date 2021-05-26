package com.quiz.quizclient;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Tarjeta;

import java.util.List;

public class AdaptadorTarjetas extends RecyclerView.Adapter<AdaptadorTarjetas.ViewHolderDatos> {

    List<Tarjeta> tarjetas;
    Context context;
    boolean mostrarCorrectas;

    public AdaptadorTarjetas(Context context, List<Tarjeta> tarjetas, boolean mostrarCorrectas) {
        this.context = context;
        this.tarjetas = tarjetas;
        this.mostrarCorrectas = mostrarCorrectas;
    }

    public void setTarjetaList(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adaptador_tarjetas, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.pregunta.setText(tarjetas.get(position).getPregunta());
        //holder.respuesta.setText(String.valueOf(tarjetas.get(position).getRespuesta()));
        holder.imagen.setVisibility(tarjetas.get(position).getRecursoRuta() != null ? View.VISIBLE : View.GONE);
        Log.println(Log.DEBUG, "LOG_ADAPTER", String.valueOf(tarjetas.get(position).getRecursoRuta() != null));
        holder.itemView.setLongClickable(true);

        if (mostrarCorrectas) {
            holder.correcta.setVisibility(View.VISIBLE);
            if (tarjetas.get(position).isCorrecta()) {
                holder.correcta.setText("✅");
                holder.lLTarjeta.setBackgroundColor(Color.parseColor("#2883FF00"));
            } else {
                holder.correcta.setText("❌");
                holder.lLTarjeta.setBackgroundColor(Color.parseColor("#29FF0000"));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (tarjetas != null) {
            return tarjetas.size();
        }
        return 0;
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView pregunta;
        ImageView imagen;
        TextView correcta;
        LinearLayout lLTarjeta;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            pregunta = (TextView) itemView.findViewById(R.id.txtPreguntaTarjeta);
            imagen = (ImageView) itemView.findViewById(R.id.iVTarjeta);
            correcta = (TextView) itemView.findViewById(R.id.txtCorrecta);
            lLTarjeta = (LinearLayout) itemView.findViewById(R.id.lLTarjeta);

            //respuesta = (TextView) itemView.findViewById(R.id.txtRespuesta);
        }
    }
}
