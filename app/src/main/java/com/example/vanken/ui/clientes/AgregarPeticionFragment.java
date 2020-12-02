package com.example.vanken.ui.clientes;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.AgregarTecnicoFragment;
import com.example.vanken.Modelos.VolleySingleton;
import com.example.vanken.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarPeticionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarPeticionFragment extends Fragment implements OnMapReadyCallback {

    private EditText direccion, problema;
    private CheckBox intenet, compu, programas, impresora, ofimatica, celular;
    private Button agregar;
    double costo, latitud, longitud;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private FusedLocationProviderClient mFused;
    private GoogleMap map;
    private Location lastKnownLocation;
    private Marker mUbicacion;
    private boolean permisoLocalizacion;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgregarPeticionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarPeticionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarPeticionFragment newInstance(String param1, String param2) {
        AgregarPeticionFragment fragment = new AgregarPeticionFragment();
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
        View view = inflater.inflate(R.layout.fragment_agregar_peticion, container, false);
        direccion = (EditText) view.findViewById(R.id.edDireccionAgregarPeticion);
        problema = (EditText) view.findViewById(R.id.edProblemaAgregarPeticion);
        agregar = (Button) view.findViewById(R.id.btnAceptarAgregarPeticion);
        intenet = (CheckBox) view.findViewById(R.id.cbCatInternetP);
        compu = (CheckBox) view.findViewById(R.id.cbCatCompuP);
        programas = (CheckBox) view.findViewById(R.id.cbCatProgramasP);
        impresora = (CheckBox) view.findViewById(R.id.cbCatImpresoraP);
        ofimatica = (CheckBox) view.findViewById(R.id.cbCatOfimaticaP);
        celular = (CheckBox) view.findViewById(R.id.cbCatCelularP);
        mapView = (MapView) view.findViewById(R.id.mapViewAgregarPeticion);
        Bundle mapViewBundle = null;
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisar();
            }
        });
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mFused = LocationServices.getFusedLocationProviderClient(getContext());
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        return view;
    }

    void revisar() {
        costo = 0;
        boolean check = false;
        latitud = mUbicacion.getPosition().latitude;
        longitud = mUbicacion.getPosition().longitude;
        if (latitud != 0 && !problema.getText().toString().isEmpty() && !direccion.getText().toString().isEmpty()) {
            if (intenet.isChecked()){
                costo += 50;
                check = true;
            }
            if (compu.isChecked()){
                costo += 200;
            }
            if (programas.isChecked()){
                costo += 120;
                check = true;
            }
            if (impresora.isChecked()){
                costo += 100;
                check = true;
            }
            if (ofimatica.isChecked()){
                costo += 400;
                check = true;
            }
            if (celular.isChecked()){
                costo += 100;
                check = true;
            }
            if(check){
                costo = (int)(costo*.70*30);
                if(costo < 75) costo = 75;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("El precio estimado de consulta es: "+ costo );
                builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviar();
                    }
                });
            }
            else Toast.makeText(getContext(), "Selecciona una categoria", Toast.LENGTH_LONG).show();
        }
    }
    public void enviar(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user.dat", Context.MODE_PRIVATE);
        VolleySingleton volleySingleton = new VolleySingleton(getContext());
        int id = sharedPreferences.getInt("idUsuario",-1);
        Map map = new HashMap<>();
        map.put("function", "crearPeticion");
        map.put("id", id);
        map.put("direccion", direccion.getText().toString());
        map.put("problema", direccion.getText().toString());
        map.put("latitud", latitud);
        map.put("longitud", longitud);
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("respuesta")) {
                        Toast.makeText(getActivity(), "Solicitud exitosa", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(AgregarPeticionFragment.this).navigate(R.id.nav_peticiones_cliente);
                    }
                    else{
                        Toast.makeText(getActivity(), "Operacion fallida", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){};
        volleySingleton.addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.map = googleMap;
        googleMap.setMyLocationEnabled(true);
        getDeviceLocation();
    }

    void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            Task<Location> locationResult = mFused.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    if(task.isSuccessful()) {
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            LatLng pos = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
                            mUbicacion = map.addMarker(new MarkerOptions().position(pos).draggable(true));
                        }
                    }
                }
            });
        }
        catch (SecurityException e){
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
        }
        mapView.onSaveInstanceState(mapViewBundle);
        //mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}