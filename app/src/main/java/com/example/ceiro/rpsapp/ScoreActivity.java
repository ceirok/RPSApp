package com.example.ceiro.rpsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    ListView list;
    Button playAgain;
    String playerName;
    Integer playerScore;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    TextView currentText;

    String line = null;

    private static final int REQUEST_CODE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        list = (ListView) findViewById(R.id.list);
        playAgain = (Button) findViewById(R.id.playAgain);
        playerName = getIntent().getStringExtra("player.name");
        playerScore = getIntent().getIntExtra("player.score", 0);
        currentText = (TextView) findViewById(R.id.currentText);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        currentText.setText(playerName + "                                              " + playerScore);

        adapter.notifyDataSetChanged();

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ScoreActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScoreActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_CODE_PERMISSION);
        }
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalDirectory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)));
            ShowDirectoryFilesInList(externalDirectory);
        }

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playMore = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(playMore);
            }
        });
    }

    private boolean isExternalStorageWriteable() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State", "Writeable");
            return true;
        } else return false;
    }

    public void onSave(View view) {
        if(isExternalStorageWriteable()) {
            File textFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                    playerName + "                                                     " + playerScore + "");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write((playerName + "                                                     " + playerScore).getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Score saved", Toast.LENGTH_SHORT).show();
                currentText.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else Toast.makeText(this, "External storage is not mounted", Toast.LENGTH_SHORT).show();
    }

    public void onShow(View view) {
        if(isExternalStorageWriteable()) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                File textFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                        playerName + "                                                     " + playerScore + "");
                FileInputStream fileInputStream = new FileInputStream(textFile);
                if(fileInputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    while((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    fileInputStream.close();
                    currentText.setText(stringBuilder);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else Toast.makeText(this, "Error reading from external storage", Toast.LENGTH_SHORT).show();
    }

    public void ShowDirectoryFilesInList(File externalDirectory) {
        File listFile[] = externalDirectory.listFiles();
        if(listFile != null) {
            for(int i = 0; i < listFile.length; i++) {
                if(listFile[i].isDirectory()) {
                    ShowDirectoryFilesInList(listFile[i]);
                } else listItems.add(listFile[i].getName());
            }
        }
    }
}
