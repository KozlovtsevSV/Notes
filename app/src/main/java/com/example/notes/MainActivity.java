package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View viewFragmentContainer = findViewById(R.id.fragment_container);
        View viewFragmentContainerNote = findViewById(R.id.fragment_container_note);
        LinearLayout.LayoutParams layoutParamsFragmentContainer = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams layoutParamsFragmentContainerNote = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutParamsFragmentContainer.weight = 1;
            viewFragmentContainer.setLayoutParams(layoutParamsFragmentContainer);
            layoutParamsFragmentContainerNote.weight = 2;
            viewFragmentContainerNote.setLayoutParams(layoutParamsFragmentContainerNote);
        }else{
            layoutParamsFragmentContainer.weight = 1;
            viewFragmentContainer.setLayoutParams(layoutParamsFragmentContainer);
            layoutParamsFragmentContainerNote.weight = 0;
            viewFragmentContainerNote.setLayoutParams(layoutParamsFragmentContainerNote);
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ListNotesFragment listNotesFragment = new ListNotesFragment();
        transaction.replace(R.id.fragment_container, listNotesFragment);
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Заглушка Настройки", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                Toast.makeText(getApplicationContext(), "Заглушка Избранное", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about_author:
                Toast.makeText(getApplicationContext(), "Заглушка О програме", Toast.LENGTH_SHORT).show();
                 return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }
            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

}
//    С этого урока мы начинаем разработку приложения с заметками.
//        Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
//        Создайте фрагмент для вывода этих данных.
//        Встройте этот фрагмент в активити. У вас должен получиться экран с заметками, который мы будем улучшать с каждым новым уроком.
//        Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока: если нажать на элемент списка в портретной ориентации — открывается новое окно, если нажать в ландшафтной — окно открывается рядом.
//        * Разберитесь, как можно сделать, и сделайте корректировку даты создания при помощи DatePicker.
//        * Задача для дополнительного обучения.

