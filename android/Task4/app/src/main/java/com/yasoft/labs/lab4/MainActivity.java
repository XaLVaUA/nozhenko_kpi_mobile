package com.yasoft.labs.lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // https://file-examples.com/wp-content/uploads/2017/04/file_example_MP4_480_1_5MG.mp4

    private final int REQUEST_CODE_CHOOSE_MEDIA = 100;

    private TextView currentTextView;
    private VideoView videoView;
    private EditText uriEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        uriEditText = findViewById(R.id.uriEditText);
        currentTextView = findViewById(R.id.currentTextView);
    }

    public void OnPlayButtonClick(View view) {
        videoView.start();
    }

    public void OnPauseButtonClick(View view) {
        videoView.pause();
    }

    public void OnStopButtonClick(View view) {
        videoView.stopPlayback();
        videoView.resume();
    }

    public void OnOpenButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{ "video/*", "audio/*"});
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_MEDIA);
    }

    public void OnLoadButtonClick(View view) {
        Uri uri = Uri.parse(uriEditText.getText().toString());

        if (uri == null) {
            currentTextView.setText(R.string.error);
            return;
        }

        videoView.setVideoURI(uri);
        currentTextView.setText(uri.getPath());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;

        if (requestCode == REQUEST_CODE_CHOOSE_MEDIA) {
            Uri uri = data.getData();

            if (uri == null) {
                currentTextView.setText(R.string.error);
                return;
            }

            videoView.setVideoURI(uri);
            currentTextView.setText(uri.getPath());
        }
    }
}
