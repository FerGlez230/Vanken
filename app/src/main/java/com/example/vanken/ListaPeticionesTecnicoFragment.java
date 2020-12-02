package com.example.vanken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.vanken.Modelos.ItemPeticionTecnico_Modelo;
import com.example.vanken.Modelos.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

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
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("user.dat",MODE_PRIVATE);
        cargarLista(sharedPreferences.getString("ssid"," "));

        return v;
    }

    public void cargarLista(String ssid){
        final ArrayList<ItemPeticionTecnico_Modelo> auxList = new ArrayList<>();
        VolleySingleton volleySingleton = null;
        Map map;
        String url="https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        map=new HashMap();
        map.put("funcion", "peticionesTecnico");
        JSONObject params=new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    if(jsonObject.getBoolean("respuesta")){
                                        JSONArray jsonArray;
                                        try {
                                            Toast.makeText(getContext(),jsonObject.toString(),Toast.LENGTH_LONG).show();
                                            jsonArray = jsonObject.getJSONArray("lista");
                                            Toast.makeText(getContext(),jsonArray.toString(),Toast.LENGTH_LONG).show();
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject objectAux = jsonArray.getJSONObject(i);

                                                modelo.add(new ItemPeticionTecnico_Modelo(objectAux.getString("nombreCliente"),objectAux.getString("domicilio"),
                                                        objectAux.getString("fecha"),objectAux.getInt("id"),objectAux.getString("tipoServicio"),
                                                        objectAux.getLong("latitud"),objectAux.getLong("longitud"),objectAux.getString("problema")));

                                                //ItemPeticionTecnico_Modelo item = new ItemPeticionTecnico_Modelo("Ana", "Loma Tuzantla #8877 Loma Dorada", "2020/11/30", 1, "Reparación Impresora", -1445566432, 39092111, "No imprime a color, se apaga sola");
                                                //modelo.add(i + 1, item);

                                                /*modelo.add(new ItemPeticionTecnico_Modelo(objectAux.getString("nombreCliente"), objectAux.getString("domicilio"),
                                                        objectAux.getString("fecha"),objectAux.getInt("id"),
                                                        objectAux.getString("tipoServicio"),-1445566432, 39092111,objectAux.getString("comentariot")));*/
                                            }
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
                                //Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
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