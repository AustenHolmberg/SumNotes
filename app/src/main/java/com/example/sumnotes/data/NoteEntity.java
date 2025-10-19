package com.example.sumnotes.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes", indices = @Index(value="updatedAt"))
public class NoteEntity {
    @PrimaryKey(autoGenerate = true) public long id;
    @NonNull
    public String title = "";
    @NonNull public String body = "";
    public Integer calories = 0;
    public long updatedAt;
    public long createdAt;
}