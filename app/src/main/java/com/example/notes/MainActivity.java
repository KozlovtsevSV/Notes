package com.example.notes;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    //public static ArrayList<Note> noteList = new ArrayList<>();
    public static DataBase dBase;
    Button mButtonBack;

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, new ListNotesFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        mButtonBack = findViewById(R.id.buttonBack);

    }

}

//    С этого урока мы начинаем разработку приложения с заметками.
//        Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
//        Создайте фрагмент для вывода этих данных.
//        Встройте этот фрагмент в активити. У вас должен получиться экран с заметками, который мы будем улучшать с каждым новым уроком.
//        Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока: если нажать на элемент списка в портретной ориентации — открывается новое окно, если нажать в ландшафтной — окно открывается рядом.
//        * Разберитесь, как можно сделать, и сделайте корректировку даты создания при помощи DatePicker.
//        * Задача для дополнительного обучения.

