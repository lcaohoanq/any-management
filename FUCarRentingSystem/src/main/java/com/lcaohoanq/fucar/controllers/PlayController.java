package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.views.game.GameMode;
import com.lcaohoanq.fucar.views.menu.MenuView;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayController implements ActionListener {
    public static MenuView menuView;
    private GameMode gameMode;
    private boolean isLoginView = false;
    private boolean isSnake = false;

    public PlayController(GameMode gameMode) {
        this.gameMode = gameMode;
        isSnake = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSnake) {
            gameMode.dispose();
            EventQueue.invokeLater(() -> {
                menuView = new MenuView();
                menuView.setVisible(true);
            });
        }
    }
}
