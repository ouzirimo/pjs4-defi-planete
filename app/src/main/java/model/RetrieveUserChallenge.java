package model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RetrieveUserChallenge extends AsyncTask<String, Integer, HashMap> {

    @Override
    protected HashMap doInBackground(String... strings) {
        final HashMap map = new HashMap();

        Task<QuerySnapshot> task = FirebaseFirestore.getInstance().collection("Users").document(strings[0]).collection("Challenge").get();
        try {
            QuerySnapshot querySnapshot = Tasks.await(task);

            for (QueryDocumentSnapshot document : task.getResult()) {

                Map<String, Object> challenge = new HashMap<>();
                challenge.put("Etat", document.getString("Etat"));
                map.put(document.getId(), challenge);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(int i=0;i<map.size();i++) {
            Task<DocumentSnapshot> task2 = FirebaseFirestore.getInstance().collection("Challenge").document((String) map.keySet().toArray()[i]).get();
            try {
                DocumentSnapshot querySnapshot2 = Tasks.await(task2);
                if(task2.isSuccessful()){
                    DocumentSnapshot document = task2.getResult();
                    if(document.exists()){
                        HashMap challenge = (HashMap) map.get(map.keySet().toArray()[i]);
                        challenge.put("Difficulté", document.getDouble("Difficulté"));
                        challenge.put("Label", document.getString("Label"));
                        challenge.put("Titre", document.getString("Titre"));
                        challenge.put("Type", document.getString("Type"));
                        if (document.getString("Lien") != null) {
                            challenge.put("Lien", document.getString("Lien"));
                        }
                        map.put(document.getId(), challenge);
                    }
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Log.d("MapUserChallenge", map.toString());
        return map;
    }
}

