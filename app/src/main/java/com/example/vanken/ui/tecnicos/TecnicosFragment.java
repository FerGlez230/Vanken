package com.example.vanken.ui.tecnicos;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.vanken.Adaptadores.AdaptadorItemTecnico;
import com.example.vanken.Modelos.Persona;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TecnicosFragment extends Fragment {

    private TecnicosViewModel tecnicosViewModel;
    private RecyclerView recyclerViewTecnicos;
    private AdaptadorItemTecnico adaptador;
    private ArrayList<Persona> personas;
    private VolleySingleton volleySingleton;
    private Switch aSwitch;
    private Map map;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tecnicosViewModel =
                ViewModelProviders.of(this).get(TecnicosViewModel.class);
        root = inflater.inflate(R.layout.fragment_tecnicos, container, false);

        recyclerViewTecnicos=(RecyclerView)root.findViewById(R.id.recyclerListaTecnicos);
        aSwitch=(Switch)root.findViewById(R.id.switchMejorTecnico);
        //Codigo generado
        tecnicosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        aSwitch.setChecked(false);
        map=new HashMap();
        map.put("funcion", "tecnicosAlfabeto");
        llamarWebService(map,0);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    recyclerViewTecnicos.invalidate();
                    map.put("funcion", "tecnicosCalificacion");
                    llamarWebService(map,1);
                    //Toast.makeText(getContext(), "Activo", Toast.LENGTH_SHORT).show();
                }else{
                    recyclerViewTecnicos.invalidate();
                    map.put("funcion", "tecnicosAlfabeto");
                    llamarWebService(map,0);
                }
            }
        });



        return root;
    }
   private void setListaRecycler(JSONObject jsonObject) {

       JSONArray jsonArray;
       Persona p;
       String cal;
       personas = new ArrayList<Persona>();
       try {
           jsonArray = jsonObject.getJSONArray("lista");
           //Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
           for (int i = 0; i < jsonArray.length(); i++) {
               if(jsonArray.getJSONObject(i).getString("Calificacion").equals("null"))
                   cal="No aplica";
               else {
                   DecimalFormat format = new DecimalFormat();
                   format.setMaximumFractionDigits(2); //Define 2 decimales.
                   Double d=Double.parseDouble(jsonArray.getJSONObject(i).getString("Calificacion"));
                   format.format(d);
                   cal =Double.toString(d) ;
               }


               //Toast.makeText(getContext(), Double.toString( cal), Toast.LENGTH_SHORT).show();
               p = new Persona(
                       Integer.parseInt(jsonArray.getJSONObject(i).getString("id")),
                       jsonArray.getJSONObject(i).getString("nombre"),
                       jsonArray.getJSONObject(i).getString("apellidos"),
                       jsonArray.getJSONObject(i).getString("telefono"),
                       cal);

               personas.add(p);
           }
           recyclerViewTecnicos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
           recyclerViewTecnicos.addItemDecoration(new DividerItemDecoration(
                   getContext(),
                   DividerItemDecoration.VERTICAL));
           adaptador = new AdaptadorItemTecnico(personas);
           adaptador.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Bundle bundle = new Bundle();
                   bundle.putInt("id", personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getId());
                   NavHostFragment.findNavController(TecnicosFragment.this).navigate(R.id.detalleTecnico,bundle);
               }
           });
           adaptador.setLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   crearAlertDialog(personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getId(),
                           personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getNombre().concat(" ").concat(
                                   personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getApellido()
                           ));
                   //Toast.makeText(getContext(), personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_SHORT).show();
                   return true;
               }
           });

           recyclerViewTecnicos.setAdapter(adaptador);
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }
    public void crearAlertDialog(final int id, String nombre){

        // Add the buttons
        //builder.setMessage("¿Que desea hacer con "+personas.get(id).getNombre()+" "+personas.get(id).getApellido()+"?");

// Set other dialog properties

// Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¿Qué desea hacer con "+nombre.concat("?"))
                .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(), "Eliminar", Toast.LENGTH_SHORT).show();
                        map.clear();
                        map.put("funcion", "eliminarTecnico");
                        map.put("id", Integer.parseInt(String.valueOf(id)));
                        llamarWebService(map,2);
                        map.put("funcion", "tecnicosAlfabeto");
                        llamarWebService(map,0);
                    }
                })
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        NavHostFragment.findNavController(TecnicosFragment.this).navigate(R.id.agregarTecnico,bundle);
                    }
                });

        builder.create().show();
    }
       public void llamarWebService(Map<String, String> map, final int t){
        String url="https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        JSONObject jo=new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    switch (t){
                                        case 0: if(jsonObject.getBoolean("respuesta"))
                                                    setListaRecycler(jsonObject); break;
                                        case 1: if(jsonObject.getBoolean("respuesta"))
                                            setListaRecycler(jsonObject); break;
                                        case 2: if(jsonObject.getBoolean("respuesta"))
                                            Toast.makeText(getContext(), "Técnico eliminado", Toast.LENGTH_SHORT).show();break;
                                            case 3: if(jsonObject.getBoolean("respuesta")){

                                            }break;
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
}
