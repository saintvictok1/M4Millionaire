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

public class question10fragment extends Fragment implements View.OnClickListener{

    Button Q10A,Q10B, Q10C, Q10D, q10confirmbutton;
    TextView QuestionNumlabel, Question10label;
    String[] question10 = {"Question 10", "What did Alfred Nobel Develop?","Dynamite","Atomic Bomb", "Nobelium", "Gun Powder"};
    String CorrectAnswer = question10[2];
    TextView viewselection = null;
    public static String SelectedAnswer = null;
    public static int prize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question10, container, false);

        //Link variables to layout buttons
        QuestionNumlabel = view.findViewById(R.id.questionnumlabel);
        Question10label = view.findViewById(R.id.question10question);
        Q10A = view.findViewById(R.id.Q10ButtonA);
        Q10A.setOnClickListener(this);
        Q10B = view.findViewById(R.id.Q10ButtonB);
        Q10B.setOnClickListener(this);
        Q10C = view.findViewById(R.id.Q10ButtonC);
        Q10C.setOnClickListener(this);
        Q10D = view.findViewById(R.id.Q10ButtonD);
        Q10D.setOnClickListener(this);
        q10confirmbutton = view.findViewById(R.id.q10FinalAnswer);
        q10confirmbutton.setOnClickListener(this);

        QuestionNumlabel.setText(question10[0]); //set Question label to first index of question10 array
        Question10label.setText(question10[1]); //set question10 to question  in question10 array
        List<String> tmpList = new ArrayList(Arrays.asList(question10)); //copy array into a modifiable List to be manipulated
        tmpList.remove(0); //remove question label
        tmpList.remove(0); //remove question
        Collections.shuffle(tmpList); //shuffle remainder of List (answers)
        Q10A.setText("A: " + tmpList.get(0)); //assign shuffled answers to Button labels
        Q10B.setText("B: " + tmpList.get(1));
        Q10C.setText("C: " + tmpList.get(2));
        Q10D.setText("D: " + tmpList.get(3));
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
        Q10A.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q10B.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q10C.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
        Q10D.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.navy));
    }
    public void setbuttoncolor(TextView view){
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.LimeGreen));
        viewselection = view;
    }
    public void setFinalanswerbutton(){
        //GameStartActivity.confirmbutton.setText("Continue");
        q10confirmbutton.setVisibility(View.VISIBLE);

    }
    public void clearselection(){
        SelectedAnswer = null;
        defaultbuttoncolors();
        q10confirmbutton.setVisibility(View.GONE);


    }
    public void disablebuttons(){
        Q10A.setClickable(false);
        Q10B.setClickable(false);
        Q10C.setClickable(false);
        Q10D.setClickable(false);

    }
    public void checkanswer(String str){

        if (str.equals(CorrectAnswer)){
            prize = 1000000;
            GameStartActivity.totalearned = prize; //set prize to total
            GameStartActivity.updateTotal();
            displayCorrectDialog(); //show Correct answer dialog
            q10confirmbutton.setVisibility(View.GONE); //hide FINAL ANSWER button
            disablebuttons();//disable selecting answers again
            GameStartActivity.win = true;
            GameStartActivity.confirmbutton.setText("!!!!!!!!");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

        }else{
            prize = 0;
            viewselection.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.DarkRed));
            displayIncorrectDialog();
            q10confirmbutton.setVisibility(View.GONE);
            disablebuttons();
            GameStartActivity.loss = true;
            GameStartActivity.confirmbutton.setText("Continue");
            GameStartActivity.confirmbutton.setVisibility(View.VISIBLE);

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
            case R.id.Q10ButtonA:
                defaultbuttoncolors();
                setbuttoncolor(Q10A);
                setFinalanswerbutton();

                break;
            case R.id.Q10ButtonB:
                defaultbuttoncolors();
                setbuttoncolor(Q10B);
                setFinalanswerbutton();

                break;
            case R.id.Q10ButtonC:
                defaultbuttoncolors();
                setbuttoncolor(Q10C);
                setFinalanswerbutton();

                break;
            case R.id.Q10ButtonD:
                defaultbuttoncolors();
                setbuttoncolor(Q10D);
                setFinalanswerbutton();

                break;
            case R.id.q10FinalAnswer:
                displayConfirmAlert(viewselection);

            default:
                break;

        }

    }
}

