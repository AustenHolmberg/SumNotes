package com.example.sumnotes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sumnotes.data.NoteEntity;

import java.math.BigInteger;
import java.util.regex.Matcher;

import com.example.sumnotes.Utils;

public class NoteEditorFragment extends Fragment {
    private NoteEditorViewModel vm;
    private EditText etTitle, etBody;
    private TextView calorieView;
    private long noteId;

    @Nullable @Override
    public View onCreateView(LayoutInflater inf, ViewGroup c, Bundle b){
        View view = inf.inflate(R.layout.fragment_note_editor, c, false);

        etBody = view.findViewById(R.id.etBody);
        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                BigInteger sum = new BigInteger("0");
                Matcher m = Utils.sumPattern.matcher(s.toString());

                while (m.find()) {
                    BigInteger bigNum = new BigInteger(m.group());
                    sum = sum.add(bigNum);
                }

                calorieView = view.findViewById(R.id.etSum);
                calorieView.setText(sum.toString());
            }
        });

        return view;
    }

    @Override public void onViewCreated(@NonNull View v, Bundle b){
        noteId = getArguments().getLong("noteId", -1);
        vm = new ViewModelProvider(this).get(NoteEditorViewModel.class);
        etTitle = v.findViewById(R.id.etTitle);
        etBody  = v.findViewById(R.id.etBody);

        if (noteId > 0) {
            vm.setNoteId(noteId);
            vm.note.observe(getViewLifecycleOwner(), n -> {
                if (n != null) {
                    etTitle.setText(n.title);
                    etBody.setText(n.body);
                }
            });
        }
    }

    @Override public void onPause() {
        super.onPause();
        vm.save(etTitle.getText().toString(), etBody.getText().toString());
    }
}