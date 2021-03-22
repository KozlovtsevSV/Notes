package com.example.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.TimeZone;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE = "Note";
    private Note mNote;
    private Button mButtonSelectDate, mButtonBack;
    private TextView mTextView;
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
        if (mNote != null){
            mButtonSelectDate.setVisibility(View.VISIBLE);
        }

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

        View.OnClickListener selectDateButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNote != null) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe"));
                    cal.setTimeInMillis(mNote.getDateNoteLong());
                    callDatePicker(view, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                }
            }
        };

        //view.OnClickListener blackButtonLisner = (view)->{};
        //mButtonBack

        mTextView.setOnLongClickListener(textViewLongClickListener);
        mButtonSelectDate.setOnClickListener(selectDateButtonClickListener);
        formNote();

        return view;
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

}