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

public class CourseFragment extends Fragment {
    private OnCourseCheckListener onCourseCheckListener;
    private View view;
    private RadioGroup courseRadioGroup;
    private boolean isCourseRadioGroupEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course, container, false);
        courseRadioGroup = view.findViewById(R.id.courseRadioGroup);
        courseRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (onCourseCheckListener != null) {
                onCourseCheckListener.onCourseCheck(group, checkedId);
            }
        });
        isCourseRadioGroupEnabled = true;
        return view;
    }

    public void clearCourseRadioGroupCheck() {
        courseRadioGroup.clearCheck();
    }

    public void setRadioGroupEnabled(boolean value) {
        if (isCourseRadioGroupEnabled == value) {
            return;
        }

        for (int i = 0, size = courseRadioGroup.getChildCount(); i < size; ++i) {
            courseRadioGroup.getChildAt(i).setEnabled(value);
        }

        isCourseRadioGroupEnabled = value;
    }

    public RadioButton getCheckedCourseRadioButton() {
        int id = courseRadioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            return null;
        }

        return view.findViewById(id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnCourseCheckListener) {
            onCourseCheckListener = (OnCourseCheckListener) context;
        }
    }

    interface OnCourseCheckListener {
        void onCourseCheck(RadioGroup radioGroup, int checkedRadioButtonId);
    }
}
