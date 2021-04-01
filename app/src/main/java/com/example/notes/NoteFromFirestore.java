package com.example.notes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NoteFromFirestore extends Note {

    public static final String FIELD_INDEX = "index";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TEXT = "text";
    public static final String FIELD_DATE = "date";

    public NoteFromFirestore(String name, String text, long date) {
        super(name, text, date);
    }

    public NoteFromFirestore(String id, String name, String text, long date) {
        this(name, text, date);
        setIndexNote(id);
    }

    public NoteFromFirestore(String id, Map<String, Object> fields) {
        this(id, (String) fields.get(FIELD_NAME), (String) fields.get(FIELD_TEXT), (long) fields.get(FIELD_DATE));
    }

    public NoteFromFirestore(Note note) {
        this(note.getIndexNote(), note.getNameNote(), note.getDescriptionNote(), note.getDateNoteLong());
    }

    public final Map<String, Object> getFields() {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put(FIELD_INDEX, getIndexNote());
        fields.put(FIELD_NAME, getNameNote());
        fields.put(FIELD_TEXT, getDescriptionNote());
        fields.put(FIELD_DATE, getDateNoteLong());
        return Collections.unmodifiableMap(fields);
    }

}
