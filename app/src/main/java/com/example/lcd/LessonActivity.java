package com.example.lcd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {
    private int lessonId;
    private int stepProgress;
    private int numberAllSteps;
    private TextView progressTextView;
    private TextView contentTextView;
    private ArrayList<Lesson> lessonList;
    private ArrayList<Step> steps;
    private int maxProgress;

    @Override
    protected void onStop() {
        writeToFile();
        super.onStop();
    }

    @Override
    protected void onPause() {
        writeToFile();
        super.onPause();
    }

    private void writeToFile() {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/stats.json");
        FileOutputStream fos = null;
        JSONArray outArray;
        try {
             outArray = new JSONArray();
            for (Lesson lesson : lessonList) {
                JSONObject obj = new JSONObject();
                obj.put("id", lesson.getId());
                obj.put("title", lesson.getTitle());
                if (lesson.getId() == lessonId)
                    obj.put("progress", maxProgress);
                else
                    obj.put("progress", lesson.getProgress());
                obj.put("number_of_steps", lesson.getNumberOfSteps());
                JSONArray stepsArray = new JSONArray();
                for (Step step : steps) {
                    JSONObject objStep = new JSONObject();
                    objStep.put("id", step.getNumber());
                    objStep.put("type", step.getType());
                    if (step.getType() == 1) {
                        objStep.put("content", ((StepMaterial)step).getText());
                    }
                    stepsArray.put(objStep);
                }
                obj.put("steps", stepsArray);
                outArray.put(obj);
            }
            fos = openFileOutput("stats.json", MODE_PRIVATE);
            fos.write(outArray.toString().getBytes());
        }
        catch (Throwable t) {
            System.out.println("4T0-T0 HE TAK C 3ATTNCRMN");
            System.out.println(t.getMessage());
            System.out.println(t.getStackTrace());
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readFromFile() {
        FileInputStream fis = null;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_lesson);
        Bundle b = getIntent().getExtras();
        lessonId = b.getInt("lessonId");
        stepProgress = b.getInt("progressId");
        if (stepProgress == 0)
            stepProgress = 1;
        maxProgress = stepProgress;
        numberAllSteps = b.getInt("numberSteps");
        lessonList = new ArrayList<Lesson>();
        String data = "";
        InputStream fis = null;
            readFromFile();
            if (lessonList.size() == 0)
            try {
                fis = getAssets().open("lessonsInfo.json");
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
        }
            catch (IOException | JSONException exc) {
                System.out.println(exc.getMessage());
            }

        MainActivity.setLastLesson(lessonId-1);
        writeToFile();
        steps = lessonList.get(lessonId-1).getSteps();
        progressTextView = (TextView)findViewById(R.id.step_progress);
        contentTextView = (TextView)findViewById(R.id.step_content);
        progressTextView.setText(
                Integer.toString(stepProgress) + "/" + Integer.toString(numberAllSteps)
        );

        if (steps.get(stepProgress-1).type == 1)
            contentTextView.setText(((StepMaterial)steps.get(stepProgress-1)).getText());
    }

    public void onArrowLeftPressed(View view) {
        if (stepProgress > 1) {
            stepProgress--;
            if (steps.get(stepProgress-1).type == 1)
                contentTextView.setText(((StepMaterial)steps.get(stepProgress-1)).getText());

            progressTextView.setText(
                    Integer.toString(stepProgress) + "/" + Integer.toString(numberAllSteps)
            );
        }
    }

    public void onArrowRightPressed(View view) {
        if (stepProgress < numberAllSteps) {
            stepProgress++;
            if (maxProgress < numberAllSteps - 1)
                maxProgress++;
            if (steps.get(stepProgress-1).type == 1)
                contentTextView.setText(((StepMaterial)steps.get(stepProgress-1)).getText());

            progressTextView.setText(
                    Integer.toString(stepProgress) + "/" + Integer.toString(numberAllSteps)
            );
        }
        writeToFile();
    }

    public void buttonHomePressed(View view) {
        //if (MainActivity.getActivityStarted())
        //    setContentView(R.layout.activity_main);
        //else
        writeToFile();
        startActivity(new Intent(view.getContext(), MainActivity.class));
    }

    public void buttonCataloguePressed(View view) {
        //if (CatalogueActivity.getActivityStarted())
        //    setContentView(R.layout.activity_catalogue);
        //else
        writeToFile();
        startActivity(new Intent(view.getContext(), CatalogueActivity.class));
    }

    public void buttonManualPressed(View view) {
        setContentView(R.layout.activity_manual);
    }
}
