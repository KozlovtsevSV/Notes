package com.example.notes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.TimeZone;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE = "Note";
    private Note mNote;
    private Button mButtonSelectDate, mButtonBack;
    private TextView mTextView;
    private TableLayout mNoteToolBar;
    //private int mYear, mMonth, mDay;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);
        mTextView = view.findViewById(R.id.text_note);
        mButtonSelectDate = view.findViewById(R.id.buttonSelectDate);
        mButtonBack = view.findViewById(R.id.buttonBack);
        mNoteToolBar = view.findViewById(R.id.noteToolBar);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mButtonBack.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams layoutParamsNoteToolBar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            mNoteToolBar.setLayoutParams(layoutParamsNoteToolBar);
        }
        else{
            mButtonBack.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParamsNoteToolBar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mNoteToolBar.setLayoutParams(layoutParamsNoteToolBar);

        }

//        if (mNote != null){
//            mButtonSelectDate.setVisibility(View.VISIBLE);
//        }

        View.OnLongClickListener textViewLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (view == mButtonSelectDate){
                    if (mNote != null) {
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe"));
                        cal.setTimeInMillis(mNote.getDateNoteLong());
                        callDatePicker(view, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        return true;
                    }
                }
                return false;
            }
        };

        View.OnClickListener buttonClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNote != null && view == mButtonSelectDate) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe"));
                    cal.setTimeInMillis(mNote.getDateNoteLong());
                    callDatePicker(view, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                }
                else if (mButtonBack != null && view == mButtonBack) {
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    manager.popBackStack();
                }
           }
        };

        mButtonBack.setOnClickListener(buttonClick);

        mTextView.setOnLongClickListener(textViewLongClickListener);
        mButtonSelectDate.setOnClickListener(buttonClick);

        formNote();

        initPopupMenu(mTextView);

        return view;
    }

    private void back_(){


    }

    private void callDatePicker(View view, int year, int month, int day) {

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe"));
                        cal.set(year, monthOfYear, dayOfMonth);
                        mNote.setDateNewNote(cal.getTimeInMillis());
                        MainActivity.dBase.setDateNote(mNote);
                        formNote();
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() -1000);
        datePickerDialog.show();
    }

    private void formNote(){
        if(mNote!= null){
            mTextView.setText(mNote.getDateNote() + " | "+ mNote.getNameNote() + "\n" + mNote.getDescriptionNote());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initPopupMenu(View view) {

        TextView viewTextNote = view.findViewById(R.id.text_note);
        viewTextNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Activity activity = requireActivity();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                Menu menu = popupMenu.getMenu();
                //menu.findItem(R.id.popup_edit_note).setVisible(false);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.popup_edit_note:
                                Toast.makeText(getContext(), "Заглушка Редактирование", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.popup_copy_note:
                                Toast.makeText(getContext(), "Заглушка Копирование", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.popup_send_note:
                                Toast.makeText(getContext(), "Заглушка Поделиться", Toast.LENGTH_SHORT).show();
                                return true;
//                            case 123456:
//                                Toast.makeText(getContext(), "Chosen new item added", Toast.LENGTH_SHORT).show();
//                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

}