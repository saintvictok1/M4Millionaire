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

public class question8fragment extends Fragment implements View.OnClickListener{

    Button Q8A,Q8B, Q8C, Q8D, q8confirmbutton;
    TextView QuestionNumlabel, Question8label;
    String[] question8 = {"Question 8", "Who discovered penicillin?", "Alexander Fleming", "Thomas Edison", "Will Smith", "Albert Einstein"};
    String CorrectAnswer = question8[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question8, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question8label = view.findViewById(R.id.question8question);
        Q8A = view.findViewById(R.id.Q8ButtonA);
        Q8A.setOnClickListener(this);
        Q8B = view.findViewById(R.id.Q8ButtonB);
        Q8B.setOnClickListener(this);
        Q8C = view.findViewById(R.id.Q8ButtonC);
        Q8C.setOnClickListener(this);
        Q8D = view.findViewById(R.id.Q8ButtonD);
        Q8D.setOnClickListener(this);
        q8confirmbutton = view.findViewById(R.id.q8FinalAnswer);
        q8confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question8[0]); //set Question label to first index of question8 array
        Question8label.setText(question8[1]); //set question8 to question  in question8 array
        List<String> tmpList = new ArrayList(Arrays.asList(question8)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q8A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q8B.setText("B: " + tmpList.get(1));
        Q8C.setText("C: " + tmpList.get(2));
        Q8D.setText("D: " + tmpList.get(3));
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
        Q8A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q8B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q8C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q8D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q8confirmbutton.setVisibility(View.VISIBLE);
    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q8confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q8A.setClickable(false);
        Q8B.setClickable(false);
        Q8C.setClickable(false);
        Q8D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 250000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q8confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q8confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q8ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q8A);
                setFinalanswerbutton();

                break;
            case R.id.Q8ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q8B);
                setFinalanswerbutton();

                break;
            case R.id.Q8ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q8C);
                setFinalanswerbutton();

                break;
            case R.id.Q8ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q8D);
                setFinalanswerbutton();

                break;
            case R.id.q8FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

