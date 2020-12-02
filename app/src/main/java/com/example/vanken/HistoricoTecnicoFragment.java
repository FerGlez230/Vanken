package com.example.vanken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Adaptadores.AdaptadorItemListaPeticionTecnico;
import com.example.vanken.Adaptadores.AdaptadorItemTecnico;
import com.example.vanken.Modelos.ItemPeticionTecnico_Modelo;
import com.example.vanken.Modelos.Persona;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.ui.tecnicos.TecnicosFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricoTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoTecnicoFragment extends Fragment {

    RecyclerView recyclerView;
    AdaptadorItemListaPeticionTecnico adapter;
    ArrayList<ItemPeticionTecnico_Modelo> modelo = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoricoTecnicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricoTecnicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoTecnicoFragment newInstance(String param1, String param2) {
        HistoricoTecnicoFragment fragment = new HistoricoTecnicoFragment();
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
        View v = inflater.inflate(R.layout.fragment_historico_tecnico, container, false);
        recyclerView = v.findViewById(R.id.recyclerHistoricoTecnico);
        recyclerView.setHasFixedSize(true);
        modelo = new ArrayList<>();
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("user.dat",MODE_PRIVATE);
        cargarLista(sharedPreferences.getString("ssid"," "));
        //Mostrar();

        return v;
    }

    public void cargarLista(String ssid){
        final ArrayList<ItemPeticionTecnico_Modelo> auxList = new ArrayList<>();
        VolleySingleton volleySingleton = null;
        Map map;
        String url="https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        map=new HashMap();
        map.put("funcion", "historico");
        map.put("ssid", ssid);
        JSONObject params=new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    if(jsonObject.getBoolean("respuesta")){JSONArray jsonArray;
                                        try {
                                            ArrayList<ItemPeticionTecnico_Modelo> aux = new ArrayList<>();
                                            jsonArray = jsonObject.getJSONArray("lista");
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject objectAux = jsonArray.getJSONObject(i);
                                                //ItemPeticionTecnico_Modelo item = new ItemPeticionTecnico_Modelo("Ana", "Loma Tuzantla #8877 Loma Dorada", "2020/11/30", 1, "Reparación Impresora", -1445566432, 39092111, "No imprime a color, se apaga sola");
                                                //modelo.add(i + 1, item);

                                                modelo.add(new ItemPeticionTecnico_Modelo(objectAux.getString("nombreCliente"), objectAux.getString("domicilio"),
                                                        objectAux.getString("fecha"),objectAux.getInt("id"),
                                                        objectAux.getString("tipoServicio"),-1445566432, 39092111,objectAux.getString("comentariot")));
                                            }
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                            adapter = new AdaptadorItemListaPeticionTecnico(getContext(),modelo);
                                            recyclerView.setAdapter(adapter);
                                     } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        Toast.makeText(getContext(),"No hay respuesta",Toast.LENGTH_LONG).show();
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
        try{
            for (int i = 0; i < modelo.size(); i++){
                Toast.makeText(getContext(), i, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}