package com.example.lcd;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   // Button buttonHome;
    //@Override
    //public void onClick(View view)
    //{
    //    switch(view.getId()) {
     //       case buttonHome.getId():
//
     //   }
   // }
    public void buttonHomePressed(View view)
    {
        setContentView(R.layout.activity_main);
    }
    public void buttonCataloguePressed(View view)
    {
        setContentView(R.layout.activity_catalogue);
    }
    public void buttonManualPressed(View view) { setContentView(R.layout.activity_manual);}


}