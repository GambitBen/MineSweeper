package com.example.yorai.minesweeper.GameLogic;

import android.content.Context;

/**
 * Created by Yorai on 27-Aug-17.
 */

public class Game {
    private MineField field;
    private boolean wonGame = false;
    private boolean lostGame = false;
    private int correctFlags;

    public Game(Context context, int width, int height, int mines) {
        field = new MineField(context,width,height,mines);
    }

    public MineField getMineField() {
        return field;
    }

    public boolean getIsWon() {
        return wonGame;
    }

    public boolean getIsLost() {
        return lostGame;
    }

    public int getCorrectFlags() {
        return correctFlags;
    }

    public void clickCell(int position) {
        if(lostGame || wonGame)
            return;
        int y = position % field.getWidth();
        int x = position / field.getWidth();
        Cell cell = field.getCell(x,y);
        if (!cell.isClicked()) {
            if(cell.isFlagged()){
                return;
            }
            else if (cell.isMine()) {
                lostGame = true;
                field.clickCell(x,y);
                return;
            } else {
                recursiveFlush(x,y);
                if (field.getNumOfUnpressedTiles() == field.getNumOfMines()) {
                    wonGame = true;
                }
            }
        }
    }
    // continue pressing all the 0 cells
    private void recursiveFlush(int x, int y) {
        Cell cell = field.getCell(x,y);
        if (cell.isMine() || cell.isClicked() || cell.isFlagged()) {
            return;
        }
        field.clickCell(x,y);
        if (cell.getNumNerbyMines() > 0) {
            return;
        }
        for (int i=x-1;i<=x+1; i++){
            if (i>=0 && i<field.getWidth())
                for (int j=y-1; j<=y+1; j++){
                    if (j>=0 && j<field.getHeight())
                        recursiveFlush(i, j);
                }
        }
    }

    public boolean longClickCell(int position){
        if (field.getItem(position).isClicked() || wonGame || lostGame)
            return false;

        field.getItem(position).setFlag();
        if(field.getItem(position).isFlagged()){
            field.addFlag();
            if (field.getItem(position).isMine())
                correctFlags++;
        } else{
            field.removeFlag();
            if (field.getItem(position).isMine())
                correctFlags--;
        }
        return true;
    }
}
