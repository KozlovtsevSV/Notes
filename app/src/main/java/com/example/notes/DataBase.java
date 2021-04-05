package com.example.notes;

import androidx.annotation.NonNull;

import java.util.List;

//public class DataBase extends AppCompatActivity {
public interface DataBase {

    interface DataBaseListener {
        void onItemAdded(int index);
        void onItemRemoved(int index);
        void onItemUpdated(int index);
        void onDataSetChanged();
    }

    void addDataBaseListener(DataBaseListener listener);
    void removeDataBaseListener(DataBaseListener listener);

    List<Note> getNote();
    Note getItemAt(int index);
    int getItemsCount();

    void add(@NonNull Note data);
    void update(@NonNull Note data);
    void remove(int position);
    void clear();

}
