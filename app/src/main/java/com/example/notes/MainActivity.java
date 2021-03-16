package com.example.notes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //public static ArrayList<Note> noteList = new ArrayList<>();
    public static DataBase dBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            // подключаемся к базе
            String[] notes = getResources().getStringArray(R.array.notes);
            String[] notesDescription = getResources().getStringArray(R.array.notesDescription);
            dBase = new DataBase(notes, notesDescription);
        }
        setContentView(R.layout.activity_main);

    }

//    private void initNotes(){
//
//        String[] notes = getResources().getStringArray(R.array.notes);
//        String[] notesDescription = getResources().getStringArray(R.array.notesDescription);
//        // Добавляем предопределенные заметки для первого задания в дальнейшем все заметки будем брать из БД
//        for(int i=0; i < notes.length; i++){
//            noteList.add(new Note(notes[i], notesDescription[i]));
//        }
//    }

}

//    С этого урока мы начинаем разработку приложения с заметками.
//        Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
//        Создайте фрагмент для вывода этих данных.
//        Встройте этот фрагмент в активити. У вас должен получиться экран с заметками, который мы будем улучшать с каждым новым уроком.
//        Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока: если нажать на элемент списка в портретной ориентации — открывается новое окно, если нажать в ландшафтной — окно открывается рядом.
//        * Разберитесь, как можно сделать, и сделайте корректировку даты создания при помощи DatePicker.
//        * Задача для дополнительного обучения.
