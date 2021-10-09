package com.example.lcd;

public abstract class Step {
    int number;
}

class StepMaterial extends Step {
    String text;

    StepMaterial(String text) {
        this.text = text;
    }
}

class StepGame extends Step {
    // TODO: придумать как хранить данные для игры
}
