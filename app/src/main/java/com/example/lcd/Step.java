package com.example.lcd;

public abstract class Step {
    int number;
    int type;
}

class StepMaterial extends Step {
    String text;

    StepMaterial(int number, String text) {
        type = 1;
        this.number = number;
        this.text = text;
    }
}

class StepGame extends Step {
    // TODO: придумать как хранить данные для игры
}
