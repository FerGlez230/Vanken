package com.example.vanken;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vanken.Adaptadores.AdaptadorItemListaPeticionTecnico;
import com.example.vanken.Modelos.ItemPeticionTecnico_Modelo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPeticionesTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPeticionesTecnicoFragment extends Fragment {

    RecyclerView recyclerView;
    AdaptadorItemListaPeticionTecnico adapter;
    ArrayList<ItemPeticionTecnico_Modelo> modelo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ListaPeticionesTecnicoFragment() {
        // Required empty public constructor
    }

    public static ListaPeticionesTecnicoFragment newInstance(String param1, String param2) {
        ListaPeticionesTecnicoFragment fragment = new ListaPeticionesTecnicoFragment();
       Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_peticiones_tecnico, container, false);
        recyclerView = v.findViewById(R.id.recyclerVListPeticionesTecnicos);
        recyclerView.setHasFixedSize(true);
        modelo = new ArrayList<>();

        cargarLista();
        Mostrar();

        return v;
    }

    public void cargarLista(){
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",1,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",2,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",3,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",4,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",5,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",6,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",7,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",8,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
        modelo.add(new ItemPeticionTecnico_Modelo("Ana","Loma Tuzantla #8877 Loma Dorada","2020/11/30",9,"Reparación Impresora",-1445566432, 39092111,"No imprime a color, se apaga sola"));
    }

    public void Mostrar(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdaptadorItemListaPeticionTecnico(getContext(),modelo);
        recyclerView.setAdapter(adapter);
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),DescripcionPeticionTecnicoActivity.class);
                i.putExtra("ID",String.valueOf(modelo.get(recyclerView.getChildAdapterPosition(v)).getNumServicio()));
                i.putExtra("Fecha",modelo.get(recyclerView.getChildAdapterPosition(v)).getFecha());
                i.putExtra("Cliente",modelo.get(recyclerView.getChildAdapterPosition(v)).getCliente());
                i.putExtra("Domicilio",modelo.get(recyclerView.getChildAdapterPosition(v)).getDomicilio());
                i.putExtra("Categoria",modelo.get(recyclerView.getChildAdapterPosition(v)).getCategoria());
                i.putExtra("Comentario",modelo.get(recyclerView.getChildAdapterPosition(v)).getComentario());
                i.putExtra("Longitud",String.valueOf(modelo.get(recyclerView.getChildAdapterPosition(v)).getLongitud()));
                i.putExtra("Latitud",String.valueOf(modelo.get(recyclerView.getChildAdapterPosition(v)).getLatitud()));
                startActivity(i);
            }
        });
    }
}