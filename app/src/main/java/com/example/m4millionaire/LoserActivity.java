package com.example.m4millionaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoserActivity extends AppCompatActivity {
    Button homebutton;
    TextView nameheader;
    String playername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loser);
        homebutton = findViewById(R.id.loserhome);
        nameheader = findViewById(R.id.losername);
        Intent intent = getIntent();
        playername = intent.getStringExtra("player_name");
        nameheader.setText(playername);


        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoserActivity.this, GameHomeScreenActivity.class ));
                finish();
            }
        });
    }
}