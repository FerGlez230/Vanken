package com.example.vanken.ui.tecnicos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleTecnicoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
private int id=0;
private ImageView imageView;
private TextView nombre, apellido, telefono, username, rango;
VolleySingleton volleySingleton;
    public DetalleTecnicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleTecnicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleTecnicoFragment newInstance(String param1, String param2) {
        DetalleTecnicoFragment fragment = new DetalleTecnicoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id=getArguments().getInt("id",0);
            //Toast.makeText(getContext(), Integer.toString(id),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detalle_tecnico, container, false);
        nombre=(TextView)view.findViewById(R.id.textViewNombreDetalleTecnico);
        apellido=(TextView)view.findViewById(R.id.textViewApellidoDetalleTecnico);
        telefono=(TextView)view.findViewById(R.id.textViewTelefonoDetalleTecnico);
        rango=(TextView)view.findViewById(R.id.textViewRangoDetalleTecnico);
        imageView=(ImageView)view.findViewById(R.id.imgVDetalleTecnico);
        username=(TextView)view.findViewById(R.id.textViewUserDetalleTecnico);
        if(id!=0){
            //Toast.makeText(getContext(), Integer.toString(id),Toast.LENGTH_SHORT).show();
            Map map=new HashMap<>();
            map.put("funcion", "detallesTecnico");
            map.put("id",Integer.toString( id));
            llamarWebService(map);
        }

        return view;
    }
    public void llamarWebService(Map<String, String> map) {
        String url = "https://www.solfeggio528.com/vanken/webservice.php";
        // Request a string response from the provided URL.
        JSONObject jo = new JSONObject(map);
        volleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest
                        (Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                try {
                                   //Toast.makeText(getContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
                                    if (jsonObject.getBoolean("respuesta")) {
                                        setDetalles(jsonObject);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String errorCause = error.toString();
                                Toast.makeText(getContext(), errorCause, Toast.LENGTH_LONG).show();
                            }
                        })
        );
    }
    public void setDetalles(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("lista");

            nombre.setText(jsonArray.getJSONObject(0).getString("nombre"));
            apellido.setText(jsonArray.getJSONObject(0).getString("apellidos"));
            telefono.setText(jsonArray.getJSONObject(0).getString("telefono"));
            username.setText(jsonArray.getJSONObject(0).getString("user"));
            rango.setText(jsonArray.getJSONObject(0).getString("rango"));
            String base64String=jsonArray.getJSONObject(0).getString("imagen");
            String base64Image = base64String;

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            imageView.setImageBitmap(decodedByte);
            //Picasso.with(getContext()).load(cadena+ jsonObject.getString("imagen")).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
