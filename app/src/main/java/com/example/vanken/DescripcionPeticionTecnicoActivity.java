package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.VolleySingleton;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DescripcionPeticionTecnicoActivity extends AppCompatActivity{

    TextView ID, fecha, cliente, domicilio, categoria, comentario;
    String s_longitud, s_latitud;
    Button acept;
    Intent i;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_peticion_tecnico);
        i = getIntent();
        ID = findViewById(R.id.txtIDDescripcionPeticion);
        fecha = findViewById(R.id.txtFechaDescripcionPeticion);
        cliente = findViewById(R.id.txtClientDescripcionPeticion);
        domicilio = findViewById(R.id.txtDomDescripcionPeticion);
        categoria = findViewById(R.id.txtCategoriaDescripcionPeticion);
        comentario = findViewById(R.id.txtComentDescripcionPeticion);
        acept = findViewById(R.id.btnAceptarPeticionTecnico);
        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DescripcionPeticionTecnicoActivity.this, "AYUDA", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("user.dat",MODE_PRIVATE);
                //Llamar a webService
                Map map = new HashMap();
                map.put("funcion", "aceptarPeticion");
                map.put("idPeticion", i.getStringExtra("ID"));
                map.put("ssid",sharedPreferences.getString("ssid"," "));
                Toast.makeText(DescripcionPeticionTecnicoActivity.this, sharedPreferences.getString("ssid"," "), Toast.LENGTH_SHORT).show();
                JSONObject params = new JSONObject(map);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.solfeggio528.com/vanken/webservice.php",
                        params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            if (response.getBoolean("respuesta")){
                                createNotificationChannel();
                                setPendingIntent();
                                createNotification();
                            }else{
                                Toast.makeText(DescripcionPeticionTecnicoActivity.this, response.getString("mensaje"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DescripcionPeticionTecnicoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DescripcionPeticionTecnicoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){};
                VolleySingleton volley = new VolleySingleton(DescripcionPeticionTecnicoActivity.this);
                volley.addToRequestQueue(jsonObjectRequest);
            }
        });

        s_longitud = i.getStringExtra("Longitud");
        s_latitud = i.getStringExtra("Latitud");
        ID.setText("# " + i.getStringExtra("ID"));
        fecha.setText(String.valueOf(i.getStringExtra("Fecha")));
        cliente.setText("Cliente: " + i.getStringExtra("Cliente"));
        domicilio.setText("Domicilio: " + i.getStringExtra("Domicilio"));
        categoria.setText("Categoria: " + i.getStringExtra("Categoria"));
        comentario.setText("Descripción:  " + i.getStringExtra("Comentario"));

    }

    private void createNotification(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(),"NOTIFICACION");
        notification.setSmallIcon(R.drawable.support);
        String cliente = i.getStringExtra("Cliente");
        String categoria = i.getStringExtra("Categoria");
        String num = i.getStringExtra("ID");
        notification.setContentTitle("# Servicio: " + num);
        notification.setContentText("Tipo: " + categoria + "  Cliente: " + cliente);
        notification.setPriority(NotificationCompat.PRIORITY_MAX);
        notification.setDefaults(Notification.DEFAULT_VIBRATE);
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setContentIntent(pendingIntent);
        notification.addAction(R.drawable.ic_ok,"INICIAR SERVICIO",pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0,notification.build());
    }

    private void createNotificationChannel(){
        CharSequence nombre = "Notificacion";
        NotificationChannel notificationChannel = new NotificationChannel("NOTIFICACION",nombre, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
    }

    private void setPendingIntent() {
        Intent intent = new Intent(DescripcionPeticionTecnicoActivity.this,detallePeticionTecnicoActivity.class);
        String cliente = i.getStringExtra("Cliente");
        String categoria = i.getStringExtra("Categoria");
        String num = i.getStringExtra("ID");
        String Fecha = i.getStringExtra("Fecha");
        String Domicilio = i.getStringExtra("Domicilio");
        String comentario = i.getStringExtra("Comentario");
        String Longitud = i.getStringExtra("Longitud");
        String Latitud = i.getStringExtra("Latitud");

        intent.putExtra("Cliente",cliente);
        intent.putExtra("Categoria", categoria);
        intent.putExtra("ID", num);
        intent.putExtra("Fecha",Fecha);
        intent.putExtra("Domicilio", Domicilio);
        intent.putExtra("Comentario",comentario);
        intent.putExtra("Longitud",Longitud);
        intent.putExtra("Latitud", Latitud);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DescripcionPeticionTecnicoActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);

    }
}