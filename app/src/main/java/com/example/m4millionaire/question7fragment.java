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

public class question7fragment extends Fragment implements View.OnClickListener{

    Button Q7A,Q7B, Q7C, Q7D, q7confirmbutton;
    TextView QuestionNumlabel, Question7label;
    String[] question7 = {"Question 7", "Which of these antagonist characters was created by novelist J.K. Rowling?", "Lord Voldermort", "Professor Moriarty", "Lord Farqaad", "Darth Vader"};
    String CorrectAnswer = question7[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question7, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question7label = view.findViewById(R.id.question7question);
        Q7A = view.findViewById(R.id.Q7ButtonA);
        Q7A.setOnClickListener(this);
        Q7B = view.findViewById(R.id.Q7ButtonB);
        Q7B.setOnClickListener(this);
        Q7C = view.findViewById(R.id.Q7ButtonC);
        Q7C.setOnClickListener(this);
        Q7D = view.findViewById(R.id.Q7ButtonD);
        Q7D.setOnClickListener(this);
        q7confirmbutton = view.findViewById(R.id.q7FinalAnswer);
        q7confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question7[0]); //set Question label to first index of question7 array
        Question7label.setText(question7[1]); //set question7 to question  in question7 array
        List<String> tmpList = new ArrayList(Arrays.asList(question7)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q7A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q7B.setText("B: " + tmpList.get(1));
        Q7C.setText("C: " + tmpList.get(2));
        Q7D.setText("D: " + tmpList.get(3));
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
        Q7A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q7B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q7C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q7D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q7confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q7confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q7A.setClickable(false);
        Q7B.setClickable(false);
        Q7C.setClickable(false);
        Q7D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 100000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q7confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q7confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q7ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q7A);
                setFinalanswerbutton();

                break;
            case R.id.Q7ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q7B);
                setFinalanswerbutton();

                break;
            case R.id.Q7ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q7C);
                setFinalanswerbutton();

                break;
            case R.id.Q7ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q7D);
                setFinalanswerbutton();

                break;
            case R.id.q7FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

