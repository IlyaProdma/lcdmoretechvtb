package com.example.lcd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonHomePressed(View view)
    {
        setContentView(R.layout.activity_main);
    }
    public void buttonCataloguePressed(View view)
    {
        setContentView(R.layout.activity_catalogue);
        ShowLessons();

    }
    public void buttonManualPressed(View view) {
        setContentView(R.layout.activity_manual);
    }

    public void ShowLessons() {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        //LinearLayoutManager llm = new LinearLayoutManager(getActivity);
        rv.setLayoutManager(llm);
        ArrayList<Lesson> lessonList = new ArrayList<Lesson>();
        String data = "";
        try {
            InputStream fis = getAssets().open("lessonsInfo.json");
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)
                data = data + strLine + "\n";
            br.close();
            in.close();
            fis.close();
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                int id = Integer.parseInt(oneObject.getString("id"));
                String title = oneObject.getString("title");
                int progress = Integer.parseInt(oneObject.getString("progress"));
                int stepsNumber = Integer.parseInt(oneObject.getString("number_of_steps"));
                lessonList.add(new Lesson(id, title, progress, stepsNumber));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}


class Card {
    private CardView cardView;
    private TextView textView;

    public Card(Context context, Lesson lesson) {
        cardView = new CardView(context);
        textView = new TextView(context);
        textView.setText(lesson.getTitle());
        textView.setTextSize(20);
        cardView.addView(textView);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(100, 30));
    }

    public CardView getCardView() {
        return cardView;
    }
}

class RVAdapter extends RecyclerView.Adapter<RVAdapter.LessonViewHolder>{
    ArrayList<Lesson> lessons;

    RVAdapter(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catalogue, parent, false);
        LessonViewHolder lvh = new LessonViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        holder.lessonTitle.setText(lessons.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView lessonTitle;
        //TextView personAge;

        LessonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            lessonTitle = (TextView)itemView.findViewById(R.id.lesson_title);
        }
    }

}