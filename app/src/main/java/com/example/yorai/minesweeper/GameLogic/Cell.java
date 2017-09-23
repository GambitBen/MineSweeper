package com.example.yorai.minesweeper.GameLogic;

import android.content.Context;

import com.example.yorai.minesweeper.R;

/**
 * Created by Yorai on 27-Aug-17.
 */

public class Cell extends android.support.v7.widget.AppCompatImageView {
    private boolean isMine = false;
    private int numNerbyMine;
    private boolean isFlagged = false;
    private boolean isClicked = false;

    public Cell(Context context) {
        super(context);
        setImageResource(R.drawable.button);
        setScaleType(ScaleType.FIT_CENTER);// why won't this work???

        //had to compromise for these
        //setScaleX(2);
        //setScaleY(2);
        //won't work either...
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getNumNerbyMines() {
        return numNerbyMine;
    }

    public void setNumNerbyMine(int numNerbyMine) {
        this.numNerbyMine = numNerbyMine;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void click() {
        isClicked = true;
        if (isMine)
            setImageResource(R.drawable.bomb_exploded);
        else if (numNerbyMine==0)
            setImageResource(R.drawable.number_0);
        else if (numNerbyMine==1)
            setImageResource(R.drawable.number_1);
        else if (numNerbyMine==2)
            setImageResource(R.drawable.number_2);
        else if (numNerbyMine==3)
            setImageResource(R.drawable.number_3);
        else if (numNerbyMine==4)
            setImageResource(R.drawable.number_4);
        else if (numNerbyMine==5)
            setImageResource(R.drawable.number_5);
        else if (numNerbyMine==6)
            setImageResource(R.drawable.number_6);
        else if (numNerbyMine==7)
            setImageResource(R.drawable.number_7);
        else if (numNerbyMine==8)
            setImageResource(R.drawable.number_8);
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlag() {
        if (!isFlagged) {
            isFlagged = true;
            setImageResource(R.drawable.flag);
        } else {
            isFlagged = false;
            setImageResource(R.drawable.button);
        }
    }

    public void revealMine(){
        if (!isClicked) {
            if (isMine && !isFlagged)
                setImageResource(R.drawable.bomb_normal);
            else if (!isMine && isFlagged)
                setImageResource(R.drawable.bomb_wrong);
        }
    }
}
