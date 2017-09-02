package com.example.yorai.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.yorai.minesweeper.GameLogic.Game;
import com.example.yorai.minesweeper.GameLogic.MineField;

public class GameActivity extends Activity {
    private GridView gridView;
    private Game newGame;
    private TextView timerView, numberOfFlagsView;
    private int timerCount = 0 ;
    private int correctFlags;

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
                    endGame();
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MineField field = newGame.getMineField();
                if (field.getItem(i).isClicked() || newGame.getIsWon() || newGame.getIsLost())
                    return false;

                field.getItem(i).setFlag();
                if(field.getItem(i).isFlagged()){
                    newGame.getMineField().addFlag();
                    if (field.getItem(i).isMine())
                        correctFlags++;
                } else{
                    newGame.getMineField().removeFlag();
                    if (field.getItem(i).isMine())
                        correctFlags--;
                }
                ((MineField) gridView.getAdapter()).notifyDataSetChanged();
                numberOfFlags();
                return true;
            }
        });
    }

    private void numberOfFlags() {
        int i = newGame.getMineField().getNumOfMines() - newGame.getMineField().getNumOfFlags();
        numberOfFlagsView = findViewById(R.id.flagsNumber);
        numberOfFlagsView.setText(""+i);
    }

    private void timerStart() {
        timerView = findViewById(R.id.Timer);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                timerView.setText(String.format("%02d:%02d", timerCount / 60, timerCount % 60));
            }
        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    timerCount++;
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void endGame(){
        newGame.getMineField().revealMines();
        Intent easyIntent = new Intent(GameActivity.this, ScoreActivity.class);
        easyIntent.putExtra("MINES",newGame.getMineField().getNumOfMines());
        easyIntent.putExtra("CORRECT_MINES",correctFlags);
        easyIntent.putExtra("TIMER",timerCount);
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        startActivity(easyIntent);
        this.finish();
    }

}