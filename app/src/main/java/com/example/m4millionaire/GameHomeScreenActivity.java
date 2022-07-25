package com.example.m4millionaire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameHomeScreenActivity extends AppCompatActivity {

    Button playbutton, exitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_homescreen);

        playbutton = findViewById(R.id.playbutton);
        exitbutton = findViewById(R.id.homeexitbutton);

        //OnClickListener for playbutton
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playgame = new Intent(view.getContext(), GameStartActivity.class);
                startActivity(playgame);
            }
        });
        //OnClickListener for exitbutton
        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
                System.exit(0);
            }
        });
    }
}