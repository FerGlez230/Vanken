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

public class EvaluarServicioTecnicoActivity extends AppCompatActivity {

    private RatingBar calif;
    private CheckBox internet, pc, programas, celular, impresora, ofimatica;
    private EditText observaciones, comision;
    private Switch swExtras;
    private Button btnFinalizar, btnQueja;
    private Intent inicialIntent;
    private String ID, tiempo, ssid;

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

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    guardarDatos();
                    Intent i = new Intent(EvaluarServicioTecnicoActivity.this, MainTecnicoActivity.class);
                    startActivity(i);
                }
            }
        });

        btnQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){

                    guardarDatos();
                    Intent i = new Intent(EvaluarServicioTecnicoActivity.this, QuejaDeTecnicoActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private String valuesCheckBox(){
        String hobbies = "";

        if(internet.isChecked())
            hobbies += "Internet,";
        if (pc.isChecked())
            hobbies += "Computadora,";
        if(programas.isChecked())
            hobbies += "Programas,";
        if(celular.isChecked())
            hobbies += "Celular,";
        if(impresora.isChecked())
            hobbies += "musica,";
        if(ofimatica.isChecked())
            hobbies += "Ofimatica,";

        return hobbies;
    }

    private boolean validar(){
        if(valuesCheckBox().equals("") || observaciones.getText().equals("") || comision.getText().equals("")){
            Toast.makeText(this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void guardarDatos(){
       /* ID = inicialIntent.getStringExtra("ID");
        tiempo = inicialIntent.getStringExtra("Tiempo");*/
    }
}