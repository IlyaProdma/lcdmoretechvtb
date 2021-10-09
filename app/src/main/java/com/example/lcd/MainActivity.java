package com.example.lcd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intentCatalogue;
    private static boolean activityStarted;

    public static void setActivityStarted(boolean val) {
        activityStarted = val;
    }

    public static boolean getActivityStarted() {
        return activityStarted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityStarted = true;
        CatalogueActivity.setActivityStarted(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentCatalogue = new Intent(this, CatalogueActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
    }

    public void buttonHomePressed(View view) {
        //startActivity(new Intent(view.getContext(), MainActivity.class));
        setContentView(R.layout.activity_main);
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

