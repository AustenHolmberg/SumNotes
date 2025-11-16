package com.example.sumnotes;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.sumnotes.data.NoteEntity;
import com.example.sumnotes.data.NoteRepository;
import com.example.sumnotes.Utils;

import java.util.regex.*;

public class NoteEditorViewModel extends AndroidViewModel {
    private final NoteRepository repo;
    public LiveData<NoteEntity> note;
    private final MutableLiveData<Long> noteId = new MutableLiveData<>();
    private NoteEntity noteEntity = null;
    public void setNoteId(long id) {
        noteId.postValue(id);
        note = repo.observe(id);
    }

    public Long getNoteId() {
        return noteId.getValue();
    }

    public NoteEditorViewModel(@NonNull Application app) {
        super(app);
        repo = new NoteRepository(app);
    }

    public void save(String title, String body) {
        NoteEntity current;
        if (note != null && note.getValue() != null) {
            current = note.getValue();
        } else {
            current = new NoteEntity();
            Long noteId = getNoteId();
            if (noteId == null) {
                current.id = 0;
                current.createdAt = System.currentTimeMillis();
            } else {
                current.id = noteId;
                current.createdAt = noteEntity.createdAt;
            }
        }
        current.title = title;
        current.body = body;

        int sum = 0;
        Matcher m = Utils.sumPattern.matcher(body);

        while (m.find()) {
            int num = Integer.parseInt(m.group());
            sum += num;
        }
        current.sum = Integer.toString(sum);

        repo.upsertAsync(current, note -> {
            if (getNoteId() == null)  {
                setNoteId(note.id);
            }
            noteEntity = note;
        });
    }
    public void delete() {
        Long noteId = getNoteId();
        if (noteId != null) repo.deleteAsync(noteId);
    }
}