package com.example.vanken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainCliente extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);
        Toolbar toolbar = findViewById(R.id.toolbarCliente);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout_cliente);
        final NavigationView navigationView = findViewById(R.id.nav_view_cliente);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabAgregarCliente);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_peticiones_cliente, R.id.nav_reportes_cliente, R.id.nav_acercade,
                R.id.nav_config_cliente)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cliente);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment = (NavHostFragment) MainCliente.this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_cliente);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.agregarPeticion);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_cliente, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cliente);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}