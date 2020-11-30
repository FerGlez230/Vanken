package com.example.vanken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainTecnicoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayoutTecnico;
    NavigationView navigationViewTecnico;
    Toolbar toolbarTecnico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tecnico);

        drawerLayoutTecnico = findViewById(R.id.drawer_layout_tecnico);
        navigationViewTecnico = findViewById(R.id.nav_tecnico_Menu);
        toolbarTecnico = findViewById(R.id.toolbarTecnico);
        setSupportActionBar(toolbarTecnico);


        navigationViewTecnico.bringToFront();
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(MainTecnicoActivity.this, drawerLayoutTecnico, R.string.nav_open_tec,R.string.nav_close_tec);
        drawerLayoutTecnico.addDrawerListener(toogle);
        toogle.syncState();
        navigationViewTecnico.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }
}