package com.example.yorai.minesweeper.GameLogic;

/**
 * Created by Yorai on 27-Aug-17.
 */

public class Score {
    private int mines;
    private int correctMines;
    private long timer;

    public Score(int mines, int minesCorrect, long timer) {
        this.mines = mines;
        this.correctMines = minesCorrect;
        this.timer = timer;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public int getMinesCorrect() {
        return correctMines;
    }

    public void setMinesCorrect(int minesCorrect) {
        this.correctMines = minesCorrect;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }
}
