package com.example.pjs4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.FireBase;
import model.User;
import views.Accueil;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etLogin,etConPass;
    Button btnRegister;
    String email,login,pass,conPass;

    SharedPreferences sharedpreferences;
    private FireBase db = new FireBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

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

            else if(pass.isEmpty()){
                etPassword.setError("mot de passe manquant");
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
            db.addNewUser(login, email);
            User u = new User(login, email, pass);

            //générer 4 challenges aléatoirement... avec la cathégorie en paramètre pour le moment rien et connâitre le nombre de challenge en général
            /*
                10 est prit au hasard comme s'il y avait max 10 challaenge dans la base pour tirer ensite un nombre entre 0 et 10
                dans la table et récup le challenge grâce à son id = nb tiré au hasard
             */
            int nbLignesDansLaTableChallenge = 10; //permet de générer le nombre random ici exemple

            u.generateRandomChallenge(nbLignesDansLaTableChallenge);
            u.generateRandomChallenge(nbLignesDansLaTableChallenge);
            u.generateRandomChallenge(nbLignesDansLaTableChallenge);
            u.generateRandomChallenge(nbLignesDansLaTableChallenge);

            db.getAllDocumentTest();

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("nameKey", login);
            editor.putString("pwdKey", pass);
            editor.commit();

            Intent in = new Intent(SignUpActivity.this, Accueil.class);
            startActivity(in);
        }

        public void register (){
        initialize();
        if(!validate()){
            Toast.makeText(this,"Inscription incorrecte", Toast.LENGTH_SHORT).show();
        }else{
            onSignUpSuccess();
        }

        }

}
