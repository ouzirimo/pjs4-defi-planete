package com.example.pjs4;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import model.FireBase;


public class Test extends AppCompatActivity {

    private ImageView img_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        this.img_view = findViewById(R.id.img_view);

        FireBase fb = new FireBase();

        try {
            img_view.setImageBitmap(fb.getImage("apples-1841132_1920.jpg"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
