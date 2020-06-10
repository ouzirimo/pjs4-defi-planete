package com.example.pjs4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import views.Accueil;

public class FilActualite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fil_actualite);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_actu:
                            startActivity(new Intent(FilActualite.this,FilActualite.class));
                            break;
                        case R.id.nav_decouvrir:
                            startActivity(new Intent(FilActualite.this,Decouvrir.class));
                            break;
                        case R.id.nav_profil:
                            startActivity(new Intent(FilActualite.this, Accueil.class));
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

}
