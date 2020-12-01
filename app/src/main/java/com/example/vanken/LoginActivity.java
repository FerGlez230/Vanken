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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText email, password;
    private TextView registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=(Button)findViewById(R.id.btnIngresarInicioSesion);
        email=(EditText)findViewById(R.id.editTextEmailIniciarSesion);
        password=(EditText)findViewById(R.id.editTextPasswordIniciarSesion);
        registro = findViewById(R.id.linkRegistro);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()){
                    //Llamar a webService
                    Map map = new HashMap();
                    map.put("funcion", "login");
                    map.put("user", email.getText().toString());
                    map.put("pass", password.getText().toString());
                    JSONObject params = new JSONObject(map);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php", params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                if (response.getBoolean("respuesta")) {
                                    String tipo = response.getString("tipo");
                                    String ssid =  response.getString("ssid");

                                    SharedPreferences sharedPreferences = getSharedPreferences("user.dat", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("usuario", email.getText().toString());
                                    editor.putString("tipo", tipo);
                                    editor.putString("ssid", ssid);
                                    editor.putBoolean("registrado", true);
                                    editor.apply();

                                    if (tipo.contentEquals("Tecnico")){
                                        Intent intent =  new Intent(v.getContext(), MainTecnicoActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(tipo.contentEquals("Admin")){
                                        startActivity(new Intent(v.getContext(), MainAdmin.class));
                                        finish();
                                    }
                                    else if(tipo.contentEquals("Cliente")){

                                    }
                                    email.setText(tipo);
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = getSharedPreferences("user.dat", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("usuario", "");
                                    editor.putString("tipo", "");
                                    editor.putString("ssid", "");
                                    editor.putBoolean("registrado", false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){};
                    VolleySingleton volley = new VolleySingleton(v.getContext());
                    volley.addToRequestQueue(jsonObjectRequest);
                }
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

}
