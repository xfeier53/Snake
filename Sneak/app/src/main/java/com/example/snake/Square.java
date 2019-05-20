package com.example.snake;

import android.graphics.Color;

public class Square {
    private int type;

    public Square(int _type){
        type = _type;
    }

    public void setType(int _type){
        type = _type;
    }

    public int getSquareColor(){
        switch(type) {
            case miniGameType.GRID:
                return Color.WHITE;
            case miniGameType.FOOD:
                return Color.GREEN;
            case miniGameType.SNAKE:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    };
}
