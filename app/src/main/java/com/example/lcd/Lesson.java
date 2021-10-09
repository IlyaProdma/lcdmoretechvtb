package com.example.lcd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Lesson {
    private int id;
    private String title;
    private int progress;
    private int numberOfSteps;
    private ArrayList<Step> steps;

    public Lesson(int id, String title, int progress, int numberOfSteps, ArrayList<Step> steps) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.numberOfSteps = numberOfSteps;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getProgress() {
        return progress;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public ArrayList<Step> getSteps() { return steps; }

    public static ArrayList<Lesson> readLessons(InputStream fis) {
        ArrayList<Lesson> lessonList = new ArrayList<Lesson>();
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
                int progress = Integer.parseInt(oneObject.getString("progress"));
                int stepsNumber = Integer.parseInt(oneObject.getString("number_of_steps"));
                JSONArray stepsArray = oneObject.getJSONArray("steps");
                ArrayList<Step> steps = new ArrayList<Step>();
                for (int j = 0; j < stepsArray.length(); ++j) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int sid = Integer.parseInt(stepObject.getString("id"));
                    int type = Integer.parseInt(stepObject.getString("type"));
                    if (type == 1) {
                        String content = stepObject.getString("content");
                        steps.add(new StepMaterial(sid, content));
                    }
                }
                lessonList.add(new Lesson(id, title, progress, stepsNumber, steps));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return lessonList;
    }
}
