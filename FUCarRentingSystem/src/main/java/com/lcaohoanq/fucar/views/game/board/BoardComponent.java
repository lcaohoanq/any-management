package com.lcaohoanq.fucar.views.game.board;

import com.lcaohoanq.fucar.styles.UIBorders;
import com.lcaohoanq.fucar.styles.UIColors;
import com.lcaohoanq.fucar.styles.UIFonts;
import com.lcaohoanq.fucar.styles.UILabels;
import com.lcaohoanq.fucar.styles.UISizes;
import com.lcaohoanq.fucar.views.menu.MenuView;
import com.lcaohoanq.fucar.views.utils.AppAlert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import lombok.Getter;

@Getter
public class BoardComponent extends JPanel {

    // UI components
    protected JLabel gameOverLabel;              // Label to display the "Game Over" message
    protected JPanel gameOverPanel;              // Panel for UI components at the game over
    protected JButton playAgainButton;           // Button to play the game again
    protected JButton exitButton;                // Button to exit the game
    protected JPanel playAgainExitButtonPanel;   // Panel for UI components at the game over
    protected JButton backToMainMenuButton;      // Button to go back to the main menu
    protected JPanel backToMainMenuButtonPanel;  // Panel for UI components at the game over
    protected JLabel scoreLabel;                 // Label to display the player's score
    protected final JPanel bottomPanel = new JPanel(); // Panel for UI components at the bottom
    protected final JPanel gameOverButtonPanel = new JPanel(); // Panel for UI components at the game over

    protected JProgressBar bigAppleProgressBar;  // Progress bar for big apple timer
    protected int lineBottom;                    // Bottom line

    protected void initLine() {
        lineBottom =
            UISizes.HEIGHT_BOARD - UISizes.LINE_SPACE_FROM_BOTTOM; // Adjust this value as needed
    }


    private void initScoreLabel() {
        // Initialize the JLabel for live score display
        scoreLabel = new JLabel(UILabels.SCORE_LIVE);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(UIFonts.SCORE_LIVE);
        scoreLabel.setBounds(10, UISizes.HEIGHT_BOARD - 30, 100, 20);
        scoreLabel.setVisible(true);
    }

    private void initProgressBar() {
        // Initialize the JProgressBar for big apple countdown
        bigAppleProgressBar = new JProgressBar(UISizes.MIN_PROGRESS_BAR, UISizes.MAX_PROGRESS_BAR);
        bigAppleProgressBar.setPreferredSize(UISizes.SIZE_PROGRESS_BAR);
        bigAppleProgressBar.setValue(100);
        bigAppleProgressBar.setStringPainted(true);
        bigAppleProgressBar.setForeground(UIColors.PROGRESS_BAR_LOADING);
        bigAppleProgressBar.setBackground(UIColors.PRIMARY_COLOR_L);
        bigAppleProgressBar.setVisible(false);
    }

    protected void initBottomPanel() {
        initScoreLabel();
        initProgressBar();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(UIColors.OTHER_OPTIONS_L);
        bottomPanel.setBorder(UIBorders.BOTTOM_SCORE_PROGRESS_BAR);
        bottomPanel.add(scoreLabel, BorderLayout.WEST);
        bottomPanel.add(bigAppleProgressBar, BorderLayout.EAST);
        bottomPanel.setVisible(true);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initGameOverTitle() {
        gameOverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gameOverLabel = new JLabel(UILabels.GAME_OVER);
        gameOverPanel.setBackground(UIColors.OTHER_OPTIONS_L);
        gameOverLabel.setForeground(UIColors.PRIMARY_COLOR_L);
        gameOverLabel.setBackground(UIColors.OTHER_OPTIONS_L);
        gameOverLabel.setFont(UIFonts.GAME_OVER);
        gameOverLabel.setBounds((UISizes.WIDTH_BOARD - 260) / 2,
                                (UISizes.HEIGHT_BOARD - 50) / 2 - 50, 260, 50);
        gameOverPanel.add(gameOverLabel);
    }

    protected void initPlayAgainButton() {
        playAgainButton = new JButton(UILabels.PLAY_AGAIN);
        playAgainButton.setFont(UIFonts.PLAY_EXIT_BUTTON);
        playAgainButton.setBackground(UIColors.TEXT_COLOR_L);
        playAgainButton.setForeground(UIColors.PRIMARY_COLOR_L);
        playAgainButton.setPreferredSize(UISizes.SIZE_BUTTON_GAME_OVER);
    }

    private void initExitButton() {
        exitButton = new JButton(UILabels.EXIT);
        exitButton.setFont(UIFonts.PLAY_EXIT_BUTTON);
        exitButton.setBackground(UIColors.PROGRESS_BAR_LOADING);
        exitButton.setForeground(UIColors.PRIMARY_COLOR_L);
        exitButton.addActionListener(e -> {
            if (AppAlert.IS_CONFIRM_EXIT() == JOptionPane.YES_OPTION) {
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        });
        exitButton.setPreferredSize(UISizes.SIZE_BUTTON_GAME_OVER);
    }

    private void initPlayAgainExitButtonPanel() {
        initPlayAgainButton();
        initExitButton();
        playAgainExitButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playAgainExitButtonPanel.setBackground(UIColors.OTHER_OPTIONS_L);
        playAgainExitButtonPanel.add(playAgainButton);
        playAgainExitButtonPanel.add(exitButton);
    }

    private void initBackToMainMenuButton() {
        backToMainMenuButton = new JButton(UILabels.BACK_TO_MAIN_MENU);
        backToMainMenuButton.setFont(UIFonts.PLAY_EXIT_BUTTON);
        backToMainMenuButton.setBackground(UIColors.BACK_TO_MAIN_MENU);
        backToMainMenuButton.setForeground(UIColors.PRIMARY_COLOR_L);
        backToMainMenuButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            new MenuView().setVisible(true);
        });
        backToMainMenuButton.setPreferredSize(UISizes.SIZE_BUTTON_GAME_OVER_BACK_TO_MAIN_MENU);
    }

    private void initBackToMainMenuButtonPanel() {
        initBackToMainMenuButton();
        backToMainMenuButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backToMainMenuButtonPanel.setBackground(UIColors.OTHER_OPTIONS_L);
        backToMainMenuButtonPanel.add(backToMainMenuButton);
    }

    protected void initGameOverPanel() {
        initGameOverTitle();
        initPlayAgainExitButtonPanel();
        initBackToMainMenuButtonPanel();
        gameOverButtonPanel.setLayout(new BorderLayout());
        gameOverButtonPanel.setBackground(UIColors.OTHER_OPTIONS_L);
        gameOverButtonPanel.setBorder(UIBorders.GAME_OVER_ELEMENT);
        gameOverButtonPanel.add(gameOverPanel, BorderLayout.NORTH);
        gameOverButtonPanel.add(playAgainExitButtonPanel, BorderLayout.CENTER);
        gameOverButtonPanel.add(backToMainMenuButtonPanel, BorderLayout.SOUTH);
        gameOverButtonPanel.setVisible(false);
        add(gameOverButtonPanel, BorderLayout.CENTER);
    }

    protected void renderProgressBar() {
        // Display the progress bar
        bigAppleProgressBar.setVisible(true);
        // Start the progress bar
        bigAppleProgressBar.setValue(100);
        // Start the timer
        Timer progressBarTimer = new Timer(45, e -> {
            int value = bigAppleProgressBar.getValue();
            if (value > 0) {
                bigAppleProgressBar.setValue(value - 1);
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        progressBarTimer.start();
    }

}
