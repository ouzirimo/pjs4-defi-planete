package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FireBase {
    FirebaseFirestore db;
    FirebaseStorage strg;

    public FireBase(){
        db = FirebaseFirestore.getInstance();
        strg = FirebaseStorage.getInstance();
    }

    public void addNewUser(String login, String mail){

        Calendar calendar = Calendar.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Login", login);
        user.put("Mail", mail);
        user.put("Register Date", calendar.getTime());

        db.collection("Users").document(login)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Wrinting firestore db", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Wrinting firestore db", "Error writing document", e);
                    }
                });
    }

    public void getAllChallenges(final FirestoreCallback firestoreCallback){

        final HashMap map = new HashMap();

        db.collection("Challenge")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("Get challenges", document.getId() + " => " + document.getData());
                                Map<String, Object> challenge = new HashMap<>();
                                challenge.put("Difficulté", document.getDouble("Difficulté"));
                                challenge.put("Label", document.getString("Label"));
                                challenge.put("Titre", document.getString("Titre"));
                                challenge.put("Type", document.getString("Type"));
                                if (document.getString("Lien") != null) {
                                    challenge.put("Lien", document.getString("Lien"));
                                }
                                map.put(document.getId(), challenge);
                            }
                            firestoreCallback.onCallback(map);
                        }
                    }
                });
    }


    public Bitmap getImage(String imageName) throws ExecutionException, InterruptedException {

        Bitmap bitmap = new RetrieveImageInBackground().execute(imageName).get();

        return bitmap;
    }

    public void getUserChallenge(String user, final FirestoreCallback firestoreCallback) {
        final HashMap map = new HashMap();

        db.collection("Users").document(user).collection("Challenge")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> challenge = new HashMap<>();
                                challenge.put("Etat", document.getString("Etat"));
                                map.put(document.getId(), challenge);
                            }
                            Log.d("Uncomplet challenge", map.toString());
                        }
                    }
                });


        for (int i = 0; i < map.size(); i++) {
            final int finalI = i;
            Log.d("KeyNumber", (String) map.keySet().toArray()[i]);
            db.collection("Challenge").document((String) map.keySet().toArray()[i])
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    HashMap challenge = (HashMap) map.get(map.keySet().toArray()[finalI]);
                                    challenge.put("Difficulté", document.getDouble("Difficulté"));
                                    challenge.put("Label", document.getString("Label"));
                                    challenge.put("Titre", document.getString("Titre"));
                                    challenge.put("Type", document.getString("Type"));
                                    if (document.getString("Lien") != null) {
                                        challenge.put("Lien", document.getString("Lien"));
                                    }
                                    map.put(document.getId(), challenge);
                                }
                                firestoreCallback.onCallback(map);
                            }
                        }
                    });

            Log.d("MapUserChallenge", map.toString());


        }
    }

}
