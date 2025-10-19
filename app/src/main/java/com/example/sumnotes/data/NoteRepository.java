package com.example.sumnotes.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class NoteRepository {
    private final NoteDao dao;
    private final java.util.concurrent.Executor io;

    public NoteRepository(Context ctx) {
        NotesDb db = NotesDb.getInstance(ctx);
        dao = db.noteDao();
        io = java.util.concurrent.Executors.newSingleThreadExecutor();
    }

    public LiveData<List<NoteEntity>> observeAll() {
        return dao.observeAll();
    }
    public LiveData<NoteEntity> observe(long id) {
        return dao.observe(id);
    }

    public void upsertAsync(NoteEntity n) { io.execute(() -> {
        long now = System.currentTimeMillis();
        n.updatedAt = now;
        dao.upsert(n);
    });}

    public void deleteAsync(long id) { io.execute(() -> dao.delete(id)); }
}