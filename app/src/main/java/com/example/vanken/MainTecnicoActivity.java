package com.example.vanken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vanken.Modelos.VolleySingleton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainTecnicoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tecnico);

        toolbar = findViewById(R.id.toolbarTecnicoMain);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_tecnico);
        navigationView = findViewById(R.id.nav_tecnico_Menu);
        navigationView.setNavigationItemSelectedListener(MainTecnicoActivity.this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainTecnicoActivity.this, drawerLayout, toolbar, R.string.nav_open_tec, R.string.nav_close_tec);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_Main_Tecnico,new ListaPeticionesTecnicoFragment());
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //para cerrar automaticamente el menu
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.itemServicios){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_Main_Tecnico,new ListaPeticionesTecnicoFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.itemHistorico){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_Main_Tecnico,new HistoricoTecnicoFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.itemPerfil){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_Main_Tecnico,new InfoPersonalTecnicoFragment());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.itemSalir){
            salir();
        }
        return false;
    }

    private void salir(){
        SharedPreferences sharedPreferences = getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", "");
        editor.putString("tipo", "");
        editor.putString("ssid", "");
        editor.putBoolean("registrado", false);
        startActivity(new Intent(this,LoginActivity.class));
    }
}