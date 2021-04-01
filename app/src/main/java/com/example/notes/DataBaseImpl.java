package com.example.notes;

import android.content.res.Resources;

import java.util.LinkedList;

public class DataBaseImpl extends DataBaseSource {
    private volatile static DataBaseImpl sInstance;
    private final LinkedList<Note> mData = new LinkedList<>();

    public static DataBaseImpl getInstance(Resources resources) {
        DataBaseImpl instance = sInstance;
        if (instance == null) {
            synchronized (DataBaseImpl.class) {
                if (sInstance == null) {
                    instance = new DataBaseImpl(resources);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private DataBaseImpl(Resources resources) {
        String[] noteNames = resources.getStringArray(R.array.notes);
        String[] noteText = resources.getStringArray(R.array.notesDescription);

        for (int i = 0; i < noteNames.length; i++) {
            mData.add(new Note(noteNames[i],
                    noteText[i]));
        }

//        String[] fruitNames = resources.getStringArray(R.array.fruit_names);
//
//        TypedArray imgs = resources.obtainTypedArray(R.array.fruit_imgs);
//        for (int i = 0; i < fruitNames.length; i++) {
//            mData.add(new CardData(fruitNames[i],
//                    imgs.getResourceId(i, -1)));
//        }
 //       imgs.recycle();
        notifyDataSetChanged();

    }

}