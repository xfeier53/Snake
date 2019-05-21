package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;


import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread m_Thread = null;
    private volatile boolean m_Playing;
    private Canvas canvas;
    private SurfaceHolder m_Holder;
    private Paint m_Paint;
    private Context m_context;
    private SnakeDirection m_direction = SnakeDirection.RIGHT;
    private int screen_width;
    private int screen_height;
    private long m_NextFrameTime;
    private final long FPS = 10;
    private final long milli_second = 1000;
    private int m_score;
    private int[] snake_x;
    private int[] snake_y;
    private int snake_length;
    private int food_x;
    private int food_y;
    private int block_size;
    private final int block_wide = 40;
    private int block_high;
//    private Paint paint = new Paint();
//    private Background background = new Background();
//    private Snake snake = new Snake();
//    public GameView(Context context) {
//        super(context);
//        initialize();
//    }
//
//    private void initialize(){
//        Position position = new Position(background.getGridSize()/2,background.getGridSize()/2);
//        snake.getSnake().add(position);
//
//
//    }
//
//    protected void onDraw(Canvas canvas) {
//        if (background != null) {
//            paint.setColor(Color.RED);
//            drawGrid(canvas);
//        }
//        if (snake != null) {
//            paint.setColor(Color.GREEN);
//            drawSnake(canvas);
//        }
//    }
//    private void drawSnake(Canvas canvas){
//        List<Position> snakePosition = snake.getSnake();
//        for (Position p:snakePosition){
//            int startX = background.getOffset() + background.getGridWidth()*p.getX();
//            int stopX = startX + background.getGridWidth();
//            int startY = background.getOffset() + background.getGridWidth() * p.getY();
//            int stopY = startY + +background.getGridWidth();
//            canvas.drawRect(startX, startY, stopX, stopY, paint);
//        }
//    }
//    private void drawGrid(Canvas canvas) {
//        for (int i = 0; i <= background.getGridSize(); i++) {
//            int startX = background.getOffset() + background.getGridWidth() * i;
//            int stopX = startX;
//            int startY = background.getOffset();//+gridBean.getGridWidth() * i
//            int stopY = startY + background.getLineLength();//
//            canvas.drawLine(startX, startY, stopX, stopY, paint); }
//        for (int i = 0; i <= background.getGridSize(); i++) {
//            int startX = background.getOffset();//+gridBean.getGridWidth() * i
//            int stopX = startX + background.getLineLength();
//
//            int startY = background.getOffset() + background.getGridWidth() * i;
//            int stopY = startY; canvas.drawLine(startX, startY, stopX, stopY, paint);
//        }
//    }
//    private void drawFood(Canvas canvas){
//        Food food = new Food();
//    }
@Override
    public void run(){
    while(m_Playing){
        if(checkForUpdate()){
            updateGame();
            drawGame();
        }
    }

}
public void pause(){
    m_Playing =false;
    try{
        m_Thread.join();
    } catch(InterruptedException e){}
}
public void resume(){
    m_Playing = true;
    m_Thread = new Thread(this);
    m_Thread.start();
}
public void updateGame(){
    if(snake_x[0]==food_x&&snake_y[0]==food_y){
        eatFood();
    }
    moveSnake();
    if(detectDeath()){
        staryGame();
    }
}
public void drawGame(){
    if(m_Holder.getSurface().isValid()){
        canvas = m_Holder.lockCanvas();
        canvas.drawColor(Color.argb(255,120,197,87));
        m_Paint.setColor(Color.argb(255, 255, 255, 255));
        m_Paint.setTextSize(30);
        canvas.drawText("Score: "+m_score,10,30,m_Paint);
        // snake drawing
        for (int i = 0; i < snake_length; i++) {
            canvas.drawRect(snake_x[i] * block_size,
                    (snake_y[i] * block_size),
                    (snake_x[i] * block_size) + block_size,
                    (snake_y[i] * block_size) + block_size,
                    m_Paint);
        }
        //food
        canvas.drawRect(food_x*block_size,(food_y*block_size),(food_x*block_size)+block_size,(food_y*block_size)+block_size,m_Paint);
        //draw
        m_Holder.unlockCanvasAndPost(canvas);
    }

}
public boolean checkForUpdate(){
    if(m_NextFrameTime<=System.currentTimeMillis()){
        m_NextFrameTime=System.currentTimeMillis()+milli_second/FPS;
        return true;
    }
    return false;
}
public boolean onTouchEvent(MotionEvent motionEvent){
    switch(motionEvent.getAction()&MotionEvent.ACTION_MASK){
        case MotionEvent.ACTION_UP:
            if(motionEvent.getX()>=screen_width/2){
                switch (m_direction){
                    case TOP:
                        m_direction = SnakeDirection.RIGHT;
                        break;
                    case LEFT:
                        m_direction = SnakeDirection.TOP;
                        break;
                    case RIGHT:
                        m_direction = SnakeDirection.BOTTOM;
                        break;
                    case BOTTOM:
                        m_direction = SnakeDirection.LEFT;
                        break;
                }
            }
            else{
                switch (m_direction){
                    case TOP:
                        m_direction = SnakeDirection.LEFT;
                        break;
                    case LEFT:
                        m_direction = SnakeDirection.BOTTOM;
                        break;
                    case RIGHT:
                        m_direction = SnakeDirection.TOP;
                        break;
                    case BOTTOM:
                        m_direction = SnakeDirection.RIGHT;
                        break;
                }
            }
            return true;
    }
}
}
