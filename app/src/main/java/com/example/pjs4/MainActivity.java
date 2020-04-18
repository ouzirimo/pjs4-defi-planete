package com.example.pjs4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import model.Challenge;
import model.DataBase;
import views.Accueil;

public class MainActivity extends AppCompatActivity {

    private Button bt_go,btn_register;
    private DataBase dataBase;

    public MainActivity(){


    }

    //rajouter la view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_go = (Button) findViewById(R.id.button_goAccueil);
        btn_register= (Button) findViewById(R.id.button_register);
        //AllChallenge = (TextView) findViewById(R.id.AllChallenge);
        dataBase = new DataBase(this); //création dataBase



        //Créer la base et insérer des valeurs (à faire une fois) insérer des user + autres challenges
        //String req = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (1, \"Ramasser 5 mégots\",\"Jeter dans une poubelle 5 mégots par terre. N'oubliez pas de vous laver les mains !\",\"Activité\",10);";
        /*String req2 = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (2, \"Manger une salade fait maison\",\"Préparer une salade avec des légumes de saisons en évitant les salades en sachets toutes prêtes.\",\"Alimentation\",5);";
        String req3 = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (3, \"Utiliser un tote bag\",\"Munissez-vous d'un tote bag pour éviter d'utiliser des sacs plastiques.,\"Lifestyle\",5);";
        String req4 = "INSERT INTO Challenge (id_challenge, name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (4, \"Utiliser un mug\",\"Apportez votre mug pour éviter d'utiliser des gobelet jetables.\",\"Lifestyle\",5);";

        //dataBase.ajouter_challenge(req);
        dataBase.ajouter_challenge(req2);
        dataBase.ajouter_challenge(req3);
        dataBase.ajouter_challenge(req4);*/


        //tester ...
        dataBase.close();

        bt_go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, Accueil.class);
                //intent.putExtra("name", String ou autre); permettra de faire passer des infos entre 2 activités.. session?
                startActivity(intent);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }

        });
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    //On click.. bt go

    //partie Elea et Noelle


    //au moment de l'ouverture, générer la liste de challenge pour un utilisateur
}
