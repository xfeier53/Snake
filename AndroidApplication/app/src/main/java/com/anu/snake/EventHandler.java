package com.anu.snake;


/**
 * @author Yuezhou u6682532
 * this class used to handle the event communication among whole game
 */
public class EventHandler {
    private String text;

    public EventHandler(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
