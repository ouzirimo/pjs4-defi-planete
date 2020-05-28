package com.example.pjs4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Challenge;
import model.DataBase;
import model.User;
import views.Accueil;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etLogin,etConPass;
    Button btnRegister;
    String email,login,pass,conPass;
    final int nbChallenge = 4;

    SharedPreferences sharedpreferences;

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

    public void register (){
        initialize();
        if(!validate()){
            Toast.makeText(this,"Inscription incorrecte", Toast.LENGTH_SHORT).show();
        }else{
            onSignUpSuccess();
        }

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
            User user = new User(login, email, pass);
            //variable Session
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("nameKey", login);
            editor.putString("pwdKey", pass);
            editor.commit();

            DataBase database = MainActivity.getDataBase();

            //set random challenge
            int cpt_challenge = nbChallenge;
            Challenge challenge = null;
            while(cpt_challenge>0){
                challenge = database.getRandomChallenge();
                if (!user.containsChallenge(challenge.getId_challenge())){
                    user.addChallenge(challenge);
                    cpt_challenge = cpt_challenge -1;
                }
            }

            //view changement
            Intent in = new Intent(SignUpActivity.this, Accueil.class);
            startActivity(in);
        }

}
