package com.example.m4millionaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {
    Button homebutton;
    TextView nameheader;
    String playername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        homebutton = findViewById(R.id.winnerhome);
        nameheader = findViewById(R.id.winnername);
        Intent intent = getIntent();
        playername = intent.getStringExtra("player_name");
        nameheader.setText(playername);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WinnerActivity.this, GameHomeScreenActivity.class ));
                finish();
            }
        });
    }

}