package com.example.m4millionaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameStartActivity extends AppCompatActivity {

    public static Button confirmbutton, exitbutton;
    public static EditText PlayerName;
    public static TextView TotalEarnedLabel;
    Fragment fragment;
    public static int totalearned = 0;
    private static int fragmentscreen = 0;
    public static boolean loss = false;
    public static boolean win = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        PlayerName = findViewById(R.id.PlayerName);
        TotalEarnedLabel = findViewById(R.id.TotalEarnedlabel);
        confirmbutton = findViewById(R.id.confirm_button);
        exitbutton = findViewById(R.id.homeexitbutton);

        //fragmentscreen variable initially set to 0 to allow Player to enter name.
        if(fragmentscreen == 0) {
            confirmbutton.setText("Save Player Name");
        }
        //Confirm button onclick listener with different scenarios for screen/ UI transitions.
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(fragmentscreen == 0){
                        if(validateuser()) {

                            updateTotal();
                            PlayerName.setVisibility(View.GONE); //hide EditText after Player's name is accepted to make room for Fragment container
                            fragmentscreen = 1;
                            setFragmentscreen();
                            NextFragment(fragment);
                         }
                            //if win or loss booleans are false, next question
                    }else if(fragmentscreen > 0 && !loss && !win) {

                        fragmentscreen++;
                        setFragmentscreen();
                        NextFragment(fragment);
                        confirmbutton.setVisibility(View.GONE);

                    }else if(loss){
                        //display loss dialog whenever loss boolean is true. reset variables
                        showLoseAlert();
                        fragmentscreen = 0;
                        loss = false;
                        totalearned = 0;

                    }else if(win){
                        //display win dialog when boolean is true (only after correctly answering question 10). reset variables
                        showWinAlert();
                        fragmentscreen = 0;
                        win = false;
                        totalearned = 0;

                    }
            } //end OnClick
        });
    } //end OnCreate

//change which fragment screen will be used by NextFragment()
    public void setFragmentscreen() {
        if (fragmentscreen == 1)
            fragment = new question1fragment();
        if (fragmentscreen == 2)
            fragment = new question2fragment();
        if (fragmentscreen == 3)
            fragment = new question3fragment();
        if (fragmentscreen == 4)
            fragment = new question4fragment();
        if (fragmentscreen == 5)
            fragment = new question5fragment();
        if (fragmentscreen == 6)
            fragment = new question6fragment();
        if (fragmentscreen == 7)
            fragment = new question7fragment();
        if (fragmentscreen == 8)
            fragment = new question8fragment();
        if (fragmentscreen == 9)
            fragment = new question9fragment();
        if (fragmentscreen == 10)
            fragment = new question10fragment();
    }

    //PlayerName validation
    public boolean validateuser(){

        String namecheck = PlayerName.getText().toString().trim();

    if (namecheck.equals("")) {
        PlayerName.setError("Enter Name");
        Toast toast = Toast.makeText(GameStartActivity.this, "What is our future Millionaire's Name?", Toast.LENGTH_SHORT);
        toast.show();
        return false;
    }else{
            return true;
        }
}

//replace current fragment container with next fragment
    public void NextFragment(Fragment fragment) {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.placeholderlayout, fragment);
    transaction.commit();
    }

    //Dialog for when game is lost
    public void showLoseAlert() {

        //alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("YOU LOSE!");
        builder.setMessage(PlayerName.getText().toString() + ", you have lost the game! \n " +
                "You made it to Question " + fragmentscreen + " before losing. Try again!");
        // Ok button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
               Intent intent = new Intent(GameStartActivity.this, LoserActivity.class);
               intent.putExtra("player_name",PlayerName.getText().toString()); //pass player name to Loser Screen
               startActivity(intent);
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showWinAlert() {

        //alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("YOU WIN!");
        builder.setMessage(PlayerName.getText().toString() + ", you have one the game!! \n " +
                "You completed all 10 questions and... \n" +
                "YOU ARE NOW A MILLIONAIRE!!!!!");
        // Ok button
        builder.setPositiveButton("$$$", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Intent intent = new Intent(GameStartActivity.this, WinnerActivity.class);
                intent.putExtra("player_name",PlayerName.getText().toString()); //pass player name to Winner Screen
                startActivity(intent);
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void updateTotal(){
        //Add Player's Name to Total Earned label and update total
        String numstring = String.format("%,d",totalearned);
        String text = PlayerName.getText().toString().trim() + "'s Total is: $" + numstring;
        TotalEarnedLabel.setText(text);
    }
}