package com.example.pjs4.ui.actu;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.pjs4.R;

public class ActuFragment extends Fragment {

    private ActuViewModel actuViewModel;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actuViewModel =
                ViewModelProviders.of(this).get(ActuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actu, container, false);

        return root;
    }






}
