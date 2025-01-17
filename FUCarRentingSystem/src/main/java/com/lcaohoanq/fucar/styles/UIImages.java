package com.lcaohoanq.fucar.styles;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.views.base.AppFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Objects;
import javax.swing.ImageIcon;
import lombok.Getter;

@Getter
public class UIImages {
    public static final URL iconURL = AppFrame.class.getResource(ResourcePaths.URL_KEY_ICON);
    public static final Image icon = Toolkit.getDefaultToolkit().createImage(iconURL);

    public static final URL snakeURL = AppFrame.class.getResource(ResourcePaths.URL_SNAKE_LOGO);
    public static final Image logo = Toolkit.getDefaultToolkit().getImage(snakeURL);
    public static final ImageIcon logoIcon = new ImageIcon(logo);

    private Image ball;                        // Snake body image
    private Image apple;                       // Regular apple image
    private Image head;                        // Snake head image
    private Image bigApple;                    // Big apple
    private Image wall;

    public void loadImages() {

        try{
            ball = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_DOT))).getImage();

            apple = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_APPLE))).getImage();

            head = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_HEAD))).getImage();

            bigApple = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_BIG_APPLE))).getImage();

            wall = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(ResourcePaths.URL_WALL))).getImage();
        }catch (Exception e){
            System.out.println("Resources at Board not found");
        }

    }
}
