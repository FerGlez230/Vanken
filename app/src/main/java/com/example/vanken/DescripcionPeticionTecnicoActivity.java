package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DescripcionPeticionTecnicoActivity extends AppCompatActivity {

    TextView ID, fecha, cliente, domicilio, categoria, comentario;
    String longitud, latitud;
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
                createNotificationChannel();
                createNotification();
                setPendingIntent();
            }
        });

        longitud = i.getStringExtra("Longitud");
        latitud = i.getStringExtra("Latitud");
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
        notification.setContentTitle("Titulo");
        notification.setContentText("Contenido");
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setDefaults(Notification.DEFAULT_VIBRATE);
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setContentIntent(pendingIntent);
        notification.addAction(R.drawable.ic_ok,"INICIAR SERVICIO",pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0,notification.build());
    }

    private void createNotificationChannel(){
        CharSequence nombre = "Notificacion";
        NotificationChannel notificationChannel = new NotificationChannel("NOTIFICACION",nombre,NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
    }

    private void setPendingIntent() {
         Intent intent = new Intent(DescripcionPeticionTecnicoActivity.this,detallePeticionTecnicoActivity.class);
         String cliente = i.getStringExtra("Cliente");
         String categoria = i.getStringExtra("Categoria");
         String num = i.getStringExtra("ID");
         intent.putExtra("Cliente",cliente);
         intent.putExtra("Categoria", categoria);
        intent.putExtra("ID", num);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DescripcionPeticionTecnicoActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT);

    }
}