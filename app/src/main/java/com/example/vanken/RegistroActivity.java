package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {
    Button button;
    EditText nombre, apellidos,email, password, passwordConf;
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty()&&!apellidos.getText().toString().isEmpty()
                &&!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()&&
                !passwordConf.getText().toString().isEmpty()){
                    if(password.getText().toString().equals(passwordConf.getText().toString())){

                    } else Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();

                }else Toast.makeText(getApplicationContext(),"Ingrese todos los datos",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
