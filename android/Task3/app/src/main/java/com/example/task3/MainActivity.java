package com.example.task3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements FacultyFragment.OnFacultyCheckListener {
    private static final int OPEN_RESULT_REQUEST = 15;

    private FacultyFragment facultyFragment;
    private CourseFragment courseFragment;
    private ResultFragment resultFragment;
    private EditText fileNameEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facultyFragment = (FacultyFragment) getSupportFragmentManager().findFragmentById(R.id.facultyFragment);
        courseFragment = (CourseFragment) getSupportFragmentManager().findFragmentById(R.id.courseFragment);
        resultFragment = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.resultFragment);

        fileNameEditText = findViewById(R.id.fileNameEditText);
        fileNameEditText.setEnabled(false);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setEnabled(false);

        courseFragment.setRadioGroupEnabled(false);
    }

    public void onOkButtonClick(View view) {
        String facultyString = getStringFromRadioButton(facultyFragment.getCheckedFacultyRadioButton(), getString(R.string.faculty));
        String courseString = getStringFromRadioButton(courseFragment.getCheckedCourseRadioButton(), getString(R.string.course));

        resultFragment.setResultText(facultyString + "\n" + courseString);

        fileNameEditText.setEnabled(true);
        saveButton.setEnabled(true);
    }

    public String getStringFromRadioButton(RadioButton radioButton, String prefix) {
        if (radioButton != null) {
            return prefix + " " + radioButton.getText();
        }

        return prefix + " " + getString(R.string.not_checked);
    }

    public void onSaveButtonClick(View view) {
        String fileName = fileNameEditText.getText().toString();
        if (fileName.isEmpty()) {
            return;
        }

        try (OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE))) {
            osw.write(resultFragment.getResultText());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.saved) + " " + fileName);
            builder.setNeutralButton(android.R.string.ok, null);
            builder.create().show();
        } catch (IOException ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.io_error);
            builder.setNeutralButton(android.R.string.ok, null);
            builder.create().show();
        }
    }

    public void onOpenButtonClick(View view) {
        reset();
        Intent intent = new Intent(this, StorageActivity.class);
        startActivityForResult(intent, OPEN_RESULT_REQUEST);
    }

    public void onCancelButtonClick(View view) {
        reset();
    }

    private void reset() {
        facultyFragment.clearFacultyRadioGroupCheck();
        courseFragment.clearCourseRadioGroupCheck();
        fileNameEditText.setEnabled(false);
        saveButton.setEnabled(false);
        courseFragment.setRadioGroupEnabled(false);
        resultFragment.setResultText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_RESULT_REQUEST) {
            if (resultCode != RESULT_OK) {
                return;
            }

            if (data == null) {
                return;
            }

            String resultString = data.getStringExtra(StorageActivity.OPEN_RESULT_STRING_EXTRA);
            resultFragment.setResultText(resultString);
        }
    }

    @Override
    public void onFacultyCheck(RadioGroup radioGroup, int checkedRadioButtonId) {
        if (checkedRadioButtonId == -1) {
            return;
        }

        courseFragment.setRadioGroupEnabled(true);
    }
}
