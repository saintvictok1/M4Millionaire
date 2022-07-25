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

public class question4fragment extends Fragment implements View.OnClickListener{

    Button Q4A,Q4B, Q4C, Q4D, q4confirmbutton;
    TextView QuestionNumlabel, Question4label;
    String[] question4 = {"Question 4", "What is the name of the toy cowboy in Toy Story?", "Woody", "Buzz", "Billy", "Jesse"};
    String CorrectAnswer = question4[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question4, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question4label = view.findViewById(R.id.question4question);
        Q4A = view.findViewById(R.id.Q4ButtonA);
        Q4A.setOnClickListener(this);
        Q4B = view.findViewById(R.id.Q4ButtonB);
        Q4B.setOnClickListener(this);
        Q4C = view.findViewById(R.id.Q4ButtonC);
        Q4C.setOnClickListener(this);
        Q4D = view.findViewById(R.id.Q4ButtonD);
        Q4D.setOnClickListener(this);
        q4confirmbutton = view.findViewById(R.id.q4FinalAnswer);
        q4confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question4[0]); //set Question label to first index of question4 array
        Question4label.setText(question4[1]); //set question4 to question  in question4 array
        List<String> tmpList = new ArrayList(Arrays.asList(question4)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q4A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q4B.setText("B: " + tmpList.get(1));
        Q4C.setText("C: " + tmpList.get(2));
        Q4D.setText("D: " + tmpList.get(3));
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
        Q4A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q4B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q4C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q4D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q4confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q4confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q4A.setClickable(false);
        Q4B.setClickable(false);
        Q4C.setClickable(false);
        Q4D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 10000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q4confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.confirmbutton.setText("Next Question");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q4confirmbutton.setVisibility(View.GONE);
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
            case R.id.Q4ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q4A);
                setFinalanswerbutton();

                break;
            case R.id.Q4ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q4B);
                setFinalanswerbutton();

                break;
            case R.id.Q4ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q4C);
                setFinalanswerbutton();

                break;
            case R.id.Q4ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q4D);
                setFinalanswerbutton();

                break;
            case R.id.q4FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

