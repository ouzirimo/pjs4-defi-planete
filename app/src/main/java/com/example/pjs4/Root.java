package com.example.pjs4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;
import java.util.Map;

import model.Challenge;
import model.ChallengeStatus;
import model.Done;
import model.FireBase;
import model.FirestoreCallback;
import model.InProgress;
import model.MapWrapper;
import model.UnDone;
import model.User;

public class Root extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        FireBase fb = new FireBase();
        fb.getAllChallenges();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_Profil, R.id.navigation_discover, R.id.navigation_Actu)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        fb.getUser(new FirestoreCallback<User>() {
            @Override
            public void onCallback(User user) {
                Log.d("GET USER : ", user.getLogin());
                fb.getUserChallenge(user.getLogin(), new FirestoreCallback<HashMap<String, String>>() {
                    @Override
                    public void onCallback(HashMap<String, String> map_challengePivot) {
                        for (Map.Entry mapentry : map_challengePivot.entrySet()) { // For each challenge pivot
                            String status = mapentry.getValue().toString();
                            ChallengeStatus challengeStatus;
                            if (status.equals("enCours")){
                                challengeStatus = new UnDone();
                            }
                            else if(status.equals("enAttente")){
                                challengeStatus = new InProgress();
                            }
                            else{
                                challengeStatus = new Done();
                            }
                            user.addChallenge(fb.getChallenges().get(mapentry.getKey()), challengeStatus);
                        }
                        Log.d("IN ROOT : ",user.getBackpack().toString());
                    }
                });
            }
        });
    }

}
