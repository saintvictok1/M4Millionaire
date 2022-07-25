package com.example.m4millionaire;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class question1fragment extends Fragment implements View.OnClickListener{

    Button Q1A,Q1B, Q1C, Q1D, q1confirmbutton;
    TextView QuestionNumlabel, Question1label;
    String[] question1 = {"Question 1", "What does “www” stand for in a website browser?", "World Wide Web", "Wild Wild West", "Wacky World Web", "Wacky Web Wiki"};
    String CorrectAnswer = question1[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question1, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question1label = view.findViewById(R.id.question1question);
        Q1A = view.findViewById(R.id.Q1ButtonA);
        Q1A.setOnClickListener(this);
        Q1B = view.findViewById(R.id.Q1ButtonB);
        Q1B.setOnClickListener(this);
        Q1C = view.findViewById(R.id.Q1ButtonC);
        Q1C.setOnClickListener(this);
        Q1D = view.findViewById(R.id.Q1ButtonD);
        Q1D.setOnClickListener(this);
        q1confirmbutton = view.findViewById(R.id.q1FinalAnswer);
        q1confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question1[0]); //set Question label to first index of question1 array
        Question1label.setText(question1[1]); //set question1 to question  in question1 array
        List<String> tmpList = new ArrayList(Arrays.asList(question1)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q1A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q1B.setText("B: " + tmpList.get(1));
        Q1C.setText("C: " + tmpList.get(2));
        Q1D.setText("D: " + tmpList.get(3));
        GameStartActivity.confirmbutton.setVisibility(View.GONE);
        return view;
    } //End OnCreateView


    public  void displayConfirmAlert(TextView view){
        String selection = view.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Final Answer? ");
        builder.setMessage("You have selected " + selection);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SelectedAnswer = selection.substring(3); //Strip preceding Letter from Answer
                checkanswer(SelectedAnswer);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
       clearselection();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void defaultbuttoncolors(){
        Q1A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q1B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q1C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q1D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q1confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q1confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q1A.setClickable(false);
        Q1B.setClickable(false);
        Q1C.setClickable(false);
        Q1D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 500;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q1confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q1confirmbutton.setVisibility(View.GONE);
            disablebuttons();
            GameStartActivity.loss = true;
            GameStartActivity.confirmbutton.setText("Continue");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

            //switch to loss screen
        }
    }
    public void displayCorrectDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Correct!");
    builder.setMessage(SelectedAnswer + " is the right answer");
    builder.setPositiveButton("OK", null);
    builder.show();
}
    public void displayIncorrectDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Incorrect!");
        builder.setMessage("You selected " + SelectedAnswer +"\n " +
                "but " + CorrectAnswer + " is the right answer.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Q1ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q1A);
                setFinalanswerbutton();

                break;
            case R.id.Q1ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q1B);
                setFinalanswerbutton();

                break;
            case R.id.Q1ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q1C);
                setFinalanswerbutton();

                break;
            case R.id.Q1ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q1D);
                setFinalanswerbutton();

                break;
            case R.id.q1FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

