package com.example.task3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CourseFragment extends Fragment {
    private View view;
    private RadioGroup courseRadioGroup;
    private boolean isCourseRadioGroupEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course, container, false);
        courseRadioGroup = view.findViewById(R.id.courseRadioGroup);
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
}
