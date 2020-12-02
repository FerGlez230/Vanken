package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
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

public class EvaluarServicioTecnicoActivity extends AppCompatActivity {

    private RatingBar calif;
    private CheckBox internet, pc, programas, celular, impresora, ofimatica;
    private EditText observaciones, comision;
    private Switch swExtras;
    private Button btnFinalizar, btnQueja;
    private Intent inicialIntent;
    private String ID, tiempo, ssid, Hora;
    private int Precio = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar_servicio_tecnico);

        calif = findViewById(R.id.ratingEvaluacionServicioTecnico);
        calif.setNumStars(5);
        calif.setStepSize((float) 1.0);

        btnFinalizar = findViewById(R.id.btnFinalizarEvaluacionTecnico);
        btnQueja = findViewById(R.id.btnGenerarQuejaEvaluacionTecnico);
        internet = findViewById(R.id.checkInternetEvaluacionTecnica);
        pc = findViewById(R.id.checkComputadoraEvaluacionTecnica);
        programas = findViewById(R.id.checkProgramasEvaluacionTecnica);
        celular = findViewById(R.id.checkCelularEvaluacionTecnica);
        impresora = findViewById(R.id.checkImpresoraEvaluacionTecnica);
        ofimatica = findViewById(R.id.checkOfimatiaEvaluacionTecnica);
        observaciones = findViewById(R.id.edtComentarioEvalucaionTecnico);
        comision = findViewById(R.id.edtComisionEvaluacionTecnico);
        btnFinalizar = findViewById(R.id.btnFinalizarEvaluacionTecnico);
        btnQueja = findViewById(R.id.btnGenerarQuejaEvaluacionTecnico);
        inicialIntent = getIntent();
        ID = inicialIntent.getStringExtra("ID");
        Hora = inicialIntent.getStringExtra("Hora");
        tiempo = inicialIntent.getStringExtra("Tiempo");

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                   if(guardarDatos()) {
                       Intent i = new Intent(EvaluarServicioTecnicoActivity.this, MainTecnicoActivity.class);
                       startActivity(i);
                   }
                }
            }
        });

        btnQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    if(guardarDatos()){
                        Intent i = getIntent();
                        Intent ev = new Intent(EvaluarServicioTecnicoActivity.this, QuejaDeTecnicoActivity.class);
                        String cliente = i.getStringExtra("Cliente");
                        String categoria = i.getStringExtra("Categoria");
                        String num = i.getStringExtra("ID");
                        String Fecha = i.getStringExtra("Fecha");
                        String Domicilio = i.getStringExtra("Domicilio");
                        String comentario = i.getStringExtra("Comentario");
                        String tiempo = i.getStringExtra("Tiempo");
                        String hora = i.getStringExtra("Hora");

                        ev.putExtra("Tiempo",tiempo);
                        ev.putExtra("Hora",hora);
                        ev.putExtra("Cliente", cliente);
                        ev.putExtra("Categoria", categoria);
                        ev.putExtra("ID", num);
                        ev.putExtra("Fecha", Fecha);
                        ev.putExtra("Domicilio", Domicilio);
                        ev.putExtra("Comentario", comentario);

                        startActivity(ev);
                    }
                }
            }
        });
    }

    private String valuesCheckBox(){
        String hobbies = "";

        if(internet.isChecked()) {
            hobbies += "Internet,";
            Precio = Precio + 50;
        }if (pc.isChecked()) {
            hobbies += "Computadora,";
            Precio = Precio + 200;
        }if(programas.isChecked()) {
            hobbies += "Programas,";
            Precio = Precio + 120;
        }if(celular.isChecked()) {
            hobbies += "Celular,";
            Precio = Precio + 100;
        }if(impresora.isChecked()) {
            hobbies += "musica,";
            Precio = Precio + 100;
        }if(ofimatica.isChecked()) {
            hobbies += "Ofimatica,";
            Precio = Precio + 400;
        }

        return hobbies;
    }

    private boolean validar(){
        if(valuesCheckBox().equals("") || observaciones.getText().equals("") || comision.getText().equals("")){
            Toast.makeText(this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean guardarDatos() {

        float cal = calif.getRating();
        Precio = (int) (Precio +  Integer.parseInt(comision.getText().toString()));
        final boolean[] flag = {false};
        //Llamar a webService
        Map map = new HashMap();
        map.put("funcion", "finalizarServicio");
        map.put("idPeticion", ID);
        map.put("evaluacionT", cal);
        map.put("comentarioT", observaciones.getText().toString());
        map.put("precio", Precio);
        JSONObject params = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php",
                params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    if (response.getBoolean("respuesta"))
                        flag[0] = true;
                    else
                        flag[0] = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                    flag[0] = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EvaluarServicioTecnicoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                flag[0] = false;
            }
        }){};
        VolleySingleton volley = new VolleySingleton(EvaluarServicioTecnicoActivity.this);
        volley.addToRequestQueue(jsonObjectRequest);

        return flag[0];
    }
}