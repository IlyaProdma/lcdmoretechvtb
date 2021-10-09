package com.example.lcd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {
    private int stepProgress;
    private int numberAllSteps;
    private TextView progressTextView;
    private TextView contentTextView;
    private ArrayList<Step> steps;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_lesson);
        Bundle b = getIntent().getExtras();
        int lessonId = b.getInt("lessonId");
        stepProgress = b.getInt("progressId");
        if (stepProgress == 0)
            stepProgress = 1;
        numberAllSteps = b.getInt("numberSteps");
        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        try {
            lessons = Lesson.readLessons(getAssets().open("lessonsInfo.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        steps = lessons.get(lessonId-1).getSteps();
        progressTextView = (TextView)findViewById(R.id.step_progress);
        contentTextView = (TextView)findViewById(R.id.step_content);
        progressTextView.setText(
                Integer.toString(stepProgress) + "/" + Integer.toString(numberAllSteps)
        );
        if (steps.get(stepProgress-1).type == 1)
            contentTextView.setText(((StepMaterial)steps.get(stepProgress-1)).text);
    }

    public void onArrowLeftPressed(View view) {
        if (stepProgress > 1) {
            stepProgress--;
            if (steps.get(stepProgress-1).type == 1)
                contentTextView.setText(((StepMaterial)steps.get(stepProgress-1)).text);

            progressTextView.setText(
                    Integer.toString(stepProgress) + "/" + Integer.toString(numberAllSteps)
            );
        }
    }

    public void onArrowRightPressed(View view) {
        if (stepProgress < numberAllSteps-1) {
            stepProgress++;
            if (steps.get(stepProgress-1).type == 1)
                contentTextView.setText(((StepMaterial)steps.get(stepProgress-1)).text);

            progressTextView.setText(
                    Integer.toString(stepProgress) + "/" + Integer.toString(numberAllSteps)
            );
        }
    }

    public void buttonHomePressed(View view) {
        //if (MainActivity.getActivityStarted())
        //    setContentView(R.layout.activity_main);
        //else
            startActivity(new Intent(view.getContext(), MainActivity.class));
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
}
