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

public class question6fragment extends Fragment implements View.OnClickListener{

    Button Q6A,Q6B, Q6C, Q6D, q6confirmbutton;
    TextView QuestionNumlabel, Question6label;
    String[] question6 = {"Question 6", "When was the first computer invented?", "1943", "1974", "1953", "1945"};
    String CorrectAnswer = question6[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question6, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question6label = view.findViewById(R.id.question6question);
        Q6A = view.findViewById(R.id.Q6ButtonA);
        Q6A.setOnClickListener(this);
        Q6B = view.findViewById(R.id.Q6ButtonB);
        Q6B.setOnClickListener(this);
        Q6C = view.findViewById(R.id.Q6ButtonC);
        Q6C.setOnClickListener(this);
        Q6D = view.findViewById(R.id.Q6ButtonD);
        Q6D.setOnClickListener(this);
        q6confirmbutton = view.findViewById(R.id.q6FinalAnswer);
        q6confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question6[0]); //set Question label to first index of question6 array
        Question6label.setText(question6[1]); //set question6 to question  in question6 array
        List<String> tmpList = new ArrayList(Arrays.asList(question6)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q6A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q6B.setText("B: " + tmpList.get(1));
        Q6C.setText("C: " + tmpList.get(2));
        Q6D.setText("D: " + tmpList.get(3));
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
        Q6A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q6B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q6C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q6D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q6confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q6confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q6A.setClickable(false);
        Q6B.setClickable(false);
        Q6C.setClickable(false);
        Q6D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 50000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q6confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q6confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q6ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q6A);
                setFinalanswerbutton();

                break;
            case R.id.Q6ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q6B);
                setFinalanswerbutton();

                break;
            case R.id.Q6ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q6C);
                setFinalanswerbutton();

                break;
            case R.id.Q6ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q6D);
                setFinalanswerbutton();

                break;
            case R.id.q6FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

