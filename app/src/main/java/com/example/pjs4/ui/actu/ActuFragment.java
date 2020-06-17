package com.example.pjs4.ui.actu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.pjs4.R;

import com.example.pjs4.R;
import com.example.pjs4.Test;

public class ActuFragment extends Fragment {

    private ActuViewModel actuViewModel;
    private Button btn_test;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actuViewModel =
                ViewModelProviders.of(this).get(ActuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actu, container, false);

        btn_test = root.findViewById(R.id.btn_test);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Test.class));
            }
        });

        return root;
    }






}
