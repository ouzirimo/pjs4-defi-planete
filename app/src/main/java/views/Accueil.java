package views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pjs4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import model.DataBase;
import model.FireBase;
import model.User;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private String uName;
    private String uPass;
    private User user;
    private FireBase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btn_logout = findViewById(R.id.btn_logout);
        txt_name = findViewById(R.id.session_name);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            uName = fbUser.getDisplayName();
        }

        fb = new FireBase();
        HashMap map = new HashMap();
        try {
            fb.getImage("apples-1841132_1920.jpg");
            map = fb.getAllChallenges();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("Final map", map.toString());


        //User Information
        txt_name.append(uName);
    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }
}
