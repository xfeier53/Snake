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
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;


import java.util.List;
/*
Authorship: Yu Wang
            Tao Xu
            Yue Zhou
 */
/*
Class clarification:
GameView defines the main layout and main logic of our snake's game.
 */
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
    private long FPS = 5;
    private final long milli_second = 1000;
    private int m_score;
    private int[] snake_x;
    private int[] snake_y;
    private int snake_length;
    private int food_x;
    private int food_y;
    private int obstacle_x;
    private int obstacle_y;
    private Paint obstacle_paint = new Paint();
    private int normal_count = 0;
    private int special_food_x;
    private int special_food_y;
    private int reference_count = 5;
    private Paint special_food_paint = new Paint();
    private int block_size;
    private final int block_wide = 40;
    private int block_high;
    private int x;
    private int y;
    //The constructor of the GameView, which defines the layout size. Also, the game start when the
    //constructor is called.
    private boolean firstTouch = false;
    private boolean isPause = false;

    public GameView(Context context, Point size) {
        super(context);
        m_context = context;
        screen_width = size.x;
        screen_height = size.y;
        //Define the every pixel block size of the game layout
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

    //The run() method updates the game and draw the game.
    @Override
    public void run(){
        while(m_Playing){
            if(checkForUpdate() && !isPause){
                updateGame();
                drawGame();
            }
        }
    }
    //This method pauses the game
    public void pause(){
        m_Playing=false;
        try{
            m_Thread.join();
        }catch (InterruptedException e){
            //Error
        }
    }
    //This method make the game resume
    public void resume(){
        m_Playing = true;
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    //This method starts the game, generates the food, obstacle. Also, the special food will be generated
    //when the score is a multiple of 5.
    public void startGame(){
        // start game with a snake head
        snake_length = 1;
        snake_x[0] = block_wide / 2;
        snake_y[0] = block_high / 2;

        // Add a mouse to eat
        spawnFood();

        //Add a special food
        special_food();

        // Add an obstacle
        spawnObstacle();

        // initial socre to 0
        m_score= 0;

        m_NextFrameTime = System.currentTimeMillis();
    }

    // Initial a food
    public void spawnFood() {
        Random random = new Random();
        food_x = random.nextInt(block_wide - 1) + 1;
        food_y = random.nextInt(block_high - 1) + 1;
    }

    //Initial a obstacle
    public void spawnObstacle() {
        Random random = new Random();
        obstacle_x = random.nextInt(block_wide - 3) + 1;
        obstacle_y = random.nextInt(block_high - 6) + 1;
        while(food_x>=obstacle_x && food_x<=obstacle_x+2 && food_y>=obstacle_y && food_y<=obstacle_y+5){
            spawnObstacle();
        }
    }

    //Initial a special food/bonus food
    public void special_food(){
        Random random = new Random();
        special_food_x = random.nextInt(block_wide - 1) + 1;
        special_food_y = random.nextInt(block_high - 1) + 1;
    }

    //A method which is called when the snake move into the food (eat the food）
    //The food's position is updated and the obstacle's position is also updated
    private void eatFood(){
        // increase the length of snake after eating food
        normal_count++;
        snake_length++;

        // add another food
        spawnFood();

        // add another obstacle
        spawnObstacle();

        // update score
        m_score++;
    }
    //This function is called when the snake eats the special food, the length of the snake will be
    //added 5 if the special food is eaten.
    private void eat_special_Food(){
        snake_length += 5;
        m_score += 5;
        special_food();
        normal_count=0;
    }

    //This function moves the position of the snake.
    private void moveSnake(){
        for(int i = snake_length; i > 0; i--){
            snake_x[i] = snake_x[i - 1];
            snake_y[i] = snake_y[i - 1];
        }

        // move the head direction
        switch (m_direction){
            case TOP:
                snake_y[0]--;
                break;
            case RIGHT:
                snake_x[0]++;
                break;
            case BOTTOM:
                snake_y[0]++;
                break;
            case LEFT:
                snake_x[0]--;
                break;
        }
    }

    //This function will be triggered if the snake runs into the wall, runs into the obstacle or runs
    //into itself.
    private boolean detectDeath(){
        boolean dead = false;

        // check if hit a wall
        if (snake_x[0] == -1){
            dead = true;
        }
        if (snake_x[0] >= block_wide){
            dead = true;
        }
        if (snake_y[0] == -1){
            dead = true;
        }
        if(snake_y[0] == block_high){
            dead= true;
        }

        // check if eaten itself
        for(int i = snake_length - 1; i > 0; i--) {
            if( (i > 4) && (snake_x[0] == snake_x[i]) && (snake_y[0] == snake_y[i]) ){
                dead = true;
            }
        }
        //check if hits the wall
        if (snake_x[0]>=obstacle_x && snake_x[0]<=obstacle_x+1 && snake_y[0]>=obstacle_y && snake_y[0]<=obstacle_y+4){
            dead = true;
        }
        return dead;
    }

//This function updates the game when the snake eats the food, special food or dies.
public void updateGame(){
    if(snake_x[0]==food_x&&snake_y[0]==food_y){
        eatFood();
    }
    if(snake_x[0]==special_food_x&&snake_y[0]==special_food_y){
        eat_special_Food();
    }
    moveSnake();
    if(detectDeath()){
        startGame();
    }
}
//This is the function which draw the entire game layout, snake, food, special food and obstacle.
public void drawGame(){
    if(m_Holder.getSurface().isValid()){
        canvas = m_Holder.lockCanvas();
        canvas.drawColor(Color.argb(255,120,197,87));
        m_Paint.setColor(Color.argb(255, 255, 255, 255));
        obstacle_paint.setColor(Color.argb(255,255,120,120));
        special_food_paint.setColor(Color.argb(255,120,120,255));
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
        //food and special food
        if (normal_count!=reference_count)
            canvas.drawRect(food_x*block_size,(food_y*block_size),(food_x*block_size)+block_size,(food_y*block_size)+block_size,m_Paint);
        else{
            canvas.drawRect(special_food_x*block_size,(special_food_y*block_size),(special_food_x*block_size)+block_size,(special_food_y*block_size)+block_size,special_food_paint);
        }
        //obstacle
        canvas.drawRect(obstacle_x*block_size,(obstacle_y*block_size),(obstacle_x*block_size)+block_size*2,(obstacle_y*block_size)+block_size*5,obstacle_paint);
        //draw
        m_Holder.unlockCanvasAndPost(canvas);
    }

}

//This function checks whether the game should be updated
public boolean checkForUpdate(){
    if(m_NextFrameTime<=System.currentTimeMillis()){
        m_NextFrameTime=System.currentTimeMillis()+milli_second/FPS;
        return true;
    }
    return false;
}
//This function defines the onTouchEvent, the snake will move the direction that the user slides into.
public boolean onTouchEvent(MotionEvent motionEvent){
            int action = motionEvent.getAction()  & MotionEvent.ACTION_MASK;

           if (action == MotionEvent.ACTION_DOWN) {
               long time= System.currentTimeMillis();
                if(firstTouch &&(System.currentTimeMillis() - time) <= 500){
                    firstTouch = false;
                    Log.d("1111",m_Playing +"");
                    isPause = !isPause;
                }else{
                    firstTouch = true;
                    time = System.currentTimeMillis();
                    x = (int) (motionEvent.getX());
                    y = (int) (motionEvent.getY());
               }
           }
            if (action== MotionEvent.ACTION_UP) {
                int x = (int) (motionEvent.getX());
                int y = (int) (motionEvent.getY());
                SnakeDirection direction = SnakeDirection.RIGHT;
                if (Math.abs(x - this.x) > Math.abs(y - this.y)) {
                    if (x - this.x > 100) {
                        direction = SnakeDirection.RIGHT;
                    }
                    if (x - this.x < -100) {
                        direction = SnakeDirection.LEFT;
                    }
                }else{
                    if (y - this.y < -100) {
                        direction = SnakeDirection.TOP;
                    }
                    if (y - this.y > 100) {
                        direction = SnakeDirection.BOTTOM;
                    }
                }
                if (m_direction == SnakeDirection.TOP || m_direction == SnakeDirection.BOTTOM) {
                    if(direction==SnakeDirection.TOP ||direction==SnakeDirection.BOTTOM ){
                    }else{
                        m_direction = direction;
                    }
                } else if (m_direction == SnakeDirection.LEFT || m_direction == SnakeDirection.RIGHT) {
                    if(direction==SnakeDirection.LEFT ||direction==SnakeDirection.RIGHT ){
                    }else{
                        m_direction = direction;
                    }
                }
            }

    return true;
}
}
