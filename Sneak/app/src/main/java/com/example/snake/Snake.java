package com.example.snake;

import java.util.LinkedList;
import java.util.List;
/*
Author: Yu Wang
This class contains a list which stores every point's position of the snake.
Two methods are involved, which are getSnake() and setSnake().

 */
public class Snake {
    private List<Position> snake = new LinkedList<Position>();
    public List<Position> getSnake(){
        return snake;
    }
    public void setSnake(List<Position> snake){
        this.snake = snake;
    }
}
