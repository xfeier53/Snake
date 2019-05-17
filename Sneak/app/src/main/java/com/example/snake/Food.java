package com.example.snake;

import java.util.Random;

public class Food {
    private int x,y;
    Random random = new Random();
    Background background = new Background();
    public Food(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = random.nextInt(background.getGridSize()-1) ;
    }

    public void setY(int y) {
        this.y = random.nextInt(background.getGridSize()-1);
    }
}
