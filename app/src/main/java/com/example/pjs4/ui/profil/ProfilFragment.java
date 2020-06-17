package com.example.pjs4.ui.profil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pjs4.MainActivity;
import com.example.pjs4.R;
import com.example.pjs4.Root;
import com.example.pjs4.ShowChallenge;
import com.example.pjs4.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.pjs4.R;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import model.Challenge;
import model.DataBase;
import model.FireBase;
import model.User;

public class ProfilFragment extends Fragment {

    private ProfilViewModel profilViewModel;

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private String uName;
    private String uPass;
    private User user;

    private TextView testChallenge;
    private TextView tv_chal1; //pour tester
    private ViewPager2 viewPager2;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Challenge> l = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        /*final TextView textView = root.findViewById(R.id.text_home);
        profilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        btn_logout = root.findViewById(R.id.btn_logout);
        txt_name = root.findViewById(R.id.session_name);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            uName = fbUser.getDisplayName();
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //User Information
        txt_name.append(uName);

        //showAllChallenge(root);
        /*Button btn_camera = getView().findViewById(R.id.btn_camera);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(this, PictureActivity.class));
            }
        });*/
        try {
            showChallengeDone(root);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showChallengeDone(root);

        return root;
    }

    //voir comment utilisef cette méthode ici
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        this.getActivity().finish();
    }

    /*
    public String getInfoUser() {
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
*/

    public void initTestChallenge(){
        Challenge c = new Challenge(1, "Ramasse des bouchons", "La collecte de bouchons consiste à ramsser 100 bouchons!", "geste ecolo", "moyen", 30);
        Challenge c2 = new Challenge(2, "jester poubelles", "jette bien tes poubelles!", "geste ecolo", "facile", 10);
        Challenge c3 = new Challenge(3, "eau froide", "lave toi à l'eau froide", "geste ecolo", "difficile", 50);

        l.add(c);
        l.add(c2);
        l.add(c3);
    }


    /**
     * Show all challenges of the data base from fire base
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showAllChallenge(View root){

        //on suppose que c la liste récupéré grâce à la fonction de Gaelle des 4 challenges en cours



        initTestChallenge();
        /**
         * Test avec une liste de Challenge
         */


        testChallenge = root.findViewById(R.id.tv_challengeTitleInProgress1);
        testChallenge.append(l.get(0).getDifficulty_challenge());




        /**
         * Ouverture du détail d'un challenge --> start activity ShowChallenge
         */
        tv_chal1 = root.findViewById(R.id.tv_chal1);


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

    private void setOnClickTest(final TextView btn, final Challenge c){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Do whatever you want(str can be used here)
                SendChallenge(c);
            }
        });
    }

    public void SendChallenge(Challenge c){

        Intent i = new Intent(getActivity(), ShowChallenge.class);
        Bundle bundle = new Bundle();
        bundle.putString("tv_challengeName", c.getName_challenge());
        bundle.putString("tv_challengeDesc", c.getDescription_challenge());
        i.putExtras(bundle);
        startActivity(i);

    }



    public void showChallengeDone(View v){
        initTestChallenge();

        LinearLayout lay_parent = v.findViewById(R.id.lay_parent) ;

        for (Challenge c : l ){
            LinearLayout lay_child = new LinearLayout(v.getContext());
            lay_child.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    500, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(100,0,0,0);
            lay_child.setBackgroundColor(Color.BLUE);
            lay_child.setLayoutParams(layoutParams);
            TextView tv_title = new TextView(v.getContext());
            tv_title.setTextSize(20);
            tv_title.setText(c.getName_challenge());

            TextView tv_show = new TextView(v.getContext());
            tv_show.setTextSize(15);
            tv_show.setText("Voir le défi");

            lay_child.addView(tv_title);
            lay_child.addView(tv_show);

            lay_parent.addView(lay_child);

            setOnClickTest(tv_show,c);



        }






    }


}
