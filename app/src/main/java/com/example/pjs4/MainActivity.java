package com.example.pjs4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import model.DataBase;
import views.Accueil;

public class MainActivity extends AppCompatActivity {

    private Button bt_go;
    private DataBase dataBase;
    private EditText ed1, ed2;

    //rajouter les input??

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey"; //pk mettre ici le name et mdp comme il est déjà dans la classe user car var session?
    public static final String Pwd = "pwdKey";
    SharedPreferences sharedpreferences;

    public MainActivity(){


    }

    //rajouter la view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getString(Name, null) != null){
            Intent in = new Intent(MainActivity.this, Accueil.class);
            startActivity(in);
        }

        bt_go = (Button) findViewById(R.id.button_goAccueil);
        //AllChallenge = (TextView) findViewById(R.id.AllChallenge);
        // = new DataBase(this); //création dataBase

        //Créer la base et insérer des valeurs (à faire une fois) insérer des user + autres challenges
        //String req = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (1, \"Ramasser 5 mégots\",\"Jeter dans une poubelle 5 mégots par terre. N'oubliez pas de vous laver les mains !\",\"Activité\",10);";
        /*String req2 = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (2, \"Manger une salade fait maison\",\"Préparer une salade avec des légumes de saisons en évitant les salades en sachets toutes prêtes.\",\"Alimentation\",5);";
        String req3 = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (3, \"Utiliser un tote bag\",\"Munissez-vous d'un tote bag pour éviter d'utiliser des sacs plastiques.,\"Lifestyle\",5);";
        String req4 = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (4, \"Utiliser un mug\",\"Apportez votre mug pour éviter d'utiliser des gobelet jetables.\",\"Lifestyle\",5);";

        //dataBase.ajouter_challenge(req);
        dataBase.ajouter_challenge(req2);
        dataBase.ajouter_challenge(req3);
        dataBase.ajouter_challenge(req4);*/



        ed1 = findViewById(R.id.input_name);
        ed2 = findViewById(R.id.input_password);
        System.out.println("Test des edt");

        //b1=(Button)findViewById(R.id.button);


        bt_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String n  = ed1.getText().toString(); //quel pb???
                String pwd  = ed2.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name, n);
                editor.putString(Pwd, pwd);
                editor.commit();

                Intent in = new Intent(MainActivity.this, Accueil.class);
                startActivity(in);
            }
        });


        //tester ...
        //dataBase.close();

        /*bt_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, Accueil.class);
                //intent.putExtra("name", String ou autre); permettra de faire passer des infos entre 2 activités.. session?
                startActivity(intent);

            }
        });*/
    }

    public DataBase getDataBase() {

        return dataBase;
    }

    //On click.. bt go

    //partie Elea et Noelle


    //au moment de l'ouverture, générer la liste de challenge pour un utilisateur
}
