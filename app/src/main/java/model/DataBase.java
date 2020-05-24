package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import model.Challenge;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbECOLO";
    private static final int VERSION = 1;

    //prepare creating request
    private final String table_challenge_createTable = "create table Challenge ("
            + "id_challenge Integer PRIMARY KEY AUTOINCREMENT,"
            + "name Varchar (20),"
            + "description Varchar (80),"
            + "type Varchar (20)," //create an Enum for that
            + "xp Integer (3));" ;

    private final String table_user_createTable = "create table User ("
            + "id_user Integer PRIMARY KEY AUTOINCREMENT,"
            + "login Varchar (20),"
            + "email Varchar (80),"
            + "password Varchar (20),"
            + "level Integer(5));";

    private final String table_ChallengePivot_createTable = "create table ChallengePivot ("
            + "id_challengePivot Integer PRIMARY KEY AUTOINCREMENT,"
            + "id_user_fk Integer,"
            + "id_challenge_fk Integer,"
            + "done Integer,"
            + "FOREIGN KEY (id_user_fk) REFERENCES User(id_user),"
            + "FOREIGN KEY (id_challenge_fk) REFERENCES Challenge(id_challenge);";

    //always 4 parameters : context, name, le CursorFactory, version
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /* create table */
        Log.i(null, "creation de la table");
        db.execSQL(table_challenge_createTable);
        db.execSQL(table_user_createTable);
        db.execSQL(table_user_createTable);

        /*input*/
        Log.i(null, "input challenge");
        String req1 = "INSERT INTO Challenge (name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (\"Ramasser 5 mégots\",\"Jeter dans une poubelle 5 mégots par terre. N'oubliez pas de vous laver les mains !\",\"Activité\",10);";
        String req2 = "INSERT INTO Challenge (name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (\"Manger une salade fait maison\",\"Préparer une salade avec des légumes de saisons en évitant les salades en sachets toutes prêtes.\",\"Alimentation\",5);";
        String req3 = "INSERT INTO Challenge (name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (\"Utiliser un mug\",\"Apportez votre mug pour éviter d'utiliser des gobelet jetables.\",\"Lifestyle\",5);";

        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);

        Log.i(null, "input user");
        req1 = "INSERT INTO User (login, email, password, level) VALUES (\"Bobette\",\"bobobette@gmail.com\",\"bob\",0);";
        req2 = "INSERT INTO User (login, email, password, level) VALUES (\"Noelle\",\"noelle@gmail.com\",\"nono\",0);";
        req3 = "INSERT INTO User (login, email, password, level) VALUES (\"Dedey\",\"dedey@gmail.com\",\"audrey\",0);";

        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);

        req1 = "INSERT INTO User (login, email, password, level) VALUES (\"Elea\",\"elea@gmail.com\",\"elena\",0);";
        req2 = "INSERT INTO User (login, email, password, level) VALUES (\"galiixy\",\"galiixy@gmail.com\",\"galaxy\",0);";
        req3 = "INSERT INTO User (login, email, password, level) VALUES (\"Antoinette\",\"antoinette@gmail.com\",\"antoine\",0);";

        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);
        db.execSQL(req3 = "INSERT INTO User (login, email, password, level) VALUES (\"jevoisrose\",\"jevoisrose@gmail.com\",\"alexis\",0);");

        Log.i(null, "input challenge pivot user");
        req1 = "INSERT INTO ChallengePivot (id_user_fk, id_challenge_fk,done) VALUES (1,1);";
        req2 = "INSERT INTO ChallengePivot (id_user_fk, id_challenge_fk,done) VALUES (1,2);";
        req3 = "INSERT INTO ChallengePivot (id_user_fk, id_challenge_fk,done) VALUES (2,2);";
        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    //Toutes les requêtes nécessaires...

    public void ajouter_challenge(String reqInsert_challenge){ //rajouter en paramètre une instance de la classe DetailChallenge (à créer)

        this.getWritableDatabase().execSQL(reqInsert_challenge);

    }

    public List<Challenge> getAllChallenge(){

        List<Challenge> liste = new ArrayList<>();


        String req = "Select * from challenge";
        Cursor cus = this.getReadableDatabase().rawQuery(req, null);

        cus.moveToFirst();

        while(!cus.isAfterLast()){ //tant que je ne suis pas à la fin de ma dernière lecture

            int id = cus.getInt(0);
            String name = cus.getString(1);
            String description = cus.getString(2);
            String type = cus.getString(3);
            int xp = cus.getInt(4);

            Challenge c = new Challenge(id, name, description, type, xp);
            liste.add(c);

            cus.moveToNext();
        }

        cus.close();
        return liste;
    }

    public void addNewUser(String login, String email, String pwd){

        String req1 = "INSERT INTO User (login, email, password, level) VALUES (\" " + login +"\",\""+ email +"\",\""+ pwd +"\",1);";
        this.getWritableDatabase().execSQL(req1);

    }

    public Object getUser(){

        return null;

    }

}
