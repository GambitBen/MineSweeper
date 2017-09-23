package com.example.yorai.minesweeper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.yorai.minesweeper.GameLogic.Game;
import com.example.yorai.minesweeper.GameLogic.MineField;
import com.example.yorai.minesweeper.Services.DegreeService;

import tyrantgit.explosionfield.ExplosionField;

public class GameActivity extends Activity {
    private GridView gridView;
    private Game newGame;
    private TextView timerView, numberOfFlagsView;
    private int timerCount = 0 ;

    private DegreeService mDegreeService;
    boolean mBound = false;
    OrientationEventListener mOrientationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = new Intent(this, DegreeService.class);
        startService(intent);

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                Log.v("Orientation changed to ", ""+orientation);
                Log.v("Orientation changed to ",""+mDegreeService.getDegree());
            }
        };

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
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                newGame.clickCell(i);

                ((MineField) gridView.getAdapter()).notifyDataSetChanged();

                if (newGame.getIsWon() || newGame.getIsLost()) {
                    endGame();
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Boolean value = newGame.longClickCell(i);

                ((MineField) gridView.getAdapter()).notifyDataSetChanged();
                numberOfFlags();

                return value;
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
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                    timerCount++;
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void endGame(){
        newGame.getMineField().revealMines();
        //only reveals the mines when the method ends, why???

        Intent easyIntent = new Intent(GameActivity.this, ScoreActivity.class);
        easyIntent.putExtra("MINES",newGame.getMineField().getNumOfMines());
        easyIntent.putExtra("CORRECT_MINES",newGame.getCorrectFlags());
        easyIntent.putExtra("TIMER",timerCount);
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        if (newGame.getIsLost()) {
            ExplosionField explosionField = ExplosionField.attach2Window(this);
            explosionField.explode(gridView);
        }
        else if(newGame.getIsWon()){
            gridView.animate().alpha(0f).setDuration(3000).start();
        }
        //animation is fine, reveal mines is fine, the thread.sleep or something else isn't working correctly (animation and reveal mines only work at the last split second of the method, no matter what)
        //if you comment out the last 2 lines you can see the animations working
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        startActivity(easyIntent);
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, DegreeService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DegreeService.LocalBinder binder = (DegreeService.LocalBinder) service;
            mDegreeService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



}