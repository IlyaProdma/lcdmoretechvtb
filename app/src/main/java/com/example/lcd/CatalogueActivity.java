package com.example.lcd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CatalogueActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        //lessonsListLayout = findViewById(R.id.lessonsListLayout);
        @SuppressLint("WrongViewCast") Button button = findViewById(R.id.buttonHome);
        button.setBackgroundResource(R.drawable.accept_outline);
    }


}

