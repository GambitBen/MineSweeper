package com.example.yorai.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        TextView minesView= (TextView) findViewById(R.id.MinesScore);
        minesView.setText(String.format("%02d/%02d",getIntent().getIntExtra("CORRECT_MINES",0),getIntent().getIntExtra("MINES",10)));
        TextView timerView= (TextView) findViewById(R.id.TimeScore);
        int timerCount = getIntent().getIntExtra("TIMER",0);
        timerView.setText(String.format("%02d:%02d", timerCount / 60, timerCount % 60));
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        }
}
