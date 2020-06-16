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

    public HashMap getAllChallenges() throws ExecutionException, InterruptedException {

        HashMap map = (HashMap) new RetrieveChallengeInBackground().execute().get();

        return map;
    }

    public Bitmap getImage(String imageName) throws ExecutionException, InterruptedException {

        Bitmap bitmap = (Bitmap) new RetrieveImageInBackground().execute(imageName).get();

        return bitmap;
    }

}
