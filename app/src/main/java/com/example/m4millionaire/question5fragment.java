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

public class question5fragment extends Fragment implements View.OnClickListener{

    Button Q5A,Q5B, Q5C, Q5D, q5confirmbutton;
    TextView QuestionNumlabel, Question5label;
    String[] question5 = {"Question 5", "What is the capital city of Spain?", "Madrid", "Barcelona", "Valencia", "Seville"};
    String CorrectAnswer = question5[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question5, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question5label = view.findViewById(R.id.question5question);
        Q5A = view.findViewById(R.id.Q5ButtonA);
        Q5A.setOnClickListener(this);
        Q5B = view.findViewById(R.id.Q5ButtonB);
        Q5B.setOnClickListener(this);
        Q5C = view.findViewById(R.id.Q5ButtonC);
        Q5C.setOnClickListener(this);
        Q5D = view.findViewById(R.id.Q5ButtonD);
        Q5D.setOnClickListener(this);
        q5confirmbutton = view.findViewById(R.id.q5FinalAnswer);
        q5confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question5[0]); //set Question label to first index of question5 array
        Question5label.setText(question5[1]); //set question5 to question  in question5 array
        List<String> tmpList = new ArrayList(Arrays.asList(question5)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q5A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q5B.setText("B: " + tmpList.get(1));
        Q5C.setText("C: " + tmpList.get(2));
        Q5D.setText("D: " + tmpList.get(3));
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
        Q5A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q5B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q5C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q5D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q5confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q5confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q5A.setClickable(false);
        Q5B.setClickable(false);
        Q5C.setClickable(false);
        Q5D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 25000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q5confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q5confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q5ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q5A);
                setFinalanswerbutton();

                break;
            case R.id.Q5ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q5B);
                setFinalanswerbutton();

                break;
            case R.id.Q5ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q5C);
                setFinalanswerbutton();

                break;
            case R.id.Q5ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q5D);
                setFinalanswerbutton();

                break;
            case R.id.q5FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

