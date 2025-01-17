package com.lcaohoanq.fucar.views.game.box;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.styles.UISizes;
import com.lcaohoanq.fucar.views.game.IGameDrawer;
import com.lcaohoanq.fucar.views.game.board.BoardView;
import java.awt.Graphics;
import java.io.InputStream;

public class Box extends BoardView implements IGameDrawer {
    protected int wallThickness = 20;

    public Box() {
        super();
    }

    @Override
    public void drawHorizontalWall(Graphics g, int startX, int startY, int length, int step) {
        for (int i = startX; i < length; i += step) {
            g.drawImage(uiImages.getWall(), i, 0, this);
            g.drawImage(uiImages.getWall(), i, startY, this);
        }
    }

    @Override
    public void drawVerticalWall(Graphics g, int startX, int startY, int length, int step) {
        for (int i = startX; i < length; i += step) {
            g.drawImage(uiImages.getWall(), 0, i, this);
            g.drawImage(uiImages.getWall(), startY, i, this);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHorizontalWall(g, 0, UISizes.HEIGHT_BOARD - 70, UISizes.WIDTH_BOARD, 20);
        drawVerticalWall(g, 0, UISizes.HEIGHT_BOARD - 70, UISizes.HEIGHT_BOARD - 70, 20);
    }

    @Override
    public void checkCollision() {
        checkSelfCollision();
        checkBoundaryCollision();
        checkWallCollision();
        if (!inGame) {
            if (isOnSound()) {
                InputStream inputStream = getClass().getResourceAsStream(ResourcePaths.URL_GAME_OVER);
                audioUtils.playAudio(inputStream);
            }
            timer.stop();
        }
    }

    @Override
    public void checkSelfCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
                break;
            }
        }
    }

    @Override
    public void checkBoundaryCollision() {
        //nothing
    }

    @Override
    public void checkWallCollision(){
        //x-axis: north and bottom: wall with 20px thickness (but bottom have 50px score panel so
        // plus 50= 70px)
        //y-axis: left and right: wall with 20px thickness
        if (y[0] >= UISizes.HEIGHT_BOARD - 70 || y[0] < 20 || x[0] >= UISizes.WIDTH_BOARD || x[0] < 20) {
            inGame = false;
        }
    }

    private int getRandomPosition() {
        return ((int) (Math.random() * (29 - 2 * wallThickness)) + wallThickness) * DOT_SIZE;
    }

    @Override
    public void locateApple() {
        if (apple_count % 5 == 0 && apple_count != 0) {
            locateBigApple();
        } else {
            bigApple_x = -100;
            apple_x = getRandomPosition();
            bigApple_y = -100;
            apple_y = getRandomPosition();
        }
    }

    @Override
    public void locateBigApple() {
        if (isOnSound()) {
            InputStream inputStream = getClass().getResourceAsStream(ResourcePaths.URL_BIG_APPLE_APP);
            audioUtils.playAudio(inputStream);
        }
        int r = (int) (Math.random() * (RAND_POS - 2 * wallThickness));
        bigApple_x = ((r + wallThickness) * DOT_SIZE);
        apple_x = -100;

        r = (int) (Math.random() * (RAND_POS - 2 * wallThickness));
        bigApple_y = ((r + wallThickness) * DOT_SIZE);
        apple_y = -100;
        setBigAppleTime();
        renderProgressBar();
    }

}
