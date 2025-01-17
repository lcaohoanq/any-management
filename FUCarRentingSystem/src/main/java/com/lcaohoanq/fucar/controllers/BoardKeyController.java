package com.lcaohoanq.fucar.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardKeyController extends KeyAdapter {
    // Snake movement directions
    private boolean leftDirection = false;     // Flag for moving left
    private boolean rightDirection = true;     // Flag for moving right
    private boolean upDirection = false;       // Flag for moving up
    private boolean downDirection = false;     // Flag for moving down

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        }

        if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

}
