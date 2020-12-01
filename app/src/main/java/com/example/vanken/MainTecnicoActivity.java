package com.example.vanken;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class MainTecnicoActivity extends AppCompatActivity {

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

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainTecnicoActivity.this, drawerLayout, toolbar, R.string.nav_open_tec, R.string.nav_close_tec);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_Main_Tecnico,new ListaPeticionesTecnicoFragment());
        fragmentTransaction.commit();
    }
}