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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FireBase {
    FirebaseFirestore db;
    FirebaseStorage strg;
    private HashMap <String, Challenge> challenges; //id_challenge, object challenge

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

    public void getAllChallenges(){

        this.challenges = new HashMap();

        db.collection("Challenge")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                long difficulty = document.getLong("Difficulté");
                                String difficulty_type;
                                if (difficulty==1){
                                    difficulty_type = "Facile";
                                }
                                else if(difficulty==2){
                                    difficulty_type = "Moyen";
                                }
                                else{
                                    difficulty_type = "Difficile";
                                }
                                Challenge challenge = new Challenge(Integer.parseInt(document.getId()),document.getString("Titre"),
                                        document.getString("Label"), document.getString("Type"),difficulty_type
                                        , difficulty, document.getString("Lien"));

                                challenges.put(document.getId(), challenge);
                            }
                        }
                    }
                });
    }

    /**
     * get the current User by calling it on the database
     * @return User
     */
    public void getUser(FirestoreCallback<User> callback){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String mail = currentUser.getEmail();

        db.collection("Users").whereEqualTo("Mail", mail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot document = task.getResult();
                String login = document.getDocuments().get(0).getString("Login");
                User user = new User(login, mail);
                getUserChallenge(login, new FirestoreCallback<HashMap<String, String>>() {
                    @Override
                    public void onCallback(HashMap <String, String> map_challengePivot) {
                        for (Map.Entry mapentry : map_challengePivot.entrySet()) { // For each challenge pivot
                            String status = mapentry.getValue().toString();
                            ChallengeStatus challengeStatus;
                            if (status.equals("enCours")){
                                challengeStatus = new UnDone();
                            }
                            else if(status.equals("enAttente")){
                                challengeStatus = new InProgress();
                            }
                            else{
                                challengeStatus = new Done();
                            }
                            user.addChallenge(challenges.get(mapentry.getKey()), challengeStatus);
                        }

                    }
                });
                Log.d("User", user.getLogin());
                callback.onCallback(user);
            }
        });
    }


    public void getImage(String imageName, final FirestoreCallback <Bitmap> callback){

        final Bitmap[] b = new Bitmap[1];
        StorageReference storageRef = strg.getReference();
        StorageReference imagesRef = storageRef.child("Challenges/" + imageName);
        final long ONE_MEGABYTE = 1024 * 1024;
        imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                b[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                callback.onCallback(b[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }


    public void getUserChallenge(String login, final FirestoreCallback firestoreCallback) {
        final HashMap <String, String> map = new HashMap();

        db.collection("Users").document(login).collection("Challenge")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                map.put(document.getId(), document.getString("Etat"));
                            }
                            Log.d("Uncomplet challenge", map.toString());
                            firestoreCallback.onCallback(map);
                        }
                    }
                });
    }

}
