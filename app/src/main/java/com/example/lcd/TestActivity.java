package com.example.lcd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TestActivity extends AppCompatActivity {
    private int numberAllQuestions;
    private TextView questionTextView;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
    private ArrayList<Answer> answers;
    private int numberAllAnswers;
    ArrayList<Test> questions;
    int questionId;

    @Override
    protected void onStart()
    {
        super.onStart();
        questionId = 0;
        numberAllAnswers = 5;
        answers = new ArrayList<Answer>();
        questions = new ArrayList<Test>();
        try {
            questions = Test.readQuestions(getAssets().open("testInfo.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(answers.isEmpty()) {setContentView(R.layout.test_first_page);}
        else setContentView(R.layout.activity_test);
    }
    public void setNewQuestion()
    {
        String questionTitle = questions.get(questionId-1).getTitle();
        answers = questions.get(questionId-1).getAnswers();
        answer1 = answers.get(0).text;
        answer2 = answers.get(1).text;
        answer3 = answers.get(2).text;
        answer4 = answers.get(3).text;
        answer5 = answers.get(4).text;
        Button button2 = findViewById(R.id.button2);
        button2.setText(answer1);
        Button button3 = findViewById(R.id.button3);
        button3.setText(answer2);
        Button button4 = findViewById(R.id.button4);
        button4.setText(answer3);
        Button button5 = findViewById(R.id.button5);
        button5.setText(answer4);
        Button button6 = findViewById(R.id.button6);
        button6.setText(answer5);
        TextView questionText = findViewById(R.id.textView);
        questionText.setText(questionTitle);
    }

    public void buttonTestPressed(View view) {

        questionId++;
        setContentView(R.layout.activity_test);
        setNewQuestion();
    }



}
