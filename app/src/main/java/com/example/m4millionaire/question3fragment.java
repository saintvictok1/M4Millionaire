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

public class question3fragment extends Fragment implements View.OnClickListener{

    Button Q3A,Q3B, Q3C, Q3D, q3confirmbutton;
    TextView QuestionNumlabel, Question3label;
    String[] question3 = {"Question 3", "How many legs does a spider have?", "Eight", "Six", "Four", "Ten"};
    String CorrectAnswer = question3[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question3, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question3label = view.findViewById(R.id.question3question);
        Q3A = view.findViewById(R.id.Q3ButtonA);
        Q3A.setOnClickListener(this);
        Q3B = view.findViewById(R.id.Q3ButtonB);
        Q3B.setOnClickListener(this);
        Q3C = view.findViewById(R.id.Q3ButtonC);
        Q3C.setOnClickListener(this);
        Q3D = view.findViewById(R.id.Q3ButtonD);
        Q3D.setOnClickListener(this);
        q3confirmbutton = view.findViewById(R.id.q3FinalAnswer);
        q3confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question3[0]); //set Question label to first index of question3 array
        Question3label.setText(question3[1]); //set question3 to question  in question3 array
        List<String> tmpList = new ArrayList(Arrays.asList(question3)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q3A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q3B.setText("B: " + tmpList.get(1));
        Q3C.setText("C: " + tmpList.get(2));
        Q3D.setText("D: " + tmpList.get(3));
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
        Q3A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q3B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q3C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q3D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q3confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q3confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q3A.setClickable(false);
        Q3B.setClickable(false);
        Q3C.setClickable(false);
        Q3D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 5000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q3confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q3confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q3ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q3A);
                setFinalanswerbutton();

                break;
            case R.id.Q3ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q3B);
                setFinalanswerbutton();

                break;
            case R.id.Q3ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q3C);
                setFinalanswerbutton();

                break;
            case R.id.Q3ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q3D);
                setFinalanswerbutton();

                break;
            case R.id.q3FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

