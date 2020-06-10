package views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pjs4.R;
import com.example.pjs4.SignUpActivity;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import model.DataBase;
import model.User;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private String uName;
    private String uPass;
    private User user;

    private ViewPager2 viewPager2;
    private Handler sliderHandle = new Handler();

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
    }

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

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }
}
