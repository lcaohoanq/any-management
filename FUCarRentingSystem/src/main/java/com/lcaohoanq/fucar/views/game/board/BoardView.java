package com.lcaohoanq.fucar.views.game.board;

import com.lcaohoanq.fucar.constants.APIConstants;
import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.controllers.BoardKeyController;
import com.lcaohoanq.fucar.controllers.LoginController;
import com.lcaohoanq.fucar.styles.UIColors;
import com.lcaohoanq.fucar.styles.UIImages;
import com.lcaohoanq.fucar.styles.UISizes;
import com.lcaohoanq.fucar.utils.ApiUtils;
import com.lcaohoanq.fucar.utils.AudioUtils;
import com.lcaohoanq.fucar.views.game.IGameLogic;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.http.HttpResponse;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BoardView extends BoardComponent implements ActionListener, IGameLogic {

    // Board dimensions and settings
    protected final int DOT_SIZE = 10;        // Size of the snake's body
    protected final int RAND_POS = 29;        // Random positioning parameter
    private final int ALL_DOTS = 900;       // Maximum number of dots on the board
    // Snake position and movement
    protected final int[] x = new int[ALL_DOTS];  // X-coordinate of each snake dot
    protected final int[] y = new int[ALL_DOTS];  // Y-coordinate of each snake dot
    private final int DELAY = 50;           // Timer delay for the game loop
    private final int DECREASE_DELAY = 2;       // Decrease delay for faster snake movement
    protected boolean inGame = true;    // Flag indicating whether the game is currently active
    protected int dots;                          // Current number of snake dots
    protected int apple_count = 0;               // Counter for regular apples
    protected int apple_x;                       // X-coordinate of a regular apple
    protected int bigApple_x;                    // X-coordinate of a big apple
    protected int bigApple_y;                    // Y-coordinate of a big apple
    protected int apple_y;                       // Y-coordinate of a regular apple
    // Game timers and images
    protected Timer timer;                       // Timer for regular game events
    protected AudioUtils audioUtils = AudioUtils.getInstance();
    // Game state variables
    private int score = 0;            // Player's score
    private Timer bigAppleTimer;               // Timer for big apple appearance

    protected final UIImages uiImages = new UIImages();
    protected final BoardKeyController boardKeyController = new BoardKeyController();

    public BoardView() {
        addKeyListener(boardKeyController);
        setBackground(UIColors.OTHER_OPTIONS_L);
        setFocusable(true);
        setPreferredSize(UISizes.SIZE_BOARD);

        setLayout(new BorderLayout());
        uiImages.loadImages();
        initGame();

        initBottomPanel();
        initLine();
        initGameOverPanel();
    }

    @Override
    protected void initPlayAgainButton() {
        super.initPlayAgainButton();
        playAgainButton.addActionListener(e -> {
            // Reset game parameters and restart the game
            SwingUtilities.invokeLater(this::resetGame);
        });
    }

    private void initGame() {

        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
        g.setColor(UIColors.PRIMARY_COLOR_L);
        g.drawLine(0, lineBottom, UISizes.WIDTH_BOARD, lineBottom);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            if (apple_count % 5 == 0 && apple_count != 0) {
                g.drawImage(uiImages.getBigApple(), bigApple_x, bigApple_y, this);
            } else {
                g.drawImage(uiImages.getApple(), apple_x, apple_y, this);
            }
            scoreLabel.setText("Score: " + score);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(uiImages.getHead(), x[z], y[z], this);
                } else {
                    g.drawImage(uiImages.getBall(), x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
            updateScore();
        }
    }

    public void updateScore() {
        String username = LoginController.email;
        if (username.isEmpty()) {
            return;
        }
        String endPoint = String.format(
            APIConstants.BASE_URL + "/scores/users/email?" + "email=%s&newScore=%d", username,
            this.score);
        try {
            HttpResponse<String> response = ApiUtils.putRequest(endPoint);
            switch (response.statusCode()) {
                case 200:
                    log.info("Update score successfully");
                    break;
                case 400:
                    log.error("Update score failed");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error when update user's score: " + e.getMessage());
        }
    }

    private void gameOver(Graphics g) {
        // Show the "Play Again" and "Exit" button after displaying "Game Over" message
        gameOverButtonPanel.setVisible(true);
        playAgainButton.setVisible(true);
        exitButton.setVisible(true);
        backToMainMenuButton.setVisible(true);
//         Hide the progress bar
        bigAppleProgressBar.setVisible(false);
    }

    private void resetGame() {
        // Reset game variables here
        // For example:
        score = 0;
        dots = 3;
        apple_count = 0;
        inGame = true;
        bigApple_x = -100;
        bigApple_y = -100;
        // Reset the snake's position
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        // Reset any other necessary game state variables
        boardKeyController.setRightDirection(true);
        boardKeyController.setLeftDirection(false);
        boardKeyController.setUpDirection(false);
        boardKeyController.setDownDirection(false);
        // Hide the "Play Again" button again
        playAgainButton.setVisible(false);
        exitButton.setVisible(false);
        backToMainMenuButton.setVisible(false);
        // Ensure that the gameOverButtonPanel is not visible
        gameOverButtonPanel.setVisible(false);
        // Restart the timer and initialize the game
        timer.stop();
        initGame();
        timer.start();
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            checkScore();
            apple_count++;
            locateApple();
            if (score % 5 != 0) {
                if (isOnSound()) {
                    InputStream inputStream = getClass().getResourceAsStream(
                        ResourcePaths.URL_EATING2);
                    audioUtils.playAudio(inputStream);
                }
            }
            return;
        }
        if ((x[0] >= bigApple_x) && (x[0] <= bigApple_x + 2 * DOT_SIZE)
            && (y[0] >= bigApple_y) && (y[0] <= bigApple_y + 2 * DOT_SIZE)) {
            dots += 5;
            checkBigScore();

            // change the game speed
            int newDelay = Math.max(timer.getDelay() - DECREASE_DELAY, 0);
            timer.setDelay(newDelay);

            // disable the big apple progress bar
            bigAppleProgressBar.setVisible(false);

            // check the big apple are eaten
            bigAppleTimer.stop();

            apple_count = 0;
            locateApple();
            if (isOnSound()) {
                InputStream inputStream = getClass().getResourceAsStream(ResourcePaths.URL_EATING);
                audioUtils.playAudio(inputStream);
            }
        }
    }

    private void checkScore() {
        score++;
    }

    private void checkBigScore() {
        score += 5;
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (boardKeyController.isLeftDirection()) {
            x[0] -= DOT_SIZE;
        }

        if (boardKeyController.isRightDirection()) {
            x[0] += DOT_SIZE;
        }

        if (boardKeyController.isUpDirection()) {
            y[0] -= DOT_SIZE;
        }

        if (boardKeyController.isDownDirection()) {
            y[0] += DOT_SIZE;
        }
    }

    public void setBigAppleTime() {
        // neu ma bigAppleTimer dang null thi tao mot timer moi
        if (bigAppleTimer != null) {
            bigAppleTimer.stop();
        }

        // Apple-related variables
        // Timer for big apple appearance
        int BIG_APPLE_TIMER = 5000;
        bigAppleTimer = new Timer(BIG_APPLE_TIMER, e -> {
            bigAppleTimer.stop();
            if (isOnSound()) {
                InputStream inputStream = getClass().getResourceAsStream(
                    ResourcePaths.URL_BIG_APPLE_DIS);
                audioUtils.playAudio(inputStream);
            }
            apple_count = 0;
            locateApple();
            bigAppleProgressBar.setVisible(false);
        });
        bigAppleProgressBar.setVisible(true);
        bigAppleTimer.start();
    }

    protected boolean isOnSound() {
        log.debug("check is On Sound: {}", !audioUtils.isEmptyPath());
        return !audioUtils.isEmptyPath();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

}