package com.example.sumnotes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    LiveData<List<NoteEntity>> observeAll();

    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<NoteEntity> observe(long id);

    @Query("SELECT * FROM notes WHERE id = :id")
    NoteEntity get(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long upsert(NoteEntity note);

    @Query("DELETE FROM notes WHERE id = :id")
    void delete(long id);
}