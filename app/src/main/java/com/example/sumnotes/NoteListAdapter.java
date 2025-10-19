package com.example.sumnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sumnotes.data.NoteEntity;

public class NoteListAdapter extends ListAdapter<NoteEntity, NoteListAdapter.VH> {
    public interface OnClick { void onClick(NoteEntity note); }
    public interface OnLongClick { void onLongClick(NoteEntity note); }
    private final OnClick onClick;
    private final OnLongClick onLongClick;

    public NoteListAdapter(OnClick onClick, OnLongClick onLongClick) {
        super(new DiffUtil.ItemCallback<NoteEntity>() {
            public boolean areItemsTheSame(NoteEntity a, NoteEntity b){ return a.id == b.id; }
            public boolean areContentsTheSame(NoteEntity a, NoteEntity b){
                return a.title.equals(b.title) && a.body.equals(b.body) && a.updatedAt==b.updatedAt;
            }
        });
        this.onClick = onClick;
        this.onLongClick = onLongClick;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView note_title, note_calories, note_date;
        VH(View v){
            super(v);
            note_title = v.findViewById(R.id.note_title);
            note_calories = v.findViewById(R.id.note_calories);
            note_date = v.findViewById(R.id.note_date);
        }
    }

    @NonNull
    public VH onCreateViewHolder(@NonNull ViewGroup p, int vt){
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_note, p, false);
        return new VH(v);
    }
    public void onBindViewHolder(@NonNull VH h, int pos){
        NoteEntity n = getItem(pos);
        h.note_title.setText(n.title.isEmpty() ? "(Untitled)" : n.title);
        h.note_calories.setText(n.calories.toString());
        h.note_date.setText(DateConverter.formatTimestamp(n.createdAt));
        h.itemView.setOnClickListener(v -> onClick.onClick(n));
        h.itemView.setOnLongClickListener(v -> {
            onLongClick.onLongClick(n);
            return true;
        });
    }
}