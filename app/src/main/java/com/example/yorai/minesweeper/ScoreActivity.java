package com.example.yorai.minesweeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TableLayout table = (TableLayout) findViewById(R.id.scoreTable);


        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        int newCorrectMines = getIntent().getIntExtra("CORRECT_MINES", 0);
        int newMines = getIntent().getIntExtra("MINES", 10);
        int newTimer = getIntent().getIntExtra("TIMER", 0);

        int minimumIndex = 0;
        long minimumMines = 0;
        TextView[][] tvs = new TextView[10][3];
        for (int i = 1; i <= 10; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView nameTextv = new TextView(this);
            TextView minesTextv = new TextView(this);
            TextView timerTextv = new TextView(this);

            String name = sharedPref.getString("name" + i, "Unknown");
            long correctMines = sharedPref.getInt("correctMines" + i, 0);
            long mines = sharedPref.getInt("mines" + i, 0);
            long timer = sharedPref.getInt("timer" + i, 0);

            nameTextv.setText(name);
            minesTextv.setText(String.format("%02d/%02d", correctMines, mines));
            timerTextv.setText(String.format("%02d:%02d", timer / 60, timer % 60));

            tvs[i-1][0]=nameTextv;
            tvs[i-1][1]=minesTextv;
            tvs[i-1][2]=timerTextv;

            if (newCorrectMines > correctMines) {
                nameTextv.setText(getName());
                minesTextv.setText(String.format("%02d/%02d", newCorrectMines, newMines));
                timerTextv.setText(String.format("%02d:%02d", newTimer / 60, newTimer % 60));
            }

            tr.addView(nameTextv);
            tr.addView(minesTextv);
            tr.addView(timerTextv);
        }

        /*
        TextView minesView= (TextView) findViewById(R.id.MinesScore);
        minesView.setText(String.format("%02d/%02d",getIntent().getIntExtra("CORRECT_MINES",0),getIntent().getIntExtra("MINES",10)));
        TextView timerView= (TextView) findViewById(R.id.TimeScore);
        int timerCount = getIntent().getIntExtra("TIMER",0);
        timerView.setText(String.format("%02d:%02d", timerCount / 60, timerCount % 60));
        */
        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getName() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter Name:");
        final EditText input = new EditText(this);
        alert.setView(input);
        final String[] out = new String[1];
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int which) {
                Editable value = input.getText();
                out[0] = value.toString();
            }
        });
        return out[1];
    }
}


