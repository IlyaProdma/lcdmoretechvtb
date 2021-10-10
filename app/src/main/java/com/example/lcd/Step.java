package com.example.lcd;

public abstract class Step {
    int number;
    int type;

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }
}

class StepMaterial extends Step {
    private String text;

    public String getText() {
        return text;
    }

    StepMaterial(int number, String text) {
        type = 1;
        this.number = number;
        this.text = text;
    }
}

class StepGame extends Step {
    // TODO: придумать как хранить данные для игры
}
