package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Button button;
    private EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=(Button)findViewById(R.id.btnIngresarInicioSesion);
        email=(EditText)findViewById(R.id.editTextEmailIniciarSesion);
        password=(EditText)findViewById(R.id.editTextPasswordIniciarSesion);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()){
                    //Llamar a webService
                    
                }
            }
        });
    }

}
