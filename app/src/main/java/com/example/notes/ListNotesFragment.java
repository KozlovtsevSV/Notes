package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.ui.NoteAdapter;

public class ListNotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note mCurrentNote;
    private boolean mIsLandscape;

    private DataBase mData;
    private NoteAdapter mAdapter;
    private RecyclerView mRecyclerView;


    public ListNotesFragment() {
    }

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE, mCurrentNote);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_lines);
        //initRecyclerView();
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        mData = MainActivity.dBase;
        initRecyclerView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyclerView(){

        // Эта установка служит для повышения производительности системы
        mRecyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        mAdapter = new NoteAdapter(mData, this);
        mRecyclerView.setAdapter(mAdapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        mRecyclerView.addItemDecoration(itemDecoration);

        // Установим слушателя
        mAdapter.SetOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCurrentNote = MainActivity.dBase.getNote(position);
                showNote(mCurrentNote);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            mCurrentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        }

        mIsLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (mIsLandscape) {
            showNote(mCurrentNote);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    private void editNote(Note note){
        // Создаём новый фрагмент с текущей позицией
        NoteEditFragment detail = NoteEditFragment.newInstance(note);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(mIsLandscape){// замена фрагмента
            fragmentTransaction.add(R.id.fragment_container_note, detail);
        }
        else{
            fragmentTransaction.replace(R.id.fragment_container, detail);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    private void showNote(Note note) {

        NoteFragment detail = NoteFragment.newInstance(note);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(mIsLandscape){// замена фрагмента
            fragmentTransaction.add(R.id.fragment_container_note, detail);
        }
        else{
            fragmentTransaction.replace(R.id.fragment_container, detail);
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
       int lastSelectedPosition = mAdapter.getLastSelectedPosition();
        if (lastSelectedPosition != -1) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    editNote(mData.getNote(lastSelectedPosition));
                    return true;
                case R.id.action_delete:
                    mData.deleteNote(mData.getNote(lastSelectedPosition));
                    mAdapter.notifyItemRemoved(mAdapter.getLastSelectedPosition());
                    return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        switch(id){
            case R.id.action_add:
                //Toast.makeText(getContext(), "Заглушка Добавить", Toast.LENGTH_SHORT).show();
                //Note newNote = new Note();
                Note newNote = mData.addNote();
                editNote(newNote);
                initRecyclerView();
                mAdapter.notifyItemInserted(newNote.getIndexNote());
                return true;
            case R.id.action_settings:
                Toast.makeText(getContext(), "Заглушка Настройки", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                Toast.makeText(getContext(), "Заглушка Избранное", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about_author:
                Toast.makeText(getContext(), "Заглушка О програме", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}