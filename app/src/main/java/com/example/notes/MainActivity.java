package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    public static DataBase dBase;
    public static ListNotesFragment mListNotesFragment = ListNotesFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState==null){
            // подключаемся к базе
            String[] notes = getResources().getStringArray(R.array.notes);
            String[] notesDescription = getResources().getStringArray(R.array.notesDescription);
            dBase = new DataBase(notes, notesDescription);

            addFragment(mListNotesFragment);
        }

        changeOrientation(mListNotesFragment);

    }

    private void changeOrientation(Fragment fragment){
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
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void addFragment(Fragment fragment) {
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

}
//    С этого урока мы начинаем разработку приложения с заметками.
//        Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
//        Создайте фрагмент для вывода этих данных.
//        Встройте этот фрагмент в активити. У вас должен получиться экран с заметками, который мы будем улучшать с каждым новым уроком.
//        Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока: если нажать на элемент списка в портретной ориентации — открывается новое окно, если нажать в ландшафтной — окно открывается рядом.
//        * Разберитесь, как можно сделать, и сделайте корректировку даты создания при помощи DatePicker.
//        * Задача для дополнительного обучения.


/*    Создайте список ваших заметок.
        Создайте карточку для элемента списка.
        Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка.
        * Создайте фрагмент для редактирования данных в конкретной карточке. Этот фрагмент пока можно вызвать через основное меню.
        * Задача для дополнительного обучения.*/



//    Сделайте фрагмент добавления и редактирования данных, если вы ещё не сделали его.
//        Сделайте навигацию между фрагментами, также организуйте обмен данными между фрагментами.
//        Создайте контекстное меню для изменения и удаления заметок.
//        *Изучите, каким образом можно вызывать DatePicker в виде диалогового окна. Создайте текстовое поле, при нажатии на которое вызывалось бы диалоговое окно с DatePicker.



