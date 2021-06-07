package com.quiz.quizclient;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quiz.quizclient.modelo.Tarjeta;
import com.quiz.quizclient.restclient.Client;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorTarjetas extends RecyclerView.Adapter<AdaptadorTarjetas.ViewHolderDatos> {

    List<Tarjeta> tarjetas;
    Context context;
    boolean mostrarCorrectas;
    boolean isImageFitToScreen;

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
        if (tarjetas.get(position).getRecursoRuta() != null) {
            holder.imagen.setVisibility(View.VISIBLE);
            establecerImagenTarjeta(tarjetas.get(position).getRecursoRuta(), holder.imagen);
        } else {
            holder.imagen.setVisibility(View.GONE);
        }
        Log.println(Log.DEBUG, "LOG_ADAPTER", String.valueOf(tarjetas.get(position).getRecursoRuta() != null));
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources r = holder.itemView.getContext().getResources();
                int px = Math.round(TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 140, r.getDisplayMetrics()));
                /*
                 *android:layout_height="wrap_content"
                 * android:adjustViewBounds="true"
                 * */
                if (!isImageFitToScreen) {
                    isImageFitToScreen = true;
                    holder.imagen.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    holder.imagen.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = false;
                    holder.imagen.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, px));
                    holder.imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    holder.imagen.setAdjustViewBounds(false);
                }
            }
        });

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

    private void establecerImagenTarjeta(String recursoRuta, ImageView imagen) {
        Log.d("LOG", "cargando imagen: " + Client.BASEURL + "jugadores/perfil/" + recursoRuta);
        Picasso.get().load(Client.BASEURL + "jugadores/perfil/" + recursoRuta).into(imagen);
    }
}
