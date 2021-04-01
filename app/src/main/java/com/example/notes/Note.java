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
    private String descriptionNote;
    private long dateCreationNote;

    Note(){
        //this.index = Integer.toString(++lastIndex);
        this.nameNote = "Новая заметка";
        this.descriptionNote = "";
        this.dateCreationNote = getDateNewNote();

    }

    Note(String nameNote){
       // this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.dateCreationNote = getDateNewNote();
    }

    public Note(String nameNote, String descriptionNote){
        //this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.descriptionNote = descriptionNote;
        this.dateCreationNote = getDateNewNote();
    }

    public Note(String nameNote, String descriptionNote, long date){
        //this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.descriptionNote = descriptionNote;
        this.dateCreationNote = date;
    }

    protected Note(Parcel in) {
        nameNote = in.readString();
        descriptionNote = in.readString();
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

    public String getDescriptionNote(){
        return this.descriptionNote;
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

    public void setDescriptionNote(String textDescription){
        descriptionNote = textDescription;
    }

    public void setDateNewNote(long date){
        this.dateCreationNote = date;
    }

    public long getDateNoteLong(){
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
//        return formatter.format(new Date(this.dateCreationNote));
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
        parcel.writeString(getDescriptionNote());
    }
}
