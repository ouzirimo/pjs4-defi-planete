package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RetrieveImageInBackground extends AsyncTask<String, Integer, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        Log.d("","start of do it background !!!!!!!!!!!!!!!!!");
        final Bitmap[] bitmap = {null};

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("Challenges/" + strings[0]);

        final long ONE_MEGABYTE = 1024 * 1024;
        imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        return bitmap[0];
    }
}
