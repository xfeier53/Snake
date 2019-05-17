package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

public class GameView extends View {
    private Paint paint = new Paint();
    private Background background = new Background();
    private Snake snake = new Snake();
    public GameView(Context context) {
        super(context);
        initialize();
    }

    private void initialize(){
        Position position = new Position(background.getGridSize()/2,background.getGridSize()/2);
        snake.getSnake().add(position);
    }

    protected void onDraw(Canvas canvas) {
        if (background != null) {
            paint.setColor(Color.RED);
            drawGrid(canvas);
        }
        if (snake != null) {
            paint.setColor(Color.GREEN);
            drawSnake(canvas);
        }
    }
    private void drawSnake(Canvas canvas){
        List<Position> snakePosition = snake.getSnake();
        for (Position p:snakePosition){
            int startX = background.getOffset() + background.getGridWidth()*p.getX();
            int stopX = startX + background.getGridWidth();
            int startY = background.getOffset() + background.getGridWidth() * p.getY();
            int stopY = startY + +background.getGridWidth();
            canvas.drawRect(startX, startY, stopX, stopY, paint);
        }
    }
    private void drawGrid(Canvas canvas) {
        for (int i = 0; i <= background.getGridSize(); i++) {
            int startX = background.getOffset() + background.getGridWidth() * i;
            int stopX = startX;
            int startY = background.getOffset();//+gridBean.getGridWidth() * i
            int stopY = startY + background.getLineLength();//
            canvas.drawLine(startX, startY, stopX, stopY, paint); }
        for (int i = 0; i <= background.getGridSize(); i++) {
            int startX = background.getOffset();//+gridBean.getGridWidth() * i
            int stopX = startX + background.getLineLength();

            int startY = background.getOffset() + background.getGridWidth() * i;
            int stopY = startY; canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

}
