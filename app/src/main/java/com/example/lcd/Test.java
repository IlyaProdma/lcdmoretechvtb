package com.example.lcd;

import android.renderscript.ScriptGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Test {

    private int id;
    private String title;
    private int numberOfAnswers;
    private ArrayList<Answer> answers;

    public Test(int id, String title, int numberOfAnswers, ArrayList<Answer> answers) {
        this.id = id;
        this.title = title;
        this.numberOfAnswers = numberOfAnswers;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNumberOfAnswers() { return numberOfAnswers; }

    public ArrayList<Answer> getAnswers() { return answers; }


    public static ArrayList<Test> readQuestions(InputStream fis) {
        ArrayList<Test> questionList = new ArrayList<>();
        String data = "";
        try {
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)
                data = data + strLine + "\n";
            br.close();
            in.close();
            fis.close();
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                int id = Integer.parseInt(oneObject.getString("id"));
                String title = oneObject.getString("title");
                int answersNumber = Integer.parseInt(oneObject.getString("number_of_answers"));
                JSONArray answersArray = oneObject.getJSONArray("answers");
                ArrayList<Answer> answers = new ArrayList<Answer>();
                for (int j = 0; j < answersArray.length(); ++j) {
                    JSONObject answerObject = answersArray.getJSONObject(j);
                    int aid = Integer.parseInt(answerObject.getString("id"));
                    String content = answerObject.getString("content");
                    answers.add(new Answer(aid, content));
                }
                questionList.add(new Test(id, title, answersNumber, answers));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return questionList;

    }
}
