package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Parcelable {
    private static int lastIndex = -1;

    private int index;
    private String nameNote;
    private String descriptionNote;
    private long dateCreationNote;

    Note(){
        this.index = ++lastIndex;
        this.dateCreationNote = getDateNewNote();

    }

    Note(String nameNote){
        this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.dateCreationNote = getDateNewNote();
    }

    Note(String nameNote, String descriptionNote){
        this.index = ++lastIndex;
        this.nameNote = nameNote;
        this.descriptionNote = descriptionNote;
        this.dateCreationNote = getDateNewNote();

    }

    protected Note(Parcel in) {
        nameNote = in.readString();
        descriptionNote = in.readString();
        dateCreationNote = in.readLong();
    }

    private long getDateNewNote(){
         return System.currentTimeMillis();
    }

    public int getIndexNote(){
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
