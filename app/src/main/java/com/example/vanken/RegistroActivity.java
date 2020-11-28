package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistroActivity extends AppCompatActivity {
    Button button;
    EditText nombre, apellidos,telefono, email, password, passwordConf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        button=(Button)findViewById(R.id.btnRegistrarme);
        nombre=(EditText)findViewById(R.id.editTextNombreRegistro);
        apellidos=(EditText)findViewById(R.id.editTextApellidosRegistro);
        email=(EditText)findViewById(R.id.editTextEmailRegistro);
        password=(EditText)findViewById(R.id.editTextPasswordRegistro);
        passwordConf=(EditText)findViewById(R.id.editTextConfirmarPasswordRegistro);
        telefono = (EditText) findViewById(R.id.editTextPhoneRegistro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty()&&!apellidos.getText().toString().isEmpty()
                &&!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()&&
                !passwordConf.getText().toString().isEmpty()){
                    if(password.getText().toString().equals(passwordConf.getText().toString())){
                        Map map = new HashMap<>();
                        map.put("funcion", "registro");
                        map.put("nombre", nombre.getText().toString());
                        map.put("apellidos", apellidos.getText().toString());
                        map.put("tipo", "Cliente");
                        map.put("telefono", telefono.getText().toString());
                        map.put("user", email.getText().toString());
                        map.put("pass", password.getText().toString());
                        JSONObject params = new JSONObject(map);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php", params, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getBoolean("respuesta") == true){
                                        Toast.makeText(RegistroActivity.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(RegistroActivity.this, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        VolleySingleton volley = new VolleySingleton(v.getContext());
                        volley.addToRequestQueue(jsonObjectRequest);
                    } else Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();

                }else Toast.makeText(getApplicationContext(),"Ingrese todos los datos",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
