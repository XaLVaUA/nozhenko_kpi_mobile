package com.example.task3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment {
    private View view;
    private TextView resultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        resultTextView = view.findViewById(R.id.resultTextView);
        return view;
    }

    public void setResultText(String text) {
        resultTextView.setText(text);
    }

    public String getResultText() {
        return resultTextView.getText().toString();
    }
}
