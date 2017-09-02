package com.example.yorai.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.yorai.minesweeper.GameLogic.Game;
import com.example.yorai.minesweeper.GameLogic.MineField;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {
    private GridView gridView;
    private Game newGame;
    private TextView timerView, numberOfFlagsView;
    private Timer timer;
    private int timerCount = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        newGame = new Game(this.getApplicationContext(),getIntent().getIntExtra("WIDTH",10),getIntent().getIntExtra("HEIGHT",10),getIntent().getIntExtra("MINES",5)); //making a new game
        createMineFieldGrid();
        numberOfFlags();
        timerStart();
    }

    private void createMineFieldGrid(){
        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(newGame.getMineField());
        gridView.setNumColumns(newGame.getMineField().getWidth());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newGame.clickCell(position);

                ((MineField) gridView.getAdapter()).notifyDataSetChanged();

                if (newGame.getIsWon() || newGame.getIsLost()) {
                    //endGame();
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MineField field = newGame.getMineField();
                if (field.getItem(i).isClicked())
                    return false;

                field.getItem(i).setFlag();
                if(field.getItem(i).isFlagged()){
                    newGame.getMineField().addFlag();
                } else{
                    newGame.getMineField().removeFlag();
                }
                ((MineField) gridView.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });
        numberOfFlags();//showing the number of flags
        timerStart();//starting the timer
    }

    private void numberOfFlags() {
        int i = newGame.getMineField().getNumOfMines()- newGame.getMineField().getNumOfFlags();
        numberOfFlagsView = findViewById(R.id.flagsNumber);
        numberOfFlagsView.setText(""+i);
    }

    private void timerStart() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        timerView = findViewById(R.id.Timer);
                        timerView.setText(String.format("%02d:%02d", timerCount / 60, timerCount % 60));
                        timerCount++;
                    }
                });
            }
        }, 1000, 1000);
    }

}