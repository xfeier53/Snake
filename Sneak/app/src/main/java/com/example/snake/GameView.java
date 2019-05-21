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
import android.util.AttributeSet;
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

    public GameView(Context context, Point size) {
        super(context);
        m_context = context;
        screen_width = size.x;
        screen_height = size.y;

        block_size = screen_width/block_wide;
        block_high = screen_height/block_size;

        m_Holder = getHolder();
        m_Paint = new Paint();

        // If the scores reach 200, the snake will be regenerated as a new one.
        snake_x = new int[200];
        snake_y = new int[200];

        // Start the game
        startGame();
    }

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
        m_Playing=false;
        try{
            m_Thread.join();
        }catch (InterruptedException e){
            //Error
        }
    }

    public void resume(){
        m_Playing = true;
        m_Thread = new Thread(this);
        m_Thread.start();
    }


    public void startGame(){
        // start game with a snake head
        snake_length = 1;
        snake_x[0] = block_wide / 2;
        snake_y[0] = block_high / 2;

        // Add a mouse to eat
        spawnFood();

        // initial socre to 0
        m_Score = 0;

        m_NextFrameTIme = System.currentTimeMillis();
    }

    // initial a mouse
    public void spawnFood() {
        Random random = new Random();
        food_x = random.nextInt(NUM_BLOCKs_WIDE - 1) + 1;
        food_y = random.nextInt(m_NumBlocksHigh - 1) + 1;
    }

    private void eatFood(){
        // increase the length of snake after eating food
        snake_length++;

        // add another food
        spawnFood();

        // update score
        m_score++;
    }


    private void moveSnake(){
        for(int i = snake_length; i > 0; i--){
            snake_x[i] = snake_x[i - 1];
            snake_y[i] = snake_y[i - 1];
        }

        // move the head direction
        switch (m_direction){
            case UP:
                snake_y[0]--;
                break;
            case RIGHT:
                snake_x[0]++;
                break;
            case DOWN:
                snake_y[0]++;
                break;
            case LEFT:
                snake_x[0]--;
                break;
        }
    }

    private boolean detectDeath(){
        boolean dead = false;

        // check if hit a wall
        if (snake_x[0] == -1){
            dead = true;
        }
        if (snake_x[0] >= block_wide){
            dead = true;
        }
        if (snake_y == -1){
            dead = true;
        }
        if(snake_y == block_high){
            dead= true;
        }

        // check if eaten itself
        for(int i = snake_length - 1; i > 0; i--) {
            if( (i > 4) && (snake_x[0] == snake_x[i]) && (snake_y[0] == snake_y[i]) ){
                dead = true;
            }
        }
        return dead;
    }

}
