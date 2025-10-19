package com.example.sumnotes.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteEntity.class}, version = 5)
public abstract class NotesDb extends RoomDatabase {
    private static volatile NotesDb INSTANCE;

    public static NotesDb getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (NotesDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                                    NotesDb.class, "notes.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract NoteDao noteDao();
}