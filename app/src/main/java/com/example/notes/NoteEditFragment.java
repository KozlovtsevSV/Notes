package com.example.notes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditFragment extends Fragment {

    public static final String ARG_NOTE = "Note";
    private Note mNote;
    private Button mButtonSelectDate, mButtonBack, mButtonSave;
    private EditText mEditText, mEditName;
    private TextView mViewData;
    private LinearLayout mLinerLayoutDate;
    private long mDate;
    private int mYear, mMonth, mDay;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    public static NoteEditFragment newInstance(Note note) {
        NoteEditFragment fragment = new NoteEditFragment();
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
            mDate = mNote.getDateNoteLong();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        mEditText = view.findViewById(R.id.edit_note_text);
        mEditName = view.findViewById(R.id.edit_note_name);
        mViewData = view.findViewById(R.id.note_date_value);

        mButtonSelectDate = view.findViewById(R.id.buttonSelectDate);
        mButtonBack = view.findViewById(R.id.buttonBack);
        mButtonSave = view.findViewById(R.id.buttonSaveNote);
        mLinerLayoutDate = view.findViewById(R.id.note_date);

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
                if (mNote != null && view == mLinerLayoutDate) {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe"));
                    cal.setTimeInMillis(mNote.getDateNoteLong());
                    callDatePicker(view, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                }
                else if (mButtonBack != null && view == mButtonBack) {
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    manager.popBackStack();
                }
                else if (mButtonSave != null && view == mButtonSave) {
                    mNote.setNameNote(mEditName.getText().toString());
                    mNote.setDescriptionNote(mEditText.getText().toString());
                    mNote.setDateNewNote(mDate);
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    manager.popBackStack();
                    Fragment fragment = manager.findFragmentByTag(ListNotesFragment.FRAGMENT_NOTE);
                    // если нашли фрагмент с заметкой в менеджере необходимо его перерисовать, чтобы отобразить сохраненые изменения
                    if(fragment!= null){
                        manager.beginTransaction().detach(fragment).commitNowAllowingStateLoss();
                        manager.beginTransaction().attach(fragment).commitAllowingStateLoss();
                    }
                }
            }
        };

        mButtonBack.setOnClickListener(buttonClick);
        mButtonSave.setOnClickListener(buttonClick);
        mEditText.setOnLongClickListener(textViewLongClickListener);
        mLinerLayoutDate.setOnClickListener(buttonClick);

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
                        mDate = cal.getTimeInMillis();
                       // mNote.setDateNewNote(cal.getTimeInMillis());
                        //MainActivity.dBase.setDateNote(mNote);
                        formNote();
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() -1000);
        datePickerDialog.show();
    }

    private void formNote(){
        if(mNote!= null){
            mEditName.setText(mNote.getNameNote());
            mEditText.setText(mNote.getDescriptionNote());
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            mViewData.setText(formatter.format(new Date(mDate)));
        }
    }

}