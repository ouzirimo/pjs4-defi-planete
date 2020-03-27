package views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pjs4.MainActivity;
import com.example.pjs4.R;

import java.util.List;

import model.Challenge;
import model.DataBase;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;

    private TextView challenges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //on récup le textView

        challenges = (TextView) findViewById(R.id.text_AllChallenge);
        //on récup la data base
        //MainActivity main = new MainActivity();

        DataBase db = new DataBase(this);

        List<Challenge> l = db.getAllChallenge();

        for(Challenge c : l){

            challenges.append(c.toString() + "\n\n");
        }

        db.close();

    }
}
