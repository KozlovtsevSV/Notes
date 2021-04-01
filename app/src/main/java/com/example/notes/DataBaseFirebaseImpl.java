package com.example.notes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class DataBaseFirebaseImpl extends DataBaseSource {
    private static final String TAG = "DataBaseFirebase";
    private final static String COLLECTION_NOTES = "com.kozlovtsev.CollectionNotes";
    private volatile static DataBaseFirebaseImpl sInstance;

    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private final CollectionReference mCollection = mStore.collection(COLLECTION_NOTES);

    public static DataBaseFirebaseImpl getInstance() {
        DataBaseFirebaseImpl instance = sInstance;
        if (instance == null) {
            synchronized (DataBaseImpl.class) {
                if (sInstance == null) {
                    instance = new DataBaseFirebaseImpl();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private DataBaseFirebaseImpl() {
        mCollection.orderBy(NoteFromFirestore.FIELD_DATE, Query.Direction.DESCENDING).get().
                addOnCompleteListener(this::onFetchComplete).
                addOnFailureListener(this::onFetchFailed);
    }

    private void onFetchComplete(Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            LinkedList<Note> data = new LinkedList<>();
            for (QueryDocumentSnapshot document : task.getResult()) {
                data.add(new NoteFromFirestore(
                        document.getId(), document.getData()));
            }
            mData.clear();
            mData.addAll(data);
            data.clear();
            notifyDataSetChanged();
        }
    }

    private void onFetchFailed(Exception e) {
        Log.e(TAG, "Fetch failed", e);
    }

    @Override
    public void add(@NonNull Note data) {
        final NoteFromFirestore cardData;
        if (data instanceof NoteFromFirestore) {
            cardData = (NoteFromFirestore) data;
        } else {
            cardData = new NoteFromFirestore(data);
        }
        mData.add(cardData);

//        UUID uuid = UUID.randomUUID();
//        String randomUUIDString = uuid.toString();
//
//        cardData.setIndexNote(randomUUIDString);

        mCollection.add(cardData.getFields()).addOnSuccessListener(documentReference -> {
            cardData.setIndexNote(documentReference.getId());
        });

    }

    @Override
    public void remove(int position) {
        String id = mData.get(position).getIndexNote();
        mCollection.document(id).delete();
        super.remove(position);
    }

    @Override
    public void clear() {
        for (Note cardData : mData) {
       //     mCollection.document(cardData.getIndexNote().delete();
        }
        super.clear();
    }
}

