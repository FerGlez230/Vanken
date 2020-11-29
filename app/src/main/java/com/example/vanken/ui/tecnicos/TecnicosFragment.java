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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tecnicosViewModel =
                ViewModelProviders.of(this).get(TecnicosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tecnicos, container, false);
        recyclerViewTecnicos=(RecyclerView)root.findViewById(R.id.recyclerListaTecnicos);
        aSwitch=(Switch)root.findViewById(R.id.switchMejorTecnico);
        //Codigo generado
        tecnicosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
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
            /*adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(),"Hola", Toast.LENGTH_SHORT).show();
                    //crearAlertDialog(personas.get(recyclerViewTecnicos.getChildAdapterPosition(v)).getId());
                    return true;
                }
            });*/
           recyclerViewTecnicos.setAdapter(adaptador);
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }
    public void crearAlertDialog(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setMessage("¿Que desea hacer con "+personas.get(id).getNombre()+" "+personas.get(id).getApellido()+"?");
        builder.setPositiveButton(R.string.editar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.eliminar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Set other dialog properties

// Create the AlertDialog
        AlertDialog dialog = builder.create();
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
