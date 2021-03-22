package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ListNotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    public ListNotesFragment() {
        // Required empty public constructor
    }

    public static ListNotesFragment newInstance(String param1, String param2) {
        ListNotesFragment fragment = new ListNotesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;

        int sizeTextListNotes = getResources().getInteger(R.integer.sizeTextListNotes);
        int colorTextListNotes = getResources().getColor(R.color.colorTextListNotes);

        ArrayList<Note> listNotes = MainActivity.dBase.getListNote();
        if(listNotes== null){return;}

        for(int i = 0; i < listNotes.size(); i++){
            Note note = listNotes.get(i);
            TextView tv = new TextView(getContext());
            tv.setText(note.getNameNote());
            tv.setTextSize(sizeTextListNotes);
            tv.setTextColor(colorTextListNotes);
            tv.setLines(1);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            layoutView.addView(tv);
            final int index = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = MainActivity.dBase.getNote(index);
                    if (isLandscape) {
                        showLandNote(currentNote);
                    } else {
                        showPortNote(currentNote);
                    }
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE, currentNote);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        }
        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void showPortNote(Note note) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), NoteActivity.class);
//        intent.putExtra(NoteFragment.ARG_NOTE, note);
//        startActivity(intent);

        NoteFragment detail = NoteFragment.newInstance(note);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        fragmentManager.popBackStack();

    }

    private void showLandNote(Note note) {
        // Создаём новый фрагмент с текущей позицией
        NoteFragment detail = NoteFragment.newInstance(note);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_note, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}