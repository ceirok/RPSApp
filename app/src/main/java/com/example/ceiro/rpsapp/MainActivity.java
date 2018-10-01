package com.example.ceiro.rpsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText playerName;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerName = (EditText) findViewById(R.id.playerName);
        startButton = (Button) findViewById(R.id.startBtn);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), RPSActivity.class);
                if(playerName.getText().length() != 0){
                    startIntent.putExtra("player.name", playerName.getText().toString());
                    startActivity( startIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
