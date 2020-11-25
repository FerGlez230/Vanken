package com.example.vanken.ui.tecnicos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.Adaptadores.AdaptadorItemTecnico;
import com.example.vanken.Modelos.Tecnico;
import com.example.vanken.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TecnicosFragment extends Fragment {

    private TecnicosViewModel tecnicosViewModel;
    private RecyclerView recyclerViewTecnicos;
    private AdaptadorItemTecnico adaptador;
    private ArrayList<Tecnico> tecnicos;//Solo para probar
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tecnicosViewModel =
                ViewModelProviders.of(this).get(TecnicosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tecnicos, container, false);
        recyclerViewTecnicos=(RecyclerView)root.findViewById(R.id.recyclerListaTecnicos);
        //Codigo generado
        tecnicosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        setListaRecycler();
        return root;
    }
   private void setListaRecycler() {
    //DATOS DE PRUEBA
        tecnicos=new ArrayList<Tecnico>();
        Tecnico t=new Tecnico("María Fernanda Gonzalez", "3311083607",5);
        tecnicos.add(t);
        t=new Tecnico("Ana Sarai Escamilla", "3324357809",4.8);
        tecnicos.add(t);
        t=new Tecnico("Grecia Isabel Lasso", "3309788872",4);
        tecnicos.add(t);

        recyclerViewTecnicos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adaptador=new AdaptadorItemTecnico(tecnicos);
        recyclerViewTecnicos.setAdapter(adaptador);
}
}
