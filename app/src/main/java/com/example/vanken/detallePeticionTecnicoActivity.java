package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        new Thread(cronometro).start();
        cronometro.reiniciar();

        btnFinalizar = findViewById(R.id.btnFinalizarTiempo);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                SimpleDateFormat spF = new SimpleDateFormat("HH:mm:ss");
                String currentDateTimeString = spF.format(date);
                Intent intent = new Intent(detallePeticionTecnicoActivity.this, EvaluarServicioTecnicoActivity.class);
                intent.putExtra("Tiempo",txtcronometro.getText());
                intent.putExtra("ID",i.getStringExtra("ID"));
                intent.putExtra("Hora",currentDateTimeString);
                startActivity(intent);
                finish();
            }
        });
    }
}