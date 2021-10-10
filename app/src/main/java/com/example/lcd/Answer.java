package com.example.lcd;

public class Answer {
    int number;
    String text;

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

    public Answer(int number, String text) {
        this.number = number;
        this.text = text;
    }
}
