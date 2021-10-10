package com.example.lcd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CatalogueActivity extends AppCompatActivity {

    private Intent lesson;
    private static boolean activityStarted;

    public static void setActivityStarted(boolean val) {
        activityStarted = val;
    }

    public static boolean getActivityStarted() {
        return activityStarted;
    }

    private ArrayList<Lesson> readFromFile() {
        FileInputStream fis = null;
        ArrayList<Lesson> lessonList = new ArrayList<>();
        try {
            fis = openFileInput("stats.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            String data = sb.toString();
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                int id = Integer.parseInt(oneObject.getString("id"));
                String title = oneObject.getString("title");
                int progress = Integer.parseInt(oneObject.getString("progress"));
                int stepsNumber = Integer.parseInt(oneObject.getString("number_of_steps"));
                JSONArray stepsArray = oneObject.getJSONArray("steps");
                ArrayList<Step> tempSteps = new ArrayList<Step>();
                for (int j = 0; j < stepsArray.length(); ++j) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int sid = Integer.parseInt(stepObject.getString("id"));
                    int type = Integer.parseInt(stepObject.getString("type"));
                    if (type == 1) {
                        String content = stepObject.getString("content");
                        tempSteps.add(new StepMaterial(sid, content));
                    }
                }
                lessonList.add(new Lesson(id, title, progress, stepsNumber, tempSteps));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessonList;
    }

    private ArrayList<Lesson> read2() {
        ArrayList<Lesson> lessonList = new ArrayList<>();
        try {
            InputStream is = null;
            is = getAssets().open("lessonsInfo.json");
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String data = "";
            while ((strLine = br.readLine()) != null)
                data = data + strLine + "\n";
            br.close();
            in.close();
            is.close();
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                // Pulling items from the array
                int id = Integer.parseInt(oneObject.getString("id"));
                String title = oneObject.getString("title");
                int progress = Integer.parseInt(oneObject.getString("progress"));
                int stepsNumber = Integer.parseInt(oneObject.getString("number_of_steps"));
                JSONArray stepsArray = oneObject.getJSONArray("steps");
                ArrayList<Step> tempSteps = new ArrayList<Step>();
                for (int j = 0; j < stepsArray.length(); ++j) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int sid = Integer.parseInt(stepObject.getString("id"));
                    int type = Integer.parseInt(stepObject.getString("type"));
                    if (type == 1) {
                        String content = stepObject.getString("content");
                        tempSteps.add(new StepMaterial(sid, content));
                    }
                }
                lessonList.add(new Lesson(id, title, progress, stepsNumber, tempSteps));
            }
        } catch(IOException exc){
            exc.printStackTrace();
        } catch(JSONException jsonException){
            jsonException.printStackTrace();
        }
        return lessonList;
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityStarted = true;
        setContentView(R.layout.activity_catalogue);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ArrayList<Lesson> lessonList = readFromFile();
        if (lessonList.size() == 0)
            lessonList = read2();
        RVAdapter adapter = new RVAdapter(this, lessonList);
        rv.setAdapter(adapter);
        //rv.notifyAll();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityStarted = true;
        setContentView(R.layout.activity_catalogue);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        ArrayList<Lesson> lessonList = readFromFile();
        if (lessonList.size() == 0)
            lessonList = read2();
        RVAdapter adapter = new RVAdapter(this, lessonList);
        rv.setAdapter(adapter);
        //rv.notifyAll();
        adapter.notifyDataSetChanged();
    }

    public void buttonHomePressed(View view) {
        //if (MainActivity.getActivityStarted())
        //    setContentView(R.layout.activity_main);
        //else
            startActivity(new Intent(view.getContext(), MainActivity.class));
    }

    public void buttonCataloguePressed(View view) {
        setContentView(R.layout.activity_catalogue);
    }

    public void buttonManualPressed(View view) {
        setContentView(R.layout.activity_manual);
    }

}

class RVAdapter extends RecyclerView.Adapter<RVAdapter.LessonViewHolder>{
    ArrayList<Lesson> lessons;
    private Context context;


    RVAdapter(Context context, ArrayList<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lessons_layout, parent, false);
        LessonViewHolder lvh = new LessonViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        String title = Integer.toString(lessons.get(position).getId()) + ". " +
                lessons.get(position).getTitle();
        holder.lessonTitle.setText(title);
        String progress = Integer.toString(lessons.get(position).getProgress()) + "/" +
                Integer.toString(lessons.get(position).getNumberOfSteps());
        holder.lessonProgress.setText(progress);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lesson = new Intent(context, LessonActivity.class);
                Bundle b = new Bundle();
                b.putInt("lessonId", lessons.get(holder.getAdapterPosition()).getId());
                b.putInt("progressId", lessons.get(holder.getAdapterPosition()).getProgress());
                b.putInt("numberSteps", lessons.get(holder.getAdapterPosition()).getNumberOfSteps());
                lesson.putExtras(b);
                context.startActivity(lesson);
            }
        });

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
        MaterialCardView cv;
        TextView lessonTitle;
        TextView lessonProgress;

        LessonViewHolder(View itemView) {
            super(itemView);
            cv = (MaterialCardView)itemView.findViewById(R.id.cv);
            lessonTitle = (TextView)itemView.findViewById(R.id.lesson_title);
            lessonProgress = (TextView)itemView.findViewById(R.id.lesson_progress);
        }
    }

}
