package com.example.snake;

import java.util.Random;

public class Food {
    private int x,y;
    Position foodPosition;
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
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void generateFood(){
        int foodX = random.nextInt(background.getGridSize()-1);
        int foodY = random.nextInt(background.getGridSize()-1);

        for(int i = 0; i < Snake.getSnake().size(); i++){
            if(foodX == Snake.getSnake().get(i).getX() && foodY == Snake.getSnake().get(i).getY()){
                foodX = random.nextInt(background.getGridSize() - 1);
                foodY = random.nextInt(background.getGridSize() - 1);
                i = 0;
            }
        }
        Food food = new Food(foodX, foodY);
        //refreshFood(food);
    }

    private void refreshFood(Position foodPosition){
        //background.get
    }
















































}
