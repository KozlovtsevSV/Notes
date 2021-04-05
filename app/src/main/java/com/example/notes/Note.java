package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Parcelable {
    private static int lastIndex = -1;

    @Nullable
    private String index;

    //private int index;
    @NonNull
    private String nameNote;
    private String textNote;
    private long dateCreationNote;

    Note(){
        //this.index = Integer.toString(++lastIndex);
        this.nameNote = "Новая заметка";
        this.textNote = "";
        this.dateCreationNote = getDateNewNote();

    }

    Note(String nameNote){
       // this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.textNote = "";
        this.dateCreationNote = getDateNewNote();
    }

    public Note(String nameNote, String textNote){
        //this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.textNote = textNote;
        this.dateCreationNote = getDateNewNote();
    }

    public Note(String nameNote, String descriptionNote, long date){
        //this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.textNote = descriptionNote;
        this.dateCreationNote = date;
    }

    protected Note(Parcel in) {
        nameNote = in.readString();
        textNote = in.readString();
        dateCreationNote = in.readLong();
    }

    private long getDateNewNote(){
         return System.currentTimeMillis();
    }

    public String getIndexNote(){
        return this.index;
    }

    public String getNameNote(){
        return this.nameNote;
    }

    public String getTextNote(){
        return this.textNote;
    }

    public String getDateNote(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(new Date(this.dateCreationNote));
    }

    public void setIndexNote(String index){
        this.index = index;
    }

    public void setNameNote(String textName){
        nameNote = textName;
    }

    public void setTextNote(String textDescription){
        textNote = textDescription;
    }

    public void setDateNewNote(long date){
        this.dateCreationNote = date;
    }

    public long getDateNoteLong(){
        return this.dateCreationNote;

    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getNameNote());
        parcel.writeString(getTextNote());
    }
}
