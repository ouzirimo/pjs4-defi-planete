package views;

import androidx.annotation.NonNull;
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
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pjs4.Decouvrir;
import com.example.pjs4.FilActualite;
import com.example.pjs4.MainActivity;
import com.example.pjs4.R;
import com.example.pjs4.SignUpActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import model.DataBase;
import model.User;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private User user;

    private ViewPager2 viewPager2;
    private Handler sliderHandle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        txt_name = (TextView) findViewById(R.id.session_name);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        //User Information
        String userName = sharedpreferences.getString("nameKey", null);
        String userPwd = sharedpreferences.getString("pwdKey", null);
        DataBase database = MainActivity.getDataBase();
        User user = database.getUser(userName, userPwd);
        txt_name.append(userName);

        //Carousel
       /* viewPager2 = findViewById(R.id.carouselView);
        final List<SliderItem> sliderItem = new ArrayList<>();
        sliderItem.add(new SliderItem(R.drawable.img1));
        sliderItem.add(new SliderItem(R.drawable.img2));
        sliderItem.add(new SliderItem(R.drawable.test));

        viewPager2.setAdapter(new SliderAdapter(sliderItem, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r + 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandle.removeCallbacks(sliderRunnable);
                sliderHandle.postDelayed(sliderRunnable, 3000);
            }
        });
    }

    //Effet supplémentaires carousel
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };*/


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
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        this.finish();
    }
}
