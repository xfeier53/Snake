package com.example.snake;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private List<Position> snake = new LinkedList<Position>();
    public List<Position> getSnake(){
        return snake;
    }
    public void setSnake(List<Position> snake){
        this.snake = snake;
    }
}
