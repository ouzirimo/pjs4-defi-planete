package com.example.pjs4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


import model.FireBase;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etLogin,etConPass;
    Button btnRegister;
    String email,login,pass,conPass;

    private FirebaseAuth mAuth;
    private FireBase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //BDD
        fb = new FireBase();
        mAuth = FirebaseAuth.getInstance();

        //inputs
        etEmail = findViewById(R.id.input_mail);
        etPassword = findViewById(R.id.input_mdp);
        etConPass = findViewById(R.id.input_mdp2);
        etLogin = findViewById(R.id.input_login);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> register());
    }

    private void updateUI(FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(login)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("Update user profile :", "User profile updated.");
                    }
                });
    }


    public void initialize(){
        email= etEmail.getText().toString();
        login = etLogin.getText().toString();
        pass = etPassword.getText().toString();
        conPass=etConPass.getText().toString();
    }

    public boolean validate(){
        boolean valid = true;
        if(login.isEmpty()){
            etLogin.setError("Login manquant");
            etLogin.requestFocus();
            valid =false;
        }

        else if(pass.length()<6){
            etPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
            etPassword.requestFocus();
            valid =false;
        }
        else if(conPass.isEmpty()) {
            etConPass.setError("Confirmez votre mot de passe");
            etConPass.requestFocus();
            valid =false;
        }
        else if (!pass.equals(conPass)){
            etConPass.setError("Les mots de passe ne correspondent pas ");
            etConPass.requestFocus();
            valid =false;
        }
        else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Email manquant/incorrect");
            etEmail.requestFocus();
            valid =false;
        }

        return valid;
    }

    public void openAccueil(){
        Intent in = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(in);
    }

    public void register (){
        initialize();
        if(validate()){
            onSignUpSuccess();
        }
    }


    private void onSignUpSuccess() {
        Log.d("TEST SIGNUP","signOut");
        FirebaseAuth.getInstance().signOut();
        Log.d("TEST SIGNUP","Create User");
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("SignUp", "Success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user!= null) {
                            updateUI(user);
                            fb.addNewUser(login, email);
                            FirebaseAuth.getInstance().signOut();
                            authentication();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this,"L'adresse mail que vous tentez d'utiliser est déjà attribuée à un compte", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void authentication() {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(SignUpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d("Authentication", "Success");
                        openAccueil();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Authentication", "Failed");
                        Toast.makeText(SignUpActivity.this, "Authentification échouée",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
