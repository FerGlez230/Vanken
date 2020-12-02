package com.example.vanken;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoPersonalTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoPersonalTecnicoFragment extends Fragment {

    EditText edtPassworA, edtPasswordN, edtPasswordC;
    TextView txtUsuario, txtNombre, txtTelefono, txtRango;
    Button btnModificar;
    ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoPersonalTecnicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoPersonalTecnicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoPersonalTecnicoFragment newInstance(String param1, String param2) {
        InfoPersonalTecnicoFragment fragment = new InfoPersonalTecnicoFragment();
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
        final View vista = inflater.inflate(R.layout.fragment_info_personal_tecnico, container, false);

        edtPassworA = vista.findViewById(R.id.edtPasswordAnteriorTecnico);
        edtPasswordN = vista.findViewById(R.id.edtPasswordNueva);
        edtPasswordC = vista.findViewById(R.id.edtPasswordConfirmTecnico);
        txtUsuario = vista.findViewById(R.id.txtUsusarioInfoTecnico);
        txtNombre = vista.findViewById(R.id.txtNombreTecnicoTec);
        txtTelefono = vista.findViewById(R.id.txtTelefonoTecnicoTec);
        txtRango = vista.findViewById(R.id.txtRangoTecnicoTec);
        imageView = vista.findViewById(R.id.imgVPersonalTecnico);
        initComponents();

        btnModificar = vista.findViewById(R.id.btnModificarPasswordTecnico);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    Map map = new HashMap();
                    map.put("funcion", "updatePassword");
                    map.put("username", txtUsuario.getText().toString());
                    map.put("passwordNueva", edtPasswordN.getText().toString());
                    map.put("passwordVieja", edtPassworA.getText().toString());

                    JSONObject params = new JSONObject(map);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php",
                            params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("respuesta")) {
                                    Toast.makeText(vista.getContext(),"Operacion Exitosa",Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(vista.getContext(),"Error no hay respuesta",Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){};
                    VolleySingleton volley = new VolleySingleton(vista.getContext());
                    volley.addToRequestQueue(jsonObjectRequest);
                }else{
                    Toast.makeText(vista.getContext(),"Ingresa correctamente el password", Toast.LENGTH_LONG).show();
                }
            }
        });

        return vista;
    }
    String url = "https://www.solfeggio528.com/vanken/webservice.php";
    private void initComponents(){

        Map map = new HashMap();
        map.put("funcion", "perfilTecnico");
        map.put("ssid", ssid());

        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("respuesta")){
                        JSONObject object = response.getJSONObject("datos");
                        txtNombre.setText(object.getString("nombre"));
                        txtTelefono.setText(object.getString("telefono"));
                        txtRango.setText(object.getString("rango") + "km");
                        txtUsuario.setText(object.getString("username"));
                        String base64String=object.getString("imagen");
                        String base64Image = base64String;

                        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        imageView.setImageBitmap(decodedByte);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    txtNombre.setText("Nombre");
                    txtTelefono.setText("Telefono");
                    txtRango.setText("Rango");
                    txtUsuario.setText("Username");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){};
        VolleySingleton volley = new VolleySingleton(getContext());
        volley.addToRequestQueue(jsonObjectRequest);
    }

    private String ssid(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user.dat",MODE_PRIVATE);
        return sharedPreferences.getString("ssid"," ");
    }

    private boolean validar(){
        String nueva = edtPasswordN.getText().toString();
        String confirm = edtPasswordC.getText().toString();
        String anterior = edtPassworA.getText().toString();

        if(nueva.isEmpty() || confirm.isEmpty() || anterior.isEmpty())
            return false;
        else if(nueva.equals(confirm) && !nueva.equals(anterior))
            return true;

        return false;
    }
}