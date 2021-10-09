package com.example.lcd;

public abstract class Step {
    int number;
}

class StepMaterial extends Step {
    String text;

    StepMaterial(int number, String text) {
        this.number = number;
        this.text = text;
    }
}

class StepGame extends Step {
    // TODO: придумать как хранить данные для игры
}
