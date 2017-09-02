package com.example.yorai.minesweeper.GameLogic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Yorai on 27-Aug-17.
 */

public class MineField extends BaseAdapter {
    private int width;
    private int height;
    private Cell field[][];
    private int mines;
    private int flags = 0;
    private int unpressed;
    private Context context;

    public MineField(Context context, int width, int height, int mines) {
        this.context=context;
        this.width = width;
        this.height = height;
        this.mines = mines;
        field = new Cell[width][height];
        for (int i=0;i<field.length; i++){
            for (int j=0; j<field[i].length; j++){
                field[i][j]=new Cell(context);
            }
        }
        unpressed = width * height;
        GenerateMines(); String minefields="";
        for (int i=0;i<field.length; i++){
            for (int j=0; j<field[i].length; j++){
                minefields+=""+field[i][j].getNumNerbyMines()+" ";
            }
            minefields+="\n";
        }
        Log.i("minefields", minefields);
    }

    public int getHeight() {
        return height;
    }

    void clickCell(int x, int y) {
        field[x][y].click();
        unpressed--;
    }

    public void addFlag() {
        flags++;
    }

    public void removeFlag() {
        flags--;
    }

    public int getNumOfFlags() {
        return flags;
    }

    public int getNumOfMines() {
        return mines;
    }

    public int getWidth() {
        return width;
    }

    public int getNumOfUnpressedTiles() {
        return unpressed;
    }

    public Cell getCell(int row, int col) {
        return field[row][col];
    }

    private void GenerateMines() {
        int i,j;
        for (int r=1;r<=mines;r++){
            do {
                i = (int) (Math.random() * height);
                j = (int) (Math.random() * width);
            }while(field[i][j].isMine());
            field[i][j].setMine(true);
            UpdateNerbyCells(i,j);
        }
    }

    private void UpdateNerbyCells(int x, int y){
        for (int i=x-1;i<=x+1; i++){
            if (i>=0 && i<field.length)
                for (int j=y-1; j<=y+1; j++){
                    if (j>=0 && j<field[i].length)
                        if (!field[i][j].isMine())
                            field[i][j].setNumNerbyMine(field[i][j].getNumNerbyMines()+1);
                }
        }
    }

    @Override
    public int getCount() {
        return width*height;
    }

    @Override
    public Cell getItem(int position) {
        int y = position % field.length;
        int x = position / field.length;
        return field[x][y];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Cell getView(int position, View view, ViewGroup viewGroup) {
        return getItem(position);
    }
}
