package com.example.task3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FacultyFragment extends Fragment {
    private OnFacultyCheckListener onFacultyCheckListener;
    private View view;
    private RadioGroup facultyRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_faculty, container, false);
        facultyRadioGroup = view.findViewById(R.id.facultyRadioGroup);
        facultyRadioGroup.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            if (onFacultyCheckListener != null) {
                onFacultyCheckListener.onFacultyCheck(group, checkedId);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnFacultyCheckListener) {
            onFacultyCheckListener = (OnFacultyCheckListener) context;
        }
    }

    public void clearFacultyRadioGroupCheck() {
        facultyRadioGroup.clearCheck();
    }

    public RadioButton getCheckedFacultyRadioButton() {
        int id = facultyRadioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            return null;
        }

        return view.findViewById(id);
    }

    interface OnFacultyCheckListener {
        void onFacultyCheck(RadioGroup radioGroup, int checkedRadioButtonId);
    }
}
