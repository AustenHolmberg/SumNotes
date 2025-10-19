package com.example.sumnotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sumnotes.data.NoteEntity;
import com.example.sumnotes.data.NoteRepository;

import java.util.regex.*;

public class NoteEditorViewModel extends AndroidViewModel {
    private final NoteRepository repo;
    private long noteId = -1;
    public LiveData<NoteEntity> note;

    public NoteEditorViewModel(@NonNull Application app) {
        super(app);
        repo = new NoteRepository(app);
    }
    public void setNoteId(long id) {
        this.noteId = id;
        note = repo.observe(id);
    }
    public void save(String title, String body) {
        NoteEntity n;
        if (noteId == -1) {
            n = new NoteEntity();
            n.id = 0;
            n.createdAt = System.currentTimeMillis();
        } else {
            n = note.getValue();
        }
        n.title = title;
        n.body = body;

        int sum = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(body);

        while (m.find()) {
            int num = Integer.parseInt(m.group());
            sum += num;
        }
        n.calories = sum;

        repo.upsertAsync(n);
    }
    public void delete() {
        if (noteId > 0) repo.deleteAsync(noteId);
    }
}