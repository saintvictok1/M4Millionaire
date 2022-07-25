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

public class question9fragment extends Fragment implements View.OnClickListener{

    Button Q9A,Q9B, Q9C, Q9D, q9confirmbutton;
    TextView QuestionNumlabel, Question9label;
    String[] question9 = {"Question 9", "Which of these chess figures is closely related to 'Bohemian Rhapsody'?", "Queen", "King", "Pawn", "Bishop"};
    String CorrectAnswer = question9[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question9, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question9label = view.findViewById(R.id.question9question);
        Q9A = view.findViewById(R.id.Q9ButtonA);
        Q9A.setOnClickListener(this);
        Q9B = view.findViewById(R.id.Q9ButtonB);
        Q9B.setOnClickListener(this);
        Q9C = view.findViewById(R.id.Q9ButtonC);
        Q9C.setOnClickListener(this);
        Q9D = view.findViewById(R.id.Q9ButtonD);
        Q9D.setOnClickListener(this);
        q9confirmbutton = view.findViewById(R.id.q9FinalAnswer);
        q9confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question9[0]); //set Question label to first index of question9 array
        Question9label.setText(question9[1]); //set question9 to question  in question9 array
        List<String> tmpList = new ArrayList(Arrays.asList(question9)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q9A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q9B.setText("B: " + tmpList.get(1));
        Q9C.setText("C: " + tmpList.get(2));
        Q9D.setText("D: " + tmpList.get(3));
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
        Q9A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q9B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q9C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q9D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q9confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q9confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q9A.setClickable(false);
        Q9B.setClickable(false);
        Q9C.setClickable(false);
        Q9D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 500000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q9confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q9confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q9ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q9A);
                setFinalanswerbutton();

                break;
            case R.id.Q9ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q9B);
                setFinalanswerbutton();

                break;
            case R.id.Q9ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q9C);
                setFinalanswerbutton();

                break;
            case R.id.Q9ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q9D);
                setFinalanswerbutton();

                break;
            case R.id.q9FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

