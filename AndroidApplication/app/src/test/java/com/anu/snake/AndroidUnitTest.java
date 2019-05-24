package com.anu.snake;

import android.content.Context;
import android.graphics.Point;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AndroidUnitTest {
    // Test the function, so we need no Context
    GameView gv = new GameView(new Point(200, 200));

    @Test
    public void testSurpriseFood () {
        gv.surprise_food();
        assertEquals(1, gv.surprise_food_1_x);
        assertEquals(1, gv.surprise_food_1_y);
        assertEquals(gv.block_wide - 2, gv.surprise_food_2_x);
        assertEquals(1, gv.surprise_food_2_y);
        assertEquals(1, gv.surprise_food_3_x);
        assertEquals(gv.block_high - 2, gv.surprise_food_3_y);
        assertEquals(gv.block_wide - 2, gv.surprise_food_4_x);
        assertEquals(gv.block_high - 2, gv.surprise_food_4_y);
    }

    @Test
    public void testEatFood() {
        gv.normal_count = 0;
        gv.snake_length = 0;
        gv.m_score = 0;
        gv.block_high = 100;
        gv.FPS = 10;
        gv.eatFood();
        assertEquals(1, gv.normal_count);
        assertEquals(1, gv.snake_length);
        assertEquals(1, gv.m_score);
        assertEquals(11, gv.FPS);
        gv.eatFood();
        gv.eatFood();
        assertEquals(3, gv.normal_count);
        assertEquals(3, gv.snake_length);
        assertEquals(3, gv.m_score);
        assertEquals(13, gv.FPS);
        gv.eatFood();
        assertEquals(4, gv.normal_count);
        assertEquals(4, gv.snake_length);
        assertEquals(4, gv.m_score);
        assertEquals(14, gv.FPS);
    }

    @Test
    public void testSpecialFooe() {
        gv.block_high = 100;
        gv.normal_count = 1;
        gv.snake_length = 1;
        gv.m_score = 1;
        gv.FPS = 10;
        gv.eat_special_Food();
        assertEquals(0, gv.normal_count);
        assertEquals(6, gv.snake_length);
        assertEquals(6, gv.m_score);
        assertEquals(8, gv.FPS);
        gv.eat_special_Food();
        gv.eat_special_Food();
        assertEquals(0, gv.normal_count);
        assertEquals(16, gv.snake_length);
        assertEquals(16, gv.m_score);
        assertEquals(4, gv.FPS);
    }

    @Test
    public void testSurprise() {
        gv.block_high = 100;
        gv.m_score = 1;
        gv.eat_surprise();
        assertEquals(11, gv.m_score);
        gv.eat_surprise();
        gv.eat_surprise();
        assertEquals(31, gv.m_score);
        gv.eat_surprise();
        gv.eat_surprise();
        assertEquals(51, gv.m_score);
    }

    @Test
    public void testSpeed() {
        gv.FPS = 10;
        gv.speedUp();
        gv.slowDown();
        assertEquals(9, gv.FPS);
        gv.speedUp();
        gv.speedUp();
        gv.speedUp();
        assertEquals(12, gv.FPS);
        gv.slowDown();
        gv.slowDown();
        assertEquals(8, gv.FPS);
    }

    @Test
    public void testMoveSnake() {
        gv.snake_length = 5;
        gv.snake_x = new int[]{10, 11, 12, 13, 13, 13};
        gv.snake_y = new int[]{20, 20, 20, 20, 21, 22};
        gv.m_direction = SnakeDirection.TOP;
        gv.moveSnake();
        assertEquals(10, gv.snake_x[0]);
        assertEquals(19, gv.snake_y[0]);
        gv.m_direction = SnakeDirection.RIGHT;
        gv.moveSnake();
        assertEquals(11, gv.snake_x[0]);
        assertEquals(19, gv.snake_y[0]);
        gv.m_direction = SnakeDirection.BOTTOM;
        gv.moveSnake();
        assertEquals(11, gv.snake_x[0]);
        assertEquals(20, gv.snake_y[0]);
        gv.m_direction = SnakeDirection.LEFT;
        gv.moveSnake();
        assertEquals(10, gv.snake_x[0]);
        assertEquals(20, gv.snake_y[0]);

    }

    @Test
    public void testDetectDeath(){
        gv.snake_length = 5;
        gv.snake_x = new int[]{10, 11, 12, 13, 13, 13};
        gv.snake_y = new int[]{20, 20, 20, 20, 21, 22};
        assertEquals(false,gv.detectDeath());
        gv.snake_x = new int[]{-1};
        assertEquals(true,gv.detectDeath());
        gv.snake_x = new int[]{gv.block_wide+1, 1,2,3};
        assertEquals(true,gv.detectDeath());
        gv.snake_y = new int[]{-1};
        assertEquals(true,gv.detectDeath());
        gv.snake_y = new int[]{gv.block_high+1};
        assertEquals(true,gv.detectDeath());

        gv.snake_x = new int[]{2,3,4,4,3,3};
        gv.snake_x = new int[]{2,2,2,3,3,2};
        assertEquals(true,gv.detectDeath());

        gv.obstacle_x = 2;
        gv.obstacle_y = 2;
        assertEquals(true,gv.detectDeath());
    }
}