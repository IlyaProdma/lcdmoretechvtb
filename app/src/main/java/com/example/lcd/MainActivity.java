package com.example.lcd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intentCatalogue;

    private static int lastLesson = 0;

    private static boolean activityStarted;

    public static void setActivityStarted(boolean val) {
        activityStarted = val;
    }

    public static boolean getActivityStarted() {
        return activityStarted;
    }

    public static void setLastLesson(int val) {
        lastLesson = val;
    }

    private ArrayList<Lesson> readFromFile() {
        FileInputStream fis = null;
        ArrayList<Lesson> lessonList = new ArrayList<>();
        try {
            fis = openFileInput("stats.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            String data = sb.toString();
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                int id = Integer.parseInt(oneObject.getString("id"));
                String title = oneObject.getString("title");
                int progress = Integer.parseInt(oneObject.getString("progress"));
                int stepsNumber = Integer.parseInt(oneObject.getString("number_of_steps"));
                JSONArray stepsArray = oneObject.getJSONArray("steps");
                ArrayList<Step> tempSteps = new ArrayList<Step>();
                for (int j = 0; j < stepsArray.length(); ++j) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int sid = Integer.parseInt(stepObject.getString("id"));
                    int type = Integer.parseInt(stepObject.getString("type"));
                    if (type == 1) {
                        String content = stepObject.getString("content");
                        tempSteps.add(new StepMaterial(sid, content));
                    }
                }
                lessonList.add(new Lesson(id, title, progress, stepsNumber, tempSteps));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessonList;
    }

    private ArrayList<Lesson> read2() {
        ArrayList<Lesson> lessonList = new ArrayList<>();
        try {
            InputStream is = null;
            is = getAssets().open("lessonsInfo.json");
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String data = "";
            while ((strLine = br.readLine()) != null)
                data = data + strLine + "\n";
            br.close();
            in.close();
            is.close();
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                int id = Integer.parseInt(oneObject.getString("id"));
                String title = oneObject.getString("title");
                int progress = Integer.parseInt(oneObject.getString("progress"));
                int stepsNumber = Integer.parseInt(oneObject.getString("number_of_steps"));
                JSONArray stepsArray = oneObject.getJSONArray("steps");
                ArrayList<Step> tempSteps = new ArrayList<Step>();
                for (int j = 0; j < stepsArray.length(); ++j) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int sid = Integer.parseInt(stepObject.getString("id"));
                    int type = Integer.parseInt(stepObject.getString("type"));
                    if (type == 1) {
                        String content = stepObject.getString("content");
                        tempSteps.add(new StepMaterial(sid, content));
                    }
                }
                lessonList.add(new Lesson(id, title, progress, stepsNumber, tempSteps));
            }
        } catch(IOException exc){
            exc.printStackTrace();
        } catch(JSONException jsonException){
            jsonException.printStackTrace();
        }
        return lessonList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityStarted = true;
        CatalogueActivity.setActivityStarted(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Lesson> lessons = read2();
        TextView LessonTitle = findViewById(R.id.textViewLessonProgressLessonName);
        LessonTitle.setText(Integer.toString(lessons.get(lastLesson).getId())+". "+lessons.get(lastLesson).getTitle());
        TextView progressValue = findViewById(R.id.textViewLessonProgressProgressValue);
        progressValue.setText(Integer.toString(lessons.get(lastLesson).getProgress()) + "/" +
                Integer.toString(lessons.get(lastLesson).getNumberOfSteps()));
        ProgressBar progressBarValue = findViewById(R.id.progressBarLessonProgress);
        progressBarValue.setMax(lessons.get(lastLesson).getNumberOfSteps());
        progressBarValue.setProgress(lessons.get(lastLesson).getProgress());
        intentCatalogue = new Intent(this, CatalogueActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        ArrayList<Lesson> lessons = readFromFile();
        if (lessons.size() == 0)
            lessons = read2();
        TextView LessonTitle = findViewById(R.id.textViewLessonProgressLessonName);
        LessonTitle.setText(Integer.toString(lessons.get(lastLesson).getId())+". "
                +lessons.get(lastLesson).getTitle());
        TextView progressValue = findViewById(R.id.textViewLessonProgressProgressValue);
        progressValue.setText(Integer.toString(lessons.get(lastLesson).getProgress()) + "/" +
                Integer.toString(lessons.get(lastLesson).getNumberOfSteps()));
        ProgressBar progressBarValue = findViewById(R.id.progressBarLessonProgress);
        progressBarValue.setMax(lessons.get(lastLesson).getNumberOfSteps());
        progressBarValue.setProgress(lessons.get(lastLesson).getProgress());
    }

    public void buttonHomePressed(View view) {
        //startActivity(new Intent(view.getContext(), MainActivity.class));
        setContentView(R.layout.activity_main);
    }

    public void buttonCataloguePressed(View view) {
        //if (CatalogueActivity.getActivityStarted())
        //    setContentView(R.layout.activity_catalogue);
        //else
            startActivity(new Intent(view.getContext(), CatalogueActivity.class));
    }

    public void buttonManualPressed(View view) {
        setContentView(R.layout.activity_manual);
    }

    public void onContinueClick(View view) {
        ArrayList<Lesson> lessons = readFromFile();
        Intent lesson = new Intent(view.getContext(), LessonActivity.class);
        if (lessons.size() == 0)
            lessons = read2();
        Bundle b = new Bundle();
        b.putInt("lessonId", lessons.get(lastLesson).getId());
        b.putInt("progressId", lessons.get(lastLesson).getProgress());
        b.putInt("numberSteps", lessons.get(lastLesson).getNumberOfSteps());
        lesson.putExtras(b);
        startActivity(lesson);
    }
}

