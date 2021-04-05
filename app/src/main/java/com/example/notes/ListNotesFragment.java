package com.example.notes;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.ui.NoteAdapter;

public class ListNotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    public static final String FRAGMENT_NOTE = "FragmentNote";
    private static final String ARG_ITEM_INDEX = "CurrentNote_index";
    private Note mCurrentNote;
    private int mCurrentIndex;
    private boolean mIsLandscape;
    private DataBaseSource mDataBaseSource;

    //private DataBase mData;
    private NoteAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public DataBaseSource.DataBaseListener mListener = new DataBaseSource.DataBaseListener() {
        @Override
        public void onItemAdded(int index) {
            if (mAdapter != null) {
                mAdapter.notifyItemInserted(index);
            }
        }

        @Override
        public void onItemRemoved(int index) {
            if (mAdapter != null) {
                mAdapter.notifyItemRemoved(index);
            }
        }

        @Override
        public void onItemUpdated(int index) {
            if (mAdapter != null) {
                mAdapter.notifyItemChanged(index);
            }
        }

        @Override
        public void onDataSetChanged() {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    };


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentIndex = getArguments().getInt(ARG_ITEM_INDEX, -1);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        mDataBaseSource = DataBaseFirebaseImpl.getInstance();
        mAdapter = new NoteAdapter(mDataBaseSource,this);
        mDataBaseSource.addDataBaseListener(mListener);

        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDataBaseSource.removeDataBaseListener(mListener);
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        //mData = MainActivity.dBase;
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
        mAdapter = new NoteAdapter(mDataBaseSource, this);
        mRecyclerView.setAdapter(mAdapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        mRecyclerView.addItemDecoration(itemDecoration);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        // Установим слушателя
        mAdapter.SetOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mCurrentNote = mDataBaseSource.getItemAt(position);//.dBase.getNote(position);
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

    private void editNote(int index){
        // Создаём новый фрагмент с текущей позицией
        NoteEditFragment detail = NoteEditFragment.newInstance(index);
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


    public void showNote(Note note) {

        NoteFragment detail = NoteFragment.newInstance(note);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(mIsLandscape){// замена фрагмента
            fragmentTransaction.add(R.id.fragment_container_note, detail, FRAGMENT_NOTE);
        }
        else{
            fragmentTransaction.replace(R.id.fragment_container, detail, FRAGMENT_NOTE);
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
                    editNote(lastSelectedPosition);
                    return true;
                case R.id.action_delete:
                    mAdapter.notifyItemRemoved(mAdapter.getLastSelectedPosition());
                    mCurrentNote = null;
                    if (mIsLandscape) {
                        showNote(mCurrentNote);
                    }
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
                mDataBaseSource.add(new Note("Новая заметка"));
                int position = mDataBaseSource.getItemsCount() - 1;
                mAdapter.notifyItemInserted(position);
                editNote(position);
                ((RecyclerView) getView()).scrollToPosition(position);

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


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


}