package com.example.ceiro.rpsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    ListView list;
    Button playAgain;
    String playerName;
    Integer playerScore;

    SharedPreferences.Editor editor;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> tempArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        list = (ListView) findViewById(R.id.list);
        playAgain = (Button) findViewById(R.id.playAgain);
        playerName = getIntent().getStringExtra("player.name");
        playerScore = getIntent().getIntExtra("player.score", 0);

        SharedPreferences playerData = getSharedPreferences("playerInfo", Context.MODE_PRIVATE);
        editor = playerData.edit();
        editor.putString("playerInfo", playerName + "                                                     " + playerScore + "");

        Map<String, ?> allEntries = playerData.getAll();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        for(Map.Entry<String, ?> entry : allEntries.entrySet()){
            listItems.add(entry.getValue().toString());
        }

        adapter.notifyDataSetChanged();
        editor.apply();
        editor.commit();

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playMore = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(playMore);
            }
        });
    }
}
