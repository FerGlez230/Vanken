package com.example.vanken.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.Modelos.ItemPeticionTecnico_Modelo;
import com.example.vanken.R;

import java.util.ArrayList;

public class AdaptadorItemListaPeticionTecnico extends RecyclerView.Adapter<AdaptadorItemListaPeticionTecnico.ViewHolder> {

    ArrayList<ItemPeticionTecnico_Modelo> modelo;
    LayoutInflater inflater;

    public AdaptadorItemListaPeticionTecnico(Context context,ArrayList<ItemPeticionTecnico_Modelo> modelo){
        this.inflater =  LayoutInflater.from(context);
        this.modelo = modelo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_peticiones_tecnico,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemPeticionTecnico_Modelo model = modelo.get(position);

        holder.servicio.setText(String.valueOf(model.getNumServicio()));
        holder.fecha.setText(model.getFecha());
        holder.cliente.setText(model.getCliente());
        holder.domicilio.setText(model.getDomicilio());
        holder.categoria.setText(model.getCategoria());
    }

    @Override
    public int getItemCount() {
        return modelo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView servicio, fecha, cliente, domicilio, categoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            servicio = itemView.findViewById(R.id.txtIdPeticionItem);
            fecha = itemView.findViewById(R.id.txtFechaPeticionItem);
            cliente = itemView.findViewById(R.id.txtClientePeticionItem);
            domicilio = itemView.findViewById(R.id.txtDomicilioPeticionItem);
            categoria = itemView.findViewById(R.id.txtCategoriaPeticionItem);


        }
    }
}
