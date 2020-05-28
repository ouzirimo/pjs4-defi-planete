package views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pjs4.MainActivity;
import com.example.pjs4.R;

import model.DataBase;
import model.User;

public class Accueil extends AppCompatActivity {

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btn_logout =(Button)findViewById(R.id.btn_logout);
        txt_name = (TextView) findViewById(R.id.session_name);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        //User Information
        String userName = sharedpreferences.getString("nameKey", null);
        String userPwd = sharedpreferences.getString("pwdKey", null);
        DataBase database = MainActivity.getDataBase();
        User user = database.getUser(userName, userPwd);
        txt_name.append(userName);
    }

    public void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        this.finish();
    }
}
