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

    /**
     * get the current User by calling it on the database
     * @return User
     */
    public void getUser(Callback<User> cb){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String mail = currentUser.getEmail();

        db.collection("Users").whereEqualTo("Mail", mail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot document = task.getResult();
                if (!document.getDocuments().isEmpty()) {
                    String login = document.getDocuments().get(0).getString("Login");
                    User user = new User(login, mail);
                    db.collection("Users").document(login).collection("Challenges").get().addOnCompleteListener(task_challengePivot -> {
                        if (task_challengePivot.isSuccessful()) {
                            QuerySnapshot doc_challengePivots = task_challengePivot.getResult();
                            for(QueryDocumentSnapshot doc_challengepivot : doc_challengePivots){
                                String id_challenge = doc_challengepivot.getId();
                                db.collection("Challenges").document(id_challenge).get().addOnCompleteListener(task_challenge -> {
                                    if (task_challenge.isSuccessful()) {
                                     //   Challenge challenge = new Challenge(doc_challenge.get("Titre"),);

                                    }
                                 });
                            }
                        }
                    });
                    Log.d("User", user.getLogin());
                    cb.Call(user);
                }
            }
            });
    }
    public Bitmap getImage(String imageName) throws ExecutionException, InterruptedException {

        Bitmap bitmap = (Bitmap) new RetrieveImageInBackground().execute(imageName).get();

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
                            firestoreCallback.onCallback(map);
                        }
                    }
                });
    }

}
