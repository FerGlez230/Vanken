package com.example.vanken.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.Modelos.Reporte;
import com.example.vanken.R;

import java.util.ArrayList;

public class AdaptadorItemReporte extends RecyclerView.Adapter<AdaptadorItemReporte.ViewHolderDatos>
        implements View.OnClickListener{
    ArrayList<Reporte> arrayList;
    Context context;
    private View.OnClickListener listener;
    private int position=0;
    public AdaptadorItemReporte(ArrayList<Reporte> arrayList) {
        this.arrayList = arrayList;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }
    @NonNull
    @Override
    public AdaptadorItemReporte.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reporte,null,false);
        view.setOnClickListener(this);
        context=parent.getContext();
        return new AdaptadorItemReporte.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if(position==1) {
            holder.itemView.setBackgroundColor(Color.rgb(240,240,240));

        }
        holder.asignarDatos(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void setOnClickListener(View.OnClickListener listener){//asociamos el listener del fragment al que estamos
        this.listener=listener;                                     //construyend
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombre,fecha, cuerpo;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.textViewNombreReporteItemList);
            fecha=itemView.findViewById(R.id.textViewFechaReporteItemList);
            cuerpo=itemView.findViewById(R.id.textViewCuerpoReporteItemList);
        }

        public void asignarDatos(Reporte reporte) {

            nombre.setText(reporte.getNombre());

            fecha.setText(reporte.getFecha());
            cuerpo.setText(reporte.getCuerpo());
            if(position==1){
                nombre.setTextColor(Color.DKGRAY);
                fecha.setTextColor(Color.DKGRAY);
                cuerpo.setTextColor(Color.GRAY);
                position=0;}else position=1;
        }
    }
}
