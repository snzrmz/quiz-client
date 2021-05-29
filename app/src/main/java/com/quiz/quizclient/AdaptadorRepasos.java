package com.quiz.quizclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Repaso;
import com.quiz.quizclient.modelo.Tarjeta;

import java.util.List;

public class AdaptadorRepasos extends RecyclerView.Adapter<AdaptadorRepasos.ViewHolderDatos> {

    List<Repaso> repasos;
    List<Tarjeta> tarjetas;
    Context context;

    public AdaptadorRepasos(Context context, List<Repaso> repasos, List<Tarjeta> tarjetas) {
        this.context = context;
        this.repasos = repasos;
        this.tarjetas = tarjetas;
    }

    public void setRepasosList(List<Repaso> repasos) {
        this.repasos = repasos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_adaptador_repasos, parent, false);
        return new ViewHolderDatos(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.acertadas.setText(String.valueOf(repasos.get(position).getTarjetaRepasoAcertado().size()));
        holder.falladas.setText(String.valueOf(repasos.get(position).getTarjetaRepasoFallado().size()));
        holder.fechaHoraInicio.setText(repasos.get(position).getFechaDiferencia());
        holder.itemView.setClickable(true);
        // Log.d("LOG_ADAPTER", repasos.get(position).getFechaHoraInicio().toString());
    }

    @Override
    public int getItemCount() {
        if (repasos != null) {
            return repasos.size();
        }
        return 0;
    }

    public static class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView acertadas;
        TextView falladas;
        TextView fechaHoraInicio;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            acertadas = itemView.findViewById(R.id.txtAcertadas);
            falladas = itemView.findViewById(R.id.txtFalladas);
            fechaHoraInicio = itemView.findViewById(R.id.txtFechaHoraInicio);
        }
    }
}