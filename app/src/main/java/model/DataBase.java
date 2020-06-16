package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Challenge;

public class
DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbECOLO";
    private static final int VERSION = 1;

    //prepare creating request
    private final String table_challenge_createTable = "create table IF NOT EXISTS Challenge ("
            + "id_challenge Integer PRIMARY KEY," //begin at 1
            + "name_challenge Varchar (20),"
            + "description_challenge Varchar (80),"
            + "type_challenge Varchar (20)," //create an Enum for that
            + "xp_challenge Integer (3));" ;

    private final String table_user_createTable = "create table IF NOT EXISTS User ("
            + "id_user Integer PRIMARY KEY,"
            + "login Varchar (20),"
            + "email Varchar (80),"
            + "password Varchar (20),"
            + "level Integer(5));";

    private final String table_ChallengePivot_createTable = "create table IF NOT EXISTS ChallengePivot ("
            + "id_challengePivot Integer PRIMARY KEY,"
            + "id_user_fk Integer,"
            + "id_challenge_fk Integer,"
            + "done Integer,"
            + "FOREIGN KEY (id_user_fk) REFERENCES User(id_user),"
            + "FOREIGN KEY (id_challenge_fk) REFERENCES Challenge(id_challenge));";

    //always 4 parameters : context, name, le CursorFactory, version
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS User;");
        db.execSQL("DROP TABLE IF EXISTS Challenge;");
        db.execSQL("DROP TABLE IF EXISTS ChallengePivot;");

        /* create table */
        Log.i(null, "creation de la table");
        db.execSQL(table_challenge_createTable);
        db.execSQL(table_user_createTable);
        db.execSQL(table_ChallengePivot_createTable);

        /*input*/
        Log.i(null, "input challenge");
            String req1 = "INSERT INTO Challenge (id_challenge,name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (1,\"Ramasser 5 mégots\",\"Jeter dans une poubelle 5 mégots par terre. N'oubliez pas de vous laver les mains !\",\"Activité\",10);";
            String req2 = "INSERT INTO Challenge (id_challenge,name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (2,\"Manger une salade fait maison\",\"Préparer une salade avec des légumes de saisons en évitant les salades en sachets toutes prêtes.\",\"Alimentation\",5);";
            String req3 = "INSERT INTO Challenge (id_challenge,name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (3,\"Utiliser un mug\",\"Apportez votre mug pour éviter d'utiliser des gobelet jetables.\",\"Lifestyle\",5);";

        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);
        db.execSQL("INSERT INTO Challenge (id_challenge,name_challenge, description_challenge, type_challenge, xp_challenge) VALUES (4,\"Utiliser une gourde en inox ou en verre\",\"Apportez votre gourde pour éviter d'utiliser des bouteilles en plastiques.\",\"Lifestyle\",5);");

        Log.i(null, "input user");
        req1 = "INSERT INTO User (id_user,login, email, password, level) VALUES (1,\"Bobette\",\"bobobette@gmail.com\",\"bob\",0);";
        req2 = "INSERT INTO User (id_user,login, email, password, level) VALUES (2,\"Noelle\",\"noelle@gmail.com\",\"nono\",0);";
        req3 = "INSERT INTO User (id_user,login, email, password, level) VALUES (3,\"Dedey\",\"dedey@gmail.com\",\"audrey\",0);";

        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);

        req1 = "INSERT INTO User (id_user,login, email, password, level) VALUES (4,\"Elea\",\"elea@gmail.com\",\"elena\",0);";
        req2 = "INSERT INTO User (id_user,login, email, password, level) VALUES (5,\"galiixy\",\"galiixy@gmail.com\",\"galaxy\",0);";
        req3 = "INSERT INTO User (id_user,login, email, password, level) VALUES (6,\"Antoinette\",\"antoinette@gmail.com\",\"antoine\",0);";

        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);
        db.execSQL(req3 = "INSERT INTO User (id_user,login, email, password, level) VALUES (7,\"jevoisrose\",\"jevoisrose@gmail.com\",\"alexis\",0);");

        Log.i(null, "input challenge pivot user");
        req1 = "INSERT INTO ChallengePivot (id_challengePivot,id_user_fk, id_challenge_fk,done) VALUES (1,2,1,0);";
        req2 = "INSERT INTO ChallengePivot (id_challengePivot,id_user_fk, id_challenge_fk,done) VALUES (2,1,2,0);";
        req3 = "INSERT INTO ChallengePivot (id_challengePivot,id_user_fk, id_challenge_fk,done) VALUES (3,2,2,1);";
        db.execSQL(req1);
        db.execSQL(req2);
        db.execSQL(req3);
        db.execSQL("INSERT INTO ChallengePivot (id_challengePivot,id_user_fk, id_challenge_fk,done) VALUES (4,2,3,0);");
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
            String dif = "test";

            Challenge c = new Challenge(id, name, description, dif, type, xp);
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

    /*public User getUser(String login, String pwd){
        String req = "SELECT * From User Where login=? and password=?;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req,new String[] {login, pwd}); //execute the query
        Cursor cursor_challenge = null;
        Challenge challenge;

        //if query is empty
        if(cursor.getCount()<=0){
            cursor.close();
            return null;
        }
        //creation of User object
        cursor.moveToFirst();
        User user = new User(login, cursor.getString(2), pwd);
        //list of user's challenge
        req = "SELECT * From ChallengePivot Where id_user_fk=?;";
        cursor = db.rawQuery(req, new String[]{String.valueOf(cursor.getInt(0))});
        cursor.moveToFirst();

        //find those challenge and creating the object Challenge
       /* for(int cpt_challenge=0;cpt_challenge<cursor.getCount();cpt_challenge++){
            req = "SELECT * From Challenge Where id_challenge=?;";
            cursor_challenge = db.rawQuery(req,new String[] {String.valueOf(cursor.getInt(2))}); //execute the query
            cursor_challenge.moveToFirst();

            challenge = new Challenge(cursor_challenge.getInt(0),
                    cursor_challenge.getString(1),cursor_challenge.getString(2),
                    cursor_challenge.getString(3),cursor_challenge.getInt(4));
            user.addChallenge(challenge);

            cursor.moveToNext();
        }
        cursor_challenge.close();
        cursor.close();
        return user;
    }*/

   /*public Challenge getRandomChallenge(){
        //query
        String req = "SELECT * From Challenge ;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req,null); //execute the query
        cursor.moveToFirst();

        int nbMax =cursor.getCount();

        int idRandomChallenge = new Random().nextInt(nbMax+1);
        if (idRandomChallenge ==0){
            idRandomChallenge =1;
        }

        req = "SELECT * From Challenge where id_challenge=? ;";
        cursor = db.rawQuery(req, new String[]{String.valueOf(idRandomChallenge)}); //execute the query
        cursor.moveToFirst();
        Challenge challenge = new Challenge(cursor.getInt(0),
                cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getInt(4));
        cursor.close();
        //return challenge;
    }*/
}
