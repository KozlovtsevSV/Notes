package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DataBase extends AppCompatActivity {

    // пока будет ArrayList дальше будем работать с БД
    private ArrayList<Note> noteList = new ArrayList<>();

    DataBase(String[] notes, String[] notesDescription){
        initDataBase(notes, notesDescription);
    }

    private void initDataBase(String[] notes, String[] notesDescription){

//        String[] notes = getResources().getStringArray(R.array.notes);
//        String[] notesDescription = getResources().getStringArray(R.array.notesDescription);
        // Добавляем предопределенные заметки для первого задания в дальнейшем все заметки будем брать из БД
        for(int i=0; i < notes.length; i++){
            this.noteList.add(new Note(notes[i], notesDescription[i]));
        }
    }

    public Note getNote(int index){
        return noteList.get(index);
    }

    public ArrayList<Note> getListNote(){
        return noteList;
    }

    public void setDateNote(Note note){
        noteList.set(note.getIndexNote(), note);
    }

}
