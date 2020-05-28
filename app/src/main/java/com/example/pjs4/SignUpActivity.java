package com.example.pjs4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import model.FireBase;
import views.Accueil;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etLogin,etConPass;
    Button btnRegister;
    String email,login,pass,conPass;

    private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
    private FireBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new FireBase();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        etEmail = findViewById(R.id.input_mail);
        etPassword = findViewById(R.id.input_mdp);
        etConPass = findViewById(R.id.input_mdp2);
        etLogin = findViewById(R.id.input_login);

        btnRegister = findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            register();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

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

        public void onSignUpSuccess(){
            Intent in = new Intent(SignUpActivity.this, Accueil.class);
            startActivity(in);
        }

        public void register (){
            initialize();
            if(!validate()){
                Toast.makeText(this,"Inscription incorrecte", Toast.LENGTH_SHORT).show();
            }else{

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //Firebase Sign in success

                                            db.addNewUser(login, email);

                                            SharedPreferences.Editor editor = sharedpreferences.edit();
                                            editor.putString("nameKey", login);
                                            editor.putString("pwdKey", pass);
                                            editor.commit();


                                            onSignUpSuccess();

                                        } else {
                                            Toast.makeText(SignUpActivity.this,"L'adresse mail que vous tentez d'utiliser est déjà attribuée à un compte", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


            }

        }

}
