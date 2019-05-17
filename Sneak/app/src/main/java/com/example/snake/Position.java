package com.example.snake;
/*
Author: Yu Wang (u5762606)
This class represents the position of every point in the sneak, the position is specified by x and y coordinates.
x,y are set as int, which are the x and y position of the food respectively.
4 methods are involved in this class, which is getX(), getY(), seX() and setY().
 */
public class Position {
    private int x,y;
    public Position(int x, int y){
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
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
