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
    //mettre les chaines de caractères représentant les create table de la base

    private final String table_challenge_createTable = "create table challenge (" + "id_challenge Integer PRIMARY KEY,"
            + "name Varchar (20),"
            + "description Varchar (80),"
            + "type Varchar (20),"
            + "xp Integer (3));" ;


    //4 paramètres toujours: context, le nom, le CursorFactory, la version
    public DataBase(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(table_challenge_createTable);
        Log.i(null, "creation de la table");
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
}
