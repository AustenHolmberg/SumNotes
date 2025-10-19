package com.example.sumnotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sumnotes.data.NoteEntity;
import com.example.sumnotes.data.NoteRepository;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel {
    private final NoteRepository repo;
    public final LiveData<List<NoteEntity>> notes;

    public NoteListViewModel(@NonNull Application app) {
        super(app);
        repo = new NoteRepository(app);
        notes = repo.observeAll();
    }
    public void delete(long id) { repo.deleteAsync(id); }
    public long newEmptyNote() { // optionally create immediately
        NoteEntity n = new NoteEntity();
        n.title = ""; n.body = "";
        repo.upsertAsync(n); // return id if you want synchronous creation: use a callback/future
        return 0L; // or manage creation in editor
    }
}