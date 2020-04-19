package views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pjs4.MainActivity;
import com.example.pjs4.R;

import java.util.List;

import model.Challenge;
import model.DataBase;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;
    private Button bt_logout;
    private TextView challenges;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        bt_logout =(Button)findViewById(R.id.bt_logout);
        name = (TextView) findViewById(R.id.session_name);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        String userName = sharedpreferences.getString("nameKey", null);
        name.append(userName);

        //on récup le textView

        /*challenges = (TextView) findViewById(R.id.text_AllChallenge);
        //on récup la data base
        //MainActivity main = new MainActivity();

        DataBase db = new DataBase(this);

        List<Challenge> l = db.getAllChallenge();

        for(Challenge c : l){

            challenges.append(c.toString() + "\n\n");
        }

        db.close();*/

    }

    public void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        this.finish();

    }
}
