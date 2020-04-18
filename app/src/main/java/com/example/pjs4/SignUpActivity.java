package com.example.pjs4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail,etPassword,etLogin,etConPass;
    Button btnRegister;
    String email,login,pass,conPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
            else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError("Email manquant");
                etEmail.requestFocus();
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
            else if (pass.equals(conPass)){
                etPassword.setError("Les mots de passe ne correspondent pas ");
                valid =false;
            }

            return valid;
        }

        public void onSignUpSucces(){

        }

        public void register (){
        initialize();
        if(!validate()){
            Toast.makeText(this,"Inscription incorrecte", Toast.LENGTH_SHORT).show();
        }else{
            onSignUpSucces();
        }

        }

}
