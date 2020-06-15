package model;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RetrieveChallengeInBackground extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        final HashMap map = new HashMap();

        Task<QuerySnapshot> task = FirebaseFirestore.getInstance().collection("Challenge")
                .get();

        try {
            QuerySnapshot querySnapshot = Tasks.await(task);

            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("Get challenges", document.getId() + " => " + document.getData());
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

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return map;
    }
}
