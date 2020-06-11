package views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pjs4.Decouvrir;
import com.example.pjs4.FilActualite;
import com.example.pjs4.MainActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pjs4.R;
import com.example.pjs4.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.DataBase;
import model.FireBase;
import model.User;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private String uName;
    private String uPass;
    private User user;

    private ViewPager2 viewPager2;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btn_logout = findViewById(R.id.btn_logout);
        txt_name = findViewById(R.id.session_name);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            uName = fbUser.getDisplayName();
        }

        //User Information
        txt_name.append(uName);


        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(navListener);
        showAllChallenge();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_actu:
                            startActivity(new Intent(Accueil.this,FilActualite.class));
                            break;
                        case R.id.nav_decouvrir:
                            startActivity(new Intent(Accueil.this,Decouvrir.class));
                            break;
                        case R.id.nav_profil:
                            startActivity(new Intent(Accueil.this,Accueil.class));
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }

    /**
     * Show all challenges of the data base from fire base
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showAllChallenge(){

        ArrayList<String> liste = new ArrayList<>();
        liste.add("Ramasse des bouchons");
        liste.add("utilise une serviette en tissu");
        liste.add("tri collectif");
        liste.add("Stop plastique");

        /*test = findViewById(R.id.tv_testChallenge);
        test.append(liste.get(0));*/

       /* LinearLayout l = findViewById(R.id.layout_challenge);

        //création d'un text view
        TextView t = new TextView(this);
        t.setText(liste.get(0));
        t.setTextSize(25);

        // Définition de la façon dont le composant va remplir le layout.
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // Ajout du composant au layout.
        l.addView(t, layoutParam);*/

    }
}
