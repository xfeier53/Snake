/*
Authorship: Yu Wang
            Tao Xu
            Yue Zhou
 */

package com.anu.snake;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/*
 @author Yuezhou u6682532, TaoXu u5527268,YuWang
Class clarification:
GameView defines the main layout and main logic of our snake's game.
 */
public class GameView extends SurfaceView implements Runnable {
    public Thread m_Thread = null;
    public volatile boolean m_Playing;
    public Canvas canvas;
    public SurfaceHolder m_Holder;
    public Paint m_Paint;
    public SnakeDirection m_direction = SnakeDirection.RIGHT;
    public int screen_width;
    public int screen_height;
    public long m_NextFrameTime;
    public long FPS = 5;
    public final long milli_second = 1000;
    static int m_score;
    public int[] snake_x;
    public int[] snake_y;
    public int snake_length;
    public int food_x;
    public int food_y;
    public int obstacle_x;
    public int obstacle_y;
    public Paint obstacle_paint = new Paint();
    public Random rand_special = new Random();
    public int normal_count = 0;
    public boolean special_food;
    public int special_food_x;
    public int special_food_y;
    public int reference_count = 5;
    public Paint special_food_paint = new Paint();
    public Paint surprise_paint = new Paint();
    public int block_size;
    public final int block_wide = 40;
    public int block_high;
    public int x;
    public int y;
    public boolean isPause = false;
    public int surprise_food_1_x;
    public int surprise_food_1_y;
    public int surprise_food_2_x;
    public int surprise_food_2_y;
    public int surprise_food_3_x;
    public int surprise_food_3_y;
    public int surprise_food_4_x;
    public int surprise_food_4_y;
    public boolean surprise_food_1 = true;
    public boolean surprise_food_2 = true;
    public boolean surprise_food_3 = true;
    public boolean surprise_food_4 = true;
    public ArrayList<int[]> obstacles = new ArrayList<>();

    //The constructor of the GameView, which defines the layout size. Also, the game start when the
    //constructor is called.
    public GameView(Context context, Point size) {
        super(context);
        screen_width = size.x;
        screen_height = size.y;
        //Define the every pixel block size of the game layout
        block_size = screen_width / block_wide;
        block_high = screen_height / block_size;
        special_food_x = rand_special.nextInt(block_wide - 1) + 1;
        special_food_y = rand_special.nextInt(block_high - 1) + 1;
        m_Holder = getHolder();
        m_Paint = new Paint();

        // If the scores reach 200, the snake will be regenerated as a new one.
        snake_x = new int[200];
        snake_y = new int[200];


        // Start the game
        startGame();
    }
    // For test only
    public GameView(Point size) {
        super(null);
    }

    //The run() method updates the game and draw the game.
    @Override
    public void run() {
        while (m_Playing) {
            if (checkForUpdate() && !isPause) {
                updateGame();
                drawGame();
            }
        }
    }

    //This method pauses the game
    public void pause() {
        m_Playing = false;
        try {
            m_Thread.join();
        } catch (InterruptedException e) {
            //Error
        }
    }

