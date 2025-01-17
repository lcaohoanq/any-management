package com.lcaohoanq.fucar.views.game;

import java.awt.Graphics;

public interface IGameDrawer {

    void drawHorizontalWall(Graphics g, int startX, int startY, int length, int step);

    void drawVerticalWall(Graphics g, int startX, int startY, int length, int step);

}
