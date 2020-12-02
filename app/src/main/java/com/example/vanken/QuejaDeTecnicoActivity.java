package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class QuejaDeTecnicoActivity extends AppCompatActivity {

    TextView txtID, txtFecha, txtCliente, txtDomicilio, txtCategoria, txtComentario;
    EditText edtReporte;
    Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queja_de_tecnico);

        Intent i = getIntent();
        String cliente = i.getStringExtra("Cliente");
        String categoria = i.getStringExtra("Categoria");
        final String num = i.getStringExtra("ID");
        String Fecha = i.getStringExtra("Fecha");
        String Domicilio = i.getStringExtra("Domicilio");
        String comentario = i.getStringExtra("Comentario");

        txtID = findViewById(R.id.txtIDQuejaTecnico);
        txtFecha = findViewById(R.id.txtFechaQuejaTecnico);
        txtCliente = findViewById(R.id.txtClienteQuejaTecnico);
        txtDomicilio = findViewById(R.id.txtDomicilioQueja);
        txtCategoria = findViewById(R.id.txtCategoriaQuejaTecnico);
        txtComentario = findViewById(R.id.txtComentarioQueja);
        edtReporte = findViewById(R.id.edtQuejaTecnico);

        txtID.setText("# de Sevicio:" + num);
        txtFecha.setText("Fecha: " + Fecha);
        txtCliente.setText("Cliente:  " + cliente);
        txtDomicilio.setText("Domicilio:  " + Domicilio);
        txtCategoria.setText("Categoria:  " + categoria);
        txtComentario.setText("Descripción:  " + comentario);

        btnAceptar = findViewById(R.id.btnAceptarQueja);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Llamar a webService
                    Map map = new HashMap();
                    map.put("funcion", "quejaTecnico");
                    map.put("usmotivoT", edtReporte.getText().toString());
                    map.put("idPeticion", num);
                    JSONObject params = new JSONObject(map);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php",
                            params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                if (response.getBoolean("respuesta")) {
                                    Toast.makeText(QuejaDeTecnicoActivity.this, "Reporte agregado exitosamente", Toast.LENGTH_SHORT);
                                    Intent i = new Intent(QuejaDeTecnicoActivity.this, MainTecnicoActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    Toast.makeText(QuejaDeTecnicoActivity.this, "Error", Toast.LENGTH_SHORT);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(QuejaDeTecnicoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){};
                    VolleySingleton volley = new VolleySingleton(v.getContext());
                    volley.addToRequestQueue(jsonObjectRequest);
                }
        });
    }
}