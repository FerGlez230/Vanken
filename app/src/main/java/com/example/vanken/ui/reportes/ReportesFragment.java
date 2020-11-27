package com.example.vanken.ui.reportes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vanken.Adaptadores.AdaptadorItemReporte;
import com.example.vanken.Modelos.Reporte;
import com.example.vanken.R;

import java.util.ArrayList;

public class ReportesFragment extends Fragment {

    private ReportesViewModel reportesViewModel;
    private RecyclerView recyclerViewReportes;
    private AdaptadorItemReporte adaptador;
    private ArrayList<Reporte> reportes;//Solo para probar
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportesViewModel =
                ViewModelProviders.of(this).get(ReportesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reportes, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);
        recyclerViewReportes=(RecyclerView)root.findViewById(R.id.recyclerListaReportes);

        reportesViewModel.getText().observe(this, new Observer<String>() {
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
        reportes=new ArrayList<Reporte>();
        Reporte r=new Reporte("Fernanda","12/11/2020","is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop " +
                "publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        reportes.add(r);
        r=new Reporte("Ana Sarai Escamilla", "17/09/2020","is simply dummy text of the printing and typesetting industry. " +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages," +
                " and more recently with desktop " +
                "publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        reportes.add(r);
        r=new Reporte("Ana Luz Gomez", "17/09/2020","is simply dummy text of the printing and typesetting industry. " +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages," +
                " and more recently with desktop " +
                "publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        reportes.add(r);


        recyclerViewReportes.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adaptador=new AdaptadorItemReporte(reportes);
        recyclerViewReportes.setAdapter(adaptador);
    }
}