    //This method make the game resume
    public void resume() {
        m_Playing = true;
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    //This method starts the game, generates the food, obstacle. Also, the special food will be generated
    //when the score is a multiple of 5.
    public void startGame() {
        // start game with a snake head
        snake_length = 1;
        snake_x[0] = block_wide / 2;
        snake_y[0] = block_high / 2;

        // Add a mouse to eat
        spawnFood();

        //Add a special food
        // Add an obstacle
        spawnObstacle();
        // initial socre to 0
        m_score = 0;

        // initial normal food counts
        normal_count = 0;

        // initial game speed
        FPS = 5;
        obstacles.clear();
        m_NextFrameTime = System.currentTimeMillis();

    }

    // Initial a food
    public void spawnFood() {
        Random random = new Random();
        food_x = random.nextInt(block_wide - 1) + 1;
        food_y = random.nextInt(block_high - 1) + 1;
        while ((food_x == 1 && food_y == 1) || (food_x == block_wide - 2 && food_y == 1) || (food_x == 1 && food_y == block_high - 2) || (food_x == block_wide - 2 && food_y == block_high - 2)) {
            spawnFood();
        }
    }

    //Initialize the surprise food in the corner.
    public void surprise_food() {

        surprise_food_1_x = 1;
        surprise_food_1_y = 1;
        surprise_food_2_x = block_wide - 2;
        surprise_food_2_y = 1;
        surprise_food_3_x = 1;
        surprise_food_3_y = block_high - 2;
        surprise_food_4_x = block_wide - 2;
        surprise_food_4_y = block_high - 2;

    }

    //Initial a obstacle
    public void spawnObstacle() {
        Random random = new Random();
        //obstacles.clear();
        obstacle_x = random.nextInt(block_wide - 3) + 1;
        obstacle_y = random.nextInt(block_high - 6) + 1;

        while (food_x >= obstacle_x && food_x <= obstacle_x + 2 && food_y >= obstacle_y && food_y <= obstacle_y + 5) {
            spawnObstacle();
        }
    }

    //Initial a special food/bonus food
    public void special_food() {
        Random random = new Random();
        special_food_x = random.nextInt(block_wide - 1) + 1;
        special_food_y = random.nextInt(block_high - 1) + 1;

        while ((food_x >= special_food_x && special_food_x <= food_x + 2 && food_y >= special_food_y && special_food_y <= food_y + 5) || (special_food_x >= obstacle_x && special_food_x <= obstacle_x + 2 && special_food_y >= obstacle_y && special_food_y <= obstacle_y + 5)) {
            special_food();
        }
    }

    //A method which is called when the snake move into the food (eat the food）
    //The food's position is updated and the obstacle's position is also updated
    public void eatFood() {
        // increase the length of snake after eating food
        normal_count++;
        snake_length++;
        // add another food
        spawnFood();

        // add another obstacle
        spawnObstacle();

        // update score
        m_score++;

        // speed up the snake
        speedUp();
    }

    //This function is called when the snake eats the special food, the length of the snake will be
    //added 5 if the special food is eaten.
    public void eat_special_Food() {
        snake_length += 5;
        m_score += 5;
        special_food();
        normal_count = 0;
        // slow down the snake
        slowDown();
    }

    //This function add 10 to score after eating a suprise food
    public void eat_surprise() {
        m_score += 10;
    }

    //This function moves the snake during the game.
    public void moveSnake() {
        for (int i = snake_length; i > 0; i--) {
            snake_x[i] = snake_x[i - 1];
            snake_y[i] = snake_y[i - 1];
        }

        // move the head direction
        switch (m_direction) {
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
    public boolean detectDeath(){
        boolean dead = false;

        // check if hit a wall
        if (snake_x[0] == -1) {
            dead = true;
        }
        if (snake_x[0] >= block_wide) {
            dead = true;
        }
        if (snake_y[0] == -1) {
            dead = true;
        }
        if (snake_y[0] == block_high) {
            dead = true;
        }

        // check if eaten itself
        for (int i = snake_length - 1; i > 0; i--) {
            if ((i > 4) && (snake_x[0] == snake_x[i]) && (snake_y[0] == snake_y[i])) {
                dead = true;
            }
        }
        //check if hits the wall
        if (snake_x[0] >= obstacle_x && snake_x[0] <= obstacle_x + 1 && snake_y[0] >= obstacle_y && snake_y[0] <= obstacle_y + 4) {
            dead = true;
        }

        return dead;
    }

//This function updates the game when the snake eats the food, special food, surprise food or dies.
public void updateGame(){
    if(snake_x[0]==food_x&&snake_y[0]==food_y){
        eatFood();
    }
    if(snake_x[0]==special_food_x&&snake_y[0]==special_food_y&&special_food){
        eat_special_Food();
        special_food=false;
    }
    if(snake_x[0]==surprise_food_1_x&&snake_y[0]==surprise_food_1_y&&surprise_food_1){
        eat_surprise();
        surprise_food_1 = false;
    }
    if(snake_x[0]==surprise_food_2_x&&snake_y[0]==surprise_food_2_y&&surprise_food_2){
        eat_surprise();
        surprise_food_2 = false;
    }
    if(snake_x[0]==surprise_food_3_x&&snake_y[0]==surprise_food_3_y&&surprise_food_3){
        eat_surprise();
        surprise_food_3 = false;
    }
    if(snake_x[0]==surprise_food_4_x&&snake_y[0]==surprise_food_4_y&&surprise_food_4){
        eat_surprise();
        surprise_food_4 = false;
    }
    moveSnake();
    if(detectDeath()){
        isPause = true;
        EventBus.getDefault().post(new EventHandler("dead"));
    }
}
//This is the function which draw the entire game layout, snake, food, special food and obstacle.
public void drawGame(){
    if(m_Holder.getSurface().isValid()){
        canvas = m_Holder.lockCanvas();
        canvas.drawColor(Color.argb(255,120,197,87));
        m_Paint.setColor(Color.argb(255, 255, 255, 255));
        surprise_paint.setColor(Color.argb(255,80,255,255));
        obstacle_paint.setColor(Color.argb(255,255,120,120));
        special_food_paint.setColor(Color.argb(255,120,120,255));
        m_Paint.setTextSize(30);
        canvas.drawText("Score: "+m_score,screen_width/2,30,m_Paint);
        // snake drawing
        for (int i = 0; i < snake_length; i++) {
            canvas.drawRect(snake_x[i] * block_size,
                    (snake_y[i] * block_size),
                    (snake_x[i] * block_size) + block_size,
                    (snake_y[i] * block_size) + block_size,
                    m_Paint);
        }
        //draw the surprise food when the score is more than 10
        if(m_score==10) {
            surprise_food();
        }
        if(m_score>=10) {
            if (surprise_food_1)
                canvas.drawRect(surprise_food_1_x * block_size, (surprise_food_1_y * block_size), (surprise_food_1_x * block_size) + block_size, (surprise_food_1_y * block_size) + block_size, surprise_paint);
            if (surprise_food_2)
                canvas.drawRect(surprise_food_2_x * block_size, (surprise_food_2_y * block_size), (surprise_food_2_x * block_size) + block_size, (surprise_food_2_y * block_size) + block_size, surprise_paint);
            if (surprise_food_3)
                canvas.drawRect(surprise_food_3_x * block_size, (surprise_food_3_y * block_size), (surprise_food_3_x * block_size) + block_size, (surprise_food_3_y * block_size) + block_size, surprise_paint);
            if (surprise_food_4)
                canvas.drawRect(surprise_food_4_x * block_size, (surprise_food_4_y * block_size), (surprise_food_4_x * block_size) + block_size, (surprise_food_4_y * block_size) + block_size, surprise_paint);
        }
        //food and special food
        if (normal_count!=reference_count)
            canvas.drawRect(food_x*block_size,(food_y*block_size),(food_x*block_size)+block_size,(food_y*block_size)+block_size,m_Paint);
        else{
            special_food = true;
            canvas.drawRect(special_food_x*block_size,(special_food_y*block_size),(special_food_x*block_size)+block_size,(special_food_y*block_size)+block_size,special_food_paint);
        }
        //obstacle
            canvas.drawRect(obstacle_x * block_size, (obstacle_y * block_size), (obstacle_x * block_size) + block_size * 2, (obstacle_y * block_size) + block_size * 5, obstacle_paint);
            //draw
            m_Holder.unlockCanvasAndPost(canvas);
        }

    }

    //This function checks whether the game should be updated
    public boolean checkForUpdate() {
        if (m_NextFrameTime <= System.currentTimeMillis()) {
            m_NextFrameTime = System.currentTimeMillis() + milli_second / FPS;
            return true;
        }
        return false;
    }

    //This function defines the onTouchEvent, the snake will move the direction that the user slides into.
    // using swipe to control  the snake
    //@author Yuezhou u6682532
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            x = (int) (motionEvent.getX());
            y = (int) (motionEvent.getY());
        }
        if (action == MotionEvent.ACTION_UP) {
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
            } else {
                if (y - this.y < -100) {
                    direction = SnakeDirection.TOP;
                }
                if (y - this.y > 100) {
                    direction = SnakeDirection.BOTTOM;
                }
            }
            if (m_direction == SnakeDirection.TOP || m_direction == SnakeDirection.BOTTOM) {
                if (direction == SnakeDirection.TOP || direction == SnakeDirection.BOTTOM) {
                    // if current direction is up and down, do nothing;
                } else {
                    m_direction = direction;
                }
            } else if (m_direction == SnakeDirection.LEFT || m_direction == SnakeDirection.RIGHT) {
                if (direction == SnakeDirection.LEFT || direction == SnakeDirection.RIGHT) {
                    // if current direction is left and right, do nothing;
                } else {
                    m_direction = direction;
                }
            }
        }
        return true;
    }

    // This function will speed up the snake after eating food
    public void speedUp() {
        FPS++;
    }

    // This function will speed up the snake after eating a special food
    public void slowDown() {
        FPS -= 2;
    }
}
