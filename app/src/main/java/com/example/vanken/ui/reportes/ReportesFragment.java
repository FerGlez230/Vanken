package com.example.vanken.ui.reportes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Adaptadores.AdaptadorItemReporte;
import com.example.vanken.Adaptadores.AdaptadorItemTecnico;
import com.example.vanken.Modelos.Persona;
import com.example.vanken.Modelos.Reporte;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.R;
import com.example.vanken.ui.tecnicos.TecnicosFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportesFragment extends Fragment {

    private ReportesViewModel reportesViewModel;
    private RecyclerView recyclerViewReportes;
    private AdaptadorItemReporte adaptador;
    private ArrayList<Reporte> reportes;//Solo para probar
    private VolleySingleton volleySingleton;
    private Switch aSwitch;
    private Map map;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportesViewModel =
                ViewModelProviders.of(this).get(ReportesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reportes, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);
        recyclerViewReportes=(RecyclerView)root.findViewById(R.id.recyclerListaReportes);
        aSwitch=(Switch)root.findViewById(R.id.switchReportes);
        map=new HashMap();
        map.put("funcion", "listaQuejasCliente");
        llamarWebService(map);
        //aSwitch.setChecked(false);
        //Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    //recyclerViewReportes.invalidate();
                    map.clear();
                    map.put("funcion", "listaQuejasTecnico");
                    //map.put("tipo", "Tecnico");
                    llamarWebService(map);
                    Toast.makeText(getContext(), "Activo", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "inactivo", Toast.LENGTH_SHORT).show();
                    //recyclerViewReportes.invalidate();
                    map.clear();
                    map.put("funcion", "listaQuejasCliente");
                    //map.put("tipo", "Cliente");
                    llamarWebService(map);
                }
            }
        });
        reportesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        return root;
    }
    public void llamarWebService(Map<String, String> map){
        String url="https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        JSONObject jo=new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                //Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                                try {
                                   // Toast.makeText(getContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();

                                    if(jsonObject.getBoolean("respuesta")){
                                        setListaRecycler(jsonObject);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause=error.toString();
                                Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
                            }
                        })
        );
    }
    private void setListaRecycler(JSONObject jsonObject) {
        //DATOS DE PRUEBA
        Reporte reporte;
        JSONArray jsonArray;
        reportes=new ArrayList<Reporte>();
        try {
            jsonArray = jsonObject.getJSONArray("lista");
            Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < jsonArray.length(); i++) {

                //Toast.makeText(getContext(), Double.toString( cal), Toast.LENGTH_SHORT).show();
                reporte = new Reporte(
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("idPeticion")),
                        jsonArray.getJSONObject(i).getString("nombre").concat(" ").concat(jsonArray.getJSONObject(i).getString("apellidos")),
                        jsonArray.getJSONObject(i).getString("fecha"),
                        jsonArray.getJSONObject(i).getString("motivo"));
                reportes.add(reporte);
            }

            recyclerViewReportes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerViewReportes.addItemDecoration(new DividerItemDecoration(
                    getContext(),
                    DividerItemDecoration.VERTICAL));
            adaptador = new AdaptadorItemReporte(reportes);
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    //bundle.putInt("id", personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getId());
                    //NavHostFragment.findNavController(ReportesFragment.this).navigate(R.id.detalleTecnico,bundle);
                }
            });
            recyclerViewReportes.setAdapter(adaptador);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}