package com.example.task3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageActivity extends AppCompatActivity {
    public static final String OPEN_RESULT_STRING_EXTRA = "open result string extra";

    private RecyclerView filesRecyclerView;
    private ArrayList<String> files;
    private RecyclerView.Adapter adapter;
    private EditText noEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        noEditText = findViewById(R.id.noEditText);
        filesRecyclerView = findViewById(R.id.filesRecyclerView);
        filesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        files = new ArrayList<>(Arrays.asList(getApplicationContext().fileList()));
        adapter = new MyAdapter(files);
        filesRecyclerView.setAdapter(adapter);
    }

    public void onCancelButtonClick(View view) {
        finish();
    }

    public void onSelectButtonClick(View view) {
        String noString = noEditText.getText().toString();
        int no = Integer.parseInt(noString);

        if (no < 1 || no > files.size()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.error_input);
            builder.setNeutralButton(android.R.string.ok, null);
            builder.create().show();
            return;
        }

        try (InputStreamReader isr = new InputStreamReader(openFileInput(files.get(no - 1)))) {
            BufferedReader bufferedReader = new BufferedReader(isr);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append("\n").append(receiveString);
            }

            Intent intent = new Intent();
            intent.putExtra(OPEN_RESULT_STRING_EXTRA, stringBuilder.toString());
            setResult(RESULT_OK, intent);
            finish();

        } catch (IOException ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.io_error);
            builder.setNeutralButton(android.R.string.ok, null);
            builder.create().show();
            return;
        }
    }

    public void onDeleteButtonClick(View view) {
        String noString = noEditText.getText().toString();
        int no = Integer.parseInt(noString);

        if (no < 1 || no > files.size()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.error_input);
            builder.setNeutralButton(android.R.string.ok, null);
            builder.create().show();
            return;
        }

        --no;
        deleteFile(files.get(no));
        files.remove(no);
        adapter.notifyDataSetChanged();
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> files;

    public MyAdapter(List<String> files) {
        this.files = files;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText((position + 1) + ": " +files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
