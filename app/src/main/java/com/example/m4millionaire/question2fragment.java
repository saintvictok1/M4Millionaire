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

public class question2fragment extends Fragment implements View.OnClickListener{

    Button Q2A,Q2B, Q2C, Q2D, q2confirmbutton;
    TextView QuestionNumlabel, Question2label;
    String[] question2 = {"Question 2", "Which of the follow is not a primary color?", "Purple", "Green", "Blue", "Red"};
    String CorrectAnswer = question2[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question2, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question2label = view.findViewById(R.id.question2question);
        Q2A = view.findViewById(R.id.Q2ButtonA);
        Q2A.setOnClickListener(this);
        Q2B = view.findViewById(R.id.Q2ButtonB);
        Q2B.setOnClickListener(this);
        Q2C = view.findViewById(R.id.Q2ButtonC);
        Q2C.setOnClickListener(this);
        Q2D = view.findViewById(R.id.Q2ButtonD);
        Q2D.setOnClickListener(this);
        q2confirmbutton = view.findViewById(R.id.q2FinalAnswer);
        q2confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question2[0]); //set Question label to first index of question2 array
        Question2label.setText(question2[1]); //set question2 to question  in question2 array
        List<String> tmpList = new ArrayList(Arrays.asList(question2)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q2A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q2B.setText("B: " + tmpList.get(1));
        Q2C.setText("C: " + tmpList.get(2));
        Q2D.setText("D: " + tmpList.get(3));
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
        Q2A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q2B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q2C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q2D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q2confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q2confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q2A.setClickable(false);
        Q2B.setClickable(false);
        Q2C.setClickable(false);
        Q2D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 1000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q2confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q2confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q2ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q2A);
                setFinalanswerbutton();

                break;
            case R.id.Q2ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q2B);
                setFinalanswerbutton();

                break;
            case R.id.Q2ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q2C);
                setFinalanswerbutton();

                break;
            case R.id.Q2ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q2D);
                setFinalanswerbutton();

                break;
            case R.id.q2FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

