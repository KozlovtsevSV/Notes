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

    // пока будет ArrayList дальше будем работать с БД
   // public List<Note> noteList = new ArrayList<>();

//    DataBase(String[] notes, String[] notesDescription){
//        initDataBase(notes, notesDescription);
//    }

//    public void initDataBase(String[] notes, String[] notesDescription){
////        // Добавляем предопределенные заметки для первого задания в дальнейшем все заметки будем брать из БД
////        for(int i=0; i < notes.length; i++){
////            this.noteList.add(new Note(notes[i], notesDescription[i]));
////        }
//    }

//    public Note getNote(int index){
////        return noteList.get(index);
//    }
//
//    public Note addNote(){
////        Note newNote = new Note();
////        this.noteList.add(newNote);
////        return newNote;
//    }
//
//    public void deleteNote(Note note){
////        noteList.remove(note.getIndexNote());
//    }
//
//    public int getSize(){
////        return noteList.size();
//    }
//
//    public ArrayList<Note> getListNote(){
////        return noteList;
//    }
//
//    public void setDateNote(Note note){
////        noteList.set(note.getIndexNote(), note);
//    }



}
