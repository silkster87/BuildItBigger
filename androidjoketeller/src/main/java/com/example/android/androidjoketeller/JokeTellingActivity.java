package com.example.android.androidjoketeller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JokeTellingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_telling);

        TextView jokeTextView = findViewById(R.id.joke_textView);

        String joke = getIntent().getStringExtra("joke");

        jokeTextView.setText(joke);
    }
}
