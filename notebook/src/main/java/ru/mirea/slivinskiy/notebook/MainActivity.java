package ru.mirea.slivinskiy.notebook;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private EditText fileNameTV;
    private EditText fileTextTV;
    private String fileName;
    private String fileText;
    private SharedPreferences preferences;
    final String filePath = "filePath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileNameTV = (EditText) findViewById(R.id.editTextFileName);
        fileTextTV = (EditText) findViewById(R.id.EditFileText);
        preferences = getPreferences(MODE_PRIVATE);
        fileName = preferences.getString(filePath,"Empty");
        if(fileName != "Empty") {
            fileTextTV.setText(getTextFromFile());
            fileNameTV.setText(fileName);
        }
    }

    public void Save(View view){
        SharedPreferences.Editor editor = preferences.edit();

        FileOutputStream outputStream;
        if(fileNameTV.getText().toString() == "")
            fileName = "TextFile";
        else
            fileName = fileNameTV.getText().toString();

        editor.putString(filePath,fileName);
        editor.apply();
        fileText = fileTextTV.getText().toString();
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileText.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}