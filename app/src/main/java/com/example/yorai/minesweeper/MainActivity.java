package com.example.yorai.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button easy,medium,hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easy = (Button) findViewById(R.id.Easy);
        easy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent easyIntent = new Intent(MainActivity.this, GameActivity.class);
                easyIntent.putExtra("MINES",5);
                easyIntent.putExtra("WIDTH",10);
                easyIntent.putExtra("HEIGTH",10);
                startActivity(easyIntent);
            }
        });
        medium = (Button) findViewById(R.id.Medium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mediumIntent = new Intent(MainActivity.this, GameActivity.class);
                mediumIntent.putExtra("MINES",10);
                mediumIntent.putExtra("WIDTH",10);
                mediumIntent.putExtra("HEIGTH",10);
                startActivity(mediumIntent);
            }
        });
        hard = (Button) findViewById(R.id.Hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hardIntent = new Intent(MainActivity.this, GameActivity.class);
                hardIntent.putExtra("MINES",10);
                hardIntent.putExtra("WIDTH",5);
                hardIntent.putExtra("HEIGTH",5);
                startActivity(hardIntent);
            }
        });
    }
}
