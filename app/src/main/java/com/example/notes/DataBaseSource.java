package com.example.notes;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class DataBaseSource implements DataBase {
    private HashSet<DataBaseListener> mListeners = new HashSet<>();
    protected final LinkedList<Note> mData = new LinkedList<>();

    public void addDataBaseListener(DataBaseListener listener){
        mListeners.add(listener);
    }

    public void removeDataBaseListener(DataBaseListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public List<Note> getNote() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public Note getItemAt(int index) {
        return mData.get(index);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }


    @Override
    public void add(@NonNull Note data) {
        mData.add(data);
        int index = mData.size() - 1;
        for (DataBaseListener listener : mListeners) {
            listener.onItemAdded(index);
        }

    }

    @Override
    public void remove(int position) {
        mData.remove(position);
        for (DataBaseListener listener : mListeners) {
            listener.onItemRemoved(position);
        }
    }

    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void update(@NonNull Note data) {
        String id = data.getIndexNote();
        if (id != null) {
            int index = 0;
            for (Note noteData : mData) {
                if (id.equals(noteData.getIndexNote())) {
                    noteData.setNameNote(data.getNameNote());
                    noteData.setTextNote(data.getTextNote());
                    noteData.setDateNewNote(data.getDateNoteLong());
                    notifyUpdated(index);
                    return;
                }
                index++;
            }

        }
        add(data);
    }

    protected final void notifyUpdated(int index) {
        for (DataBaseListener listener : mListeners) {
            listener.onItemUpdated(index);
        }
    }

    protected final void notifyDataSetChanged() {
        for (DataBaseListener listener : mListeners) {
            listener.onDataSetChanged();
        }
    }
}
