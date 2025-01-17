package com.lcaohoanq.fucar.views.game;

import com.lcaohoanq.fucar.controllers.MenuController;
import com.lcaohoanq.fucar.controllers.PlayController;
import com.lcaohoanq.fucar.styles.UIBorders;
import com.lcaohoanq.fucar.styles.UIImages;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMode extends JFrame implements IGameLogic {
    private final JMenuBar jMenuBar = new JMenuBar();
    private final JMenu jMenu = new JMenu("HELP");
    private final JMenuItem jMenuItem_Back_To_Main_Menu = new JMenuItem("Back to main menu");

    public GameMode(String mode) {
        initMenu();
        initUI(mode);
    }

    private void initUI(String mode) {
        checkGameMode(this, mode);
        setResizable(false);
        pack();
        setTitle("Snake");
        setIconImage(UIImages.icon);
        setJMenuBar(jMenuBar);
        MenuController.menuView.dispose();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initMenu() {
        jMenuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        jMenuBar.setBorder(UIBorders.LINE_MENU_BAR);
        jMenuBar.add(jMenu);
        jMenu.add(jMenuItem_Back_To_Main_Menu);
        jMenuItem_Back_To_Main_Menu.addActionListener(new PlayController(this));
        this.setJMenuBar(jMenuBar);
    }

    public void startGame() {
        setVisible(true);
    }

    public void stopGame() {
        dispose();
    }

    @Override
    public void checkCollision() {

    }

    @Override
    public void checkSelfCollision() {

    }

    @Override
    public void checkBoundaryCollision() {

    }

    @Override
    public void checkWallCollision() {

    }

    @Override
    public void locateApple() {

    }

    @Override
    public void locateBigApple() {

    }
}
