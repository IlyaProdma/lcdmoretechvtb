package com.example.lcd;

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
}
