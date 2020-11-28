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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.Adaptadores.AdaptadorItemTecnico;
import com.example.vanken.Modelos.Persona;
import com.example.vanken.R;

import java.util.ArrayList;

public class TecnicosFragment extends Fragment {

    private TecnicosViewModel tecnicosViewModel;
    private RecyclerView recyclerViewTecnicos;
    private AdaptadorItemTecnico adaptador;
    private ArrayList<Persona> personas;//Solo para probar
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
            }
        });
        setListaRecycler();
        return root;
    }
   private void setListaRecycler() {
        personas =new ArrayList<Persona>();
        Persona t=new Persona(1,"María Fernanda Gonzalez", "3311083607",5);
        personas.add(t);
        t=new Persona(2,"Ana Sarai Escamilla", "3324357809",4.8);
        personas.add(t);
        t=new Persona(3,"Grecia Isabel Lasso", "3309788872",4);
        personas.add(t);

        recyclerViewTecnicos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adaptador=new AdaptadorItemTecnico(personas);
       adaptador.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle bundle=new Bundle();
               bundle.putInt("id", personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getId());
               Toast.makeText(getContext(),personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_SHORT ).show();
               Fragment fragment=new DetalleTecnicoFragment();
               fragment.setArguments(bundle);
               NavHostFragment.findNavController(TecnicosFragment.this).navigate(R.id.detalleTecnico);


           }
       });
        recyclerViewTecnicos.setAdapter(adaptador);
}

}
