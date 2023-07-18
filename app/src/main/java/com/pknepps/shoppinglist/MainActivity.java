package com.pknepps.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /** The xml element helloText represented in java */
    TextView helloText;

    Button button;

    /** True if button has been clicked an odd number of times */
    boolean oddClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.helloButton);
        helloText = findViewById(R.id.helloText);
        helloText.setText((String) "Hello World!");
        oddClick = false;
    }

    public void onHelloButtonClick(View view) {
        String onClick = "What's Up Bitches!";
        String onOtherClick = "Hello World!";
        oddClick = !oddClick;
        if (oddClick) {
            helloText.setText(onClick);
        } else {
            helloText.setText(onOtherClick);
        }
    }
}