package com.example.pjs4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import model.DataBase;
import model.User;
import views.Accueil;

public class MainActivity extends AppCompatActivity {

    private Button btn_go, btn_register;
    private static DataBase dataBase;
    private EditText ed1, ed2;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Pwd = "pwdKey";
    private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;

    public MainActivity() {


    }

    //rajouter la view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        if (sharedpreferences.getString(Name, null) != null) {
            Intent in = new Intent(MainActivity.this, Accueil.class);
            startActivity(in);
        }

        btn_go = (Button) findViewById(R.id.button_goAccueil);
        btn_register = findViewById(R.id.button_register);

        /* call database */
        dataBase = new DataBase(this);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        dataBase.onCreate(db);

        ed1 = findViewById(R.id.input_login);
        ed2 = findViewById(R.id.input_pwd);

        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get name and pwd from textbox
                final String login = ed1.getText().toString();
//quel pb???
                final String pwd = ed2.getText().toString();


/*
                //call the database to check user
                User user = dataBase.getUser(login, pwd);
                if(user != null){
                    //put the session on
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, login);
                    editor.putString(Pwd, pwd);
                    editor.commit();
*/

                mAuth.signInWithEmailAndPassword(login, pwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Log.d("Authentication", "Success");
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString(Name, login);
                                    editor.putString(Pwd, pwd);
                                    editor.commit();

                                    Intent in = new Intent(MainActivity.this, Accueil.class);
                                    startActivity(in);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d("Authentication", "Failed");
                                    Toast.makeText(MainActivity.this, "Authentification échouée",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

/*        btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(in);
                    //clear textbox
                    ed1.setText("");
                    ed2.setText("");

                }
                else{
                    ed1.setError("login ou mot de passe erroné");
                }

            }*/
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }

        });

        //tester ...
        //dataBase.close();

        /*btn_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, Accueil.class);
                //intent.putExtra("name", String ou autre); permettra de faire passer des infos entre 2 activités.. session?
                startActivity(intent);

            }
        });*/
    }

    public static DataBase getDataBase() {
        return dataBase;
    }
}