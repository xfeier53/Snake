package com.anu.snake;

import android.content.Context;
import android.graphics.Point;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    // Test the function, so we need no Context
    GameView gv = new GameView(new Point(200, 200));

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSurpriseFood () {
        gv.surprise_food();
        assertEquals(1, gv.surprise_food_1_x);
        assertEquals(1, gv.surprise_food_1_y);
        assertEquals(1, gv.surprise_food_2_y);
        assertEquals(1, gv.surprise_food_3_x);
    }

    @Test
    public void testEatFood() {
        gv.normal_count = 0;
        gv.snake_length = 0;
        gv.m_score = 0;
        gv.eatFood();
        assertEquals(1, gv.normal_count);
        assertEquals(1, gv.snake_length);
        assertEquals(1, gv.m_score);

        gv.eatFood();
        gv.eatFood();
        assertEquals(3, gv.normal_count);
        assertEquals(3, gv.snake_length);
        assertEquals(3, gv.m_score);
    }

}