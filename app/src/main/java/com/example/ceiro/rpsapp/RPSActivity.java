package com.example.ceiro.rpsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RPSActivity extends AppCompatActivity {

    Button b_rock, b_paper, b_scissors;
    ImageView iv_cpu, iv_me;
    TextView name;

    String myChoise, cpuChoise, result, newName;
    Integer counter, playerScore, cpuScore;

    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);

        iv_cpu = (ImageView) findViewById(R.id.iv_cpu);
        iv_me = (ImageView) findViewById(R.id.iv_me);

        b_rock = (Button) findViewById(R.id.b_rock);
        b_paper = (Button) findViewById(R.id.b_paper);
        b_scissors = (Button) findViewById(R.id.b_scissors);

        name = (TextView) findViewById(R.id.name);
        if(getIntent().hasExtra("player.name")){
            newName = getIntent().getStringExtra("player.name");
            name.setText(newName);
        }

        r = new Random();
        counter = 10;
        playerScore = 0;
        cpuScore = 0;

        b_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myChoise = "rock";
                iv_me.setImageResource(R.drawable.rock);
                calculate();
                counter--;
                checkCounter();
            }
        });
        b_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myChoise = "paper";
                iv_me.setImageResource(R.drawable.paper);
                calculate();
                counter--;
                checkCounter();
            }
        });
        b_scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myChoise = "scissors";
                iv_me.setImageResource(R.drawable.scissors);
                calculate();
                counter--;
                checkCounter();
            }
        });
    }
    public void checkCounter(){
        if(counter == 0){
            String currentPlayer = newName;
            Intent endIntent = new Intent(getApplicationContext(), ScoreActivity.class);
            Integer winCounter = 0;
            if(playerScore > cpuScore){
                winCounter++;
            } else {
                winCounter = 0;
            }
            endIntent.putExtra("player.name", currentPlayer);
            endIntent.putExtra("player.score", winCounter);
            startActivity(endIntent);
        }
    }
    public void checkWinCount(){
        if(result.equals("You win!")){
            playerScore++;
        } else {
            cpuScore++;
        }
    }
    public void calculate(){
        int cpu = r.nextInt(3);

        if(cpu == 0){
            cpuChoise = "rock";
            iv_cpu.setImageResource(R.drawable.rock);
        } else if(cpu == 1){
            cpuChoise = "paper";
            iv_cpu.setImageResource(R.drawable.paper);
        } else if(cpu == 2){
            cpuChoise = "scissors";
            iv_cpu.setImageResource(R.drawable.scissors);
        }


        if(myChoise.equals("rock") && cpuChoise.equals("paper")){
            result = "You lose!";
            cpuScore++;
        } else
        if(myChoise.equals("rock") && cpuChoise.equals("scissors")){
            result = "You win!";
            playerScore++;
        } else
        if(myChoise.equals("paper") && cpuChoise.equals("rock")){
            result = "You win!";
            playerScore++;
        } else
        if(myChoise.equals("paper") && cpuChoise.equals("scissors")){
            result = "You lose!";
            cpuScore++;
        } else
        if(myChoise.equals("scissors") && cpuChoise.equals("rock")){
            result = "You lose!";
            cpuScore++;
        } else
        if(myChoise.equals("scissors") && cpuChoise.equals("paper")){
            result = "You win!";
            playerScore++;
        } else
        if(myChoise.equals(cpuChoise)){
            result = "Draw!";
        }

        Toast.makeText(RPSActivity.this, result, Toast.LENGTH_SHORT).show();
    }
}
