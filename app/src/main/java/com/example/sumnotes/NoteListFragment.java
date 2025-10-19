package com.example.sumnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumnotes.R;

public class NoteListFragment extends Fragment {
    private NoteListViewModel vm;

    @Nullable @Override
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle b){
        return inf.inflate(R.layout.fragment_note_list, c, false);
    }

    @Override public void onViewCreated(@NonNull View v, Bundle b){
        super.onViewCreated(v, b);

        vm = new ViewModelProvider(this).get(NoteListViewModel.class);
        RecyclerView rv = v.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        NoteListAdapter adapter = new NoteListAdapter(note -> {
            Bundle args = new Bundle(); args.putLong("noteId", note.id);
            NavHostFragment.findNavController(this).navigate(R.id.noteEditorFragment, args);
        }, note -> {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_note_title)
                .setMessage(R.string.delete_note_message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> vm.delete(note.id))
                .setNegativeButton(android.R.string.no, null)
                .show();
    });

        rv.setAdapter(adapter);

        vm.notes.observe(getViewLifecycleOwner(), notes -> {
            adapter.submitList(notes);
        });

        v.findViewById(R.id.fabAdd).setOnClickListener(x -> {
            // new note: navigate with noteId = -1 (editor creates on first save)
            NavHostFragment.findNavController(this).navigate(R.id.noteEditorFragment);
        });
    }
}