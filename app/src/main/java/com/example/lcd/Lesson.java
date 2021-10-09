package com.example.lcd;

import java.util.ArrayList;

public class Lesson {
    private int id;
    private String title;
    private int progress;
    private int numberOfSteps;

    public Lesson(int id, String title, int progress, int numberOfSteps) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.numberOfSteps = numberOfSteps;
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
}
