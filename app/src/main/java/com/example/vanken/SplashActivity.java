package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = null;
                //Se define el acceso al archivo donde se guardan las preferencias
                SharedPreferences sharedPreferences = getSharedPreferences("user.dat",MODE_PRIVATE);
                //Retorna el estatis de registrado, si existe, sino regresa falso
                if (sharedPreferences.getBoolean("registrado",false)){
                    switch (sharedPreferences.getString("tipo","Cliente")){
                        case "Admin":
                            intent = new Intent(SplashActivity.this, MainAdmin.class);
                        break;
                        case "Tecnico":
                            intent =  new Intent(SplashActivity.this, MainTecnicoActivity.class);
                        break;
                        case "Cliente":
                        break;
                        default:
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                        break;
                    }
                }else{
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }

                //se ejecuta la Activity dlmlds
                startActivity(intent);
                //Se elimina la Activity
                finish();
            }
        },SPLASH);
    }
}