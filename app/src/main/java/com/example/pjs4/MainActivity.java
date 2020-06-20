package com.example.pjs4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin, editTextPassword;

    private String login;
    private String pwd;

    private FirebaseAuth mAuth;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btn_go, btn_register;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btn_go = findViewById(R.id.button_goAccueil);
        btn_register = findViewById(R.id.button_register);

        editTextLogin = findViewById(R.id.input_login);
        editTextPassword = findViewById(R.id.input_pwd);

        btn_go.setOnClickListener(v -> {
            intialize();
            authentication();
        });


        btn_register.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SignUpActivity.class)));

    }

    private void intialize() {
        login = editTextLogin.getText().toString();
        pwd = editTextPassword.getText().toString();
    }

    private void authentication() {

        mAuth.signInWithEmailAndPassword(login, pwd)
                .addOnCompleteListener(MainActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d("Authentication", "Success");
                        openAccueil();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Authentication", "Failed");
                        Toast.makeText(MainActivity.this, "Authentification échouée",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void openAccueil() {
        Intent in = new Intent(MainActivity.this, Root.class);
        startActivity(in);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            editTextLogin.setText("");
            editTextPassword.setText("");
            openAccueil();
        }
    }
}
