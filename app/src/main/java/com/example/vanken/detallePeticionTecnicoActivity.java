package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class detallePeticionTecnicoActivity extends AppCompatActivity {

    TextView txtCliente;
    TextView txtServicio;
    TextView txtcronometro;
    Button btnFinalizar;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_peticion_tecnico);

        i = getIntent();

        txtCliente = findViewById(R.id.txtClienteDetalleTecnico);
        txtServicio = findViewById(R.id.txtServicioDetalleTecnico);

        txtCliente.setText("Cliente:    " + i.getStringExtra("Cliente"));
        txtServicio.setText("Servicio:    " + i.getStringExtra("Categoria"));
        txtcronometro = findViewById(R.id.txtCronometroTecnico);

        final Cronometro cronometro = new Cronometro("txtCronometroTecnico", txtcronometro);

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat spF = new SimpleDateFormat("HH:mm:ss");
        String currentDateTimeString = spF.format(date);

        //Llamar a webService
        Map map = new HashMap();
        map.put("funcion", "iniciarServicio");
        map.put("idPeticion", i.getStringExtra("ID"));
        map.put("horaInicioT", currentDateTimeString);
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php",
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    if (response.getBoolean("respuesta")){
                        new Thread(cronometro).start();
                        cronometro.reiniciar();
                    }
                    else{
                        Toast.makeText(detallePeticionTecnicoActivity.this, response.getString("mensaje"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(detallePeticionTecnicoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(detallePeticionTecnicoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){};
        VolleySingleton volley = new VolleySingleton(detallePeticionTecnicoActivity.this);
        volley.addToRequestQueue(jsonObjectRequest);


        btnFinalizar = findViewById(R.id.btnFinalizarTiempo);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cliente = i.getStringExtra("Cliente");
                String categoria = i.getStringExtra("Categoria");
                String num = i.getStringExtra("ID");
                String Fecha = i.getStringExtra("Fecha");
                String Domicilio = i.getStringExtra("Domicilio");
                String comentario = i.getStringExtra("Comentario");
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                SimpleDateFormat spF = new SimpleDateFormat("HH:mm:ss");
                String currentDateTimeString = spF.format(date);
                Intent intent = new Intent(detallePeticionTecnicoActivity.this, EvaluarServicioTecnicoActivity.class);
                intent.putExtra("Tiempo", txtcronometro.getText());
                intent.putExtra("Hora", currentDateTimeString);
                intent.putExtra("Cliente", cliente);
                intent.putExtra("Categoria", categoria);
                intent.putExtra("ID", num);
                intent.putExtra("Fecha", Fecha);
                intent.putExtra("Domicilio", Domicilio);
                intent.putExtra("Comentario", comentario);

                startActivity(intent);
                finish();
            }
        });
    }
}