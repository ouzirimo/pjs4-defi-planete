package model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FireBase {
    FirebaseFirestore db;
    private final String TAG;

    public FireBase(){
        db = FirebaseFirestore.getInstance();
        TAG = "Jsp à quoi ca sert";
    }

    public void addNewUser(String login, String mail){

        Calendar calendar = Calendar.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Login", login);
        user.put("Mail", mail);
        user.put("Register Date", calendar.getTime());

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    //à transformer en return pour les afficher dans la session
    //puis ensuite fonction de random...
    public void getAllDocumentTest(){

        db.collection("Challenge")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " MARCHEEEEEEEEEE => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: MARCHE PAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAS ", task.getException());
                        }
                    }
                });


    }
}
