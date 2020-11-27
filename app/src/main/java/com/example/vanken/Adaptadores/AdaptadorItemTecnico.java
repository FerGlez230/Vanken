package com.example.vanken.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.Modelos.Persona;
import com.example.vanken.R;

import java.util.ArrayList;

public class AdaptadorItemTecnico extends RecyclerView.Adapter<AdaptadorItemTecnico.ViewHolderDatos>
        implements View.OnClickListener{
    ArrayList<Persona> arrayList;
    Context context;
    private View.OnClickListener listener;
    public AdaptadorItemTecnico(ArrayList<Persona> arrayList) {
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
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tecnico,null,false);
        view.setOnClickListener(this);
        context=parent.getContext();
        return new AdaptadorItemTecnico.ViewHolderDatos(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
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
        TextView nombre,telefono, cal;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.textViewNombreTecnicoItemList);
            telefono=itemView.findViewById(R.id.textViewTelefonoTecnicoItemList);
            cal=itemView.findViewById(R.id.textViewCalificacionTecnicoItemList);
        }

        public void asignarDatos(Persona persona) {
            nombre.setText(persona.getNombre());
            telefono.setText(persona.getNumero());
            cal.setText((Double.toString(persona.getCalificacion())));
        }
    }
}
