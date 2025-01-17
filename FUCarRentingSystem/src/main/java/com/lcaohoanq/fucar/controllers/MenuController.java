package com.lcaohoanq.fucar.controllers;

import com.lcaohoanq.fucar.constants.ResourcePaths;
import com.lcaohoanq.fucar.enums.HoverState;
import com.lcaohoanq.fucar.styles.UIHovers;
import com.lcaohoanq.fucar.utils.AudioUtils;
import com.lcaohoanq.fucar.views.base.AppComponent;
import com.lcaohoanq.fucar.views.game.GameMode;
import com.lcaohoanq.fucar.views.menu.MenuView;
import com.lcaohoanq.fucar.views.utils.AppAlert;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;

public class MenuController implements MouseListener, ActionListener {

    public static MenuView menuView;
    public static MenuView.MenuModern menuModern;
    private boolean isMenuModern = true;
    private final AudioUtils audioUtils;
    private UIHovers<MenuView> uiHovers;

    private final List<JButton> jButtonList;
//    private UIHovers<MenuView> uiHovers;

    public MenuController(MenuView menuView) {
        MenuController.menuView = menuView;
        this.audioUtils = AudioUtils.getInstance();
        this.jButtonList = Arrays.asList(
            menuView.getJButton_Mode_Classic(),
            menuView.getJButton_Mode_Modern(),
            menuView.getJButton_Mode_Campaign());
        this.uiHovers = new UIHovers<>(menuView);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == menuView.getJButton_Mode_Classic()) {
            EventQueue.invokeLater(() -> {
                menuView.dispose();
                new GameMode("Classic").startGame();
            });
        }
        if (e.getSource() == menuView.getJButton_Mode_Modern()) {
            EventQueue.invokeLater(() -> {

                if (isMenuModern) {
                    menuModern = menuView.new MenuModern();
                    isMenuModern = true;
//                    menuView.dispose();
                    menuModern.setVisible(true);
                } else {
//                    menuView.dispose();
                    isMenuModern = false;
                }
            });
        }
        if (e.getSource() == menuView.getJButton_Mode_Campaign()) {
            EventQueue.invokeLater(() -> {
//                menuView.dispose();
//                new Snake("Campaign").startGame();

                prepareUnsupportFeature();
            });
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (menuView.isActive()) {
            jButtonList.stream()
                .filter(button -> e.getSource() == button)
                .forEach(button -> {
                    uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light", button);
                });
        }
    }


    @Override
    public void mouseExited(MouseEvent e) {
        if (menuView.isActive()) {
            jButtonList.stream()
                .filter(button -> e.getSource() == button)
                .forEach(button -> {
                    uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light", button);
                });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Off")) {
            System.out.println("user click Off sound");
            menuView.setAudio(false);
        } else if (e.getActionCommand().equals("On")) {
            menuView.setAudio(HoverState.ENABLE.isStatus());
        }
    }

    private void prepareUnsupportFeature() {
        InputStream unsupported = getClass().getResourceAsStream(ResourcePaths.URL_EATING);
        audioUtils.playAudio(unsupported);
        AppAlert.IS_NOT_SUPPORT();
    }

    public static class MenuModernController extends AppComponent implements MouseListener {

        private final MenuView.MenuModern menuModern;
        private final AudioUtils audioUtils;
        private UIHovers<MenuView.MenuModern> uiHovers;

        public MenuModernController(MenuView.MenuModern menuModern) {
            this.menuModern = menuModern;
            this.audioUtils = AudioUtils.getInstance();
            this.uiHovers = new UIHovers<>(menuModern);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == menuModern.getJButton_NoMaze()) {
                EventQueue.invokeLater(() -> {
                    menuView.dispose();
                    new GameMode("NoMaze").startGame();
                });
            }
            if (e.getSource() == menuModern.getJButton_Box()) {
                EventQueue.invokeLater(() -> {
                    menuView.dispose();
                    new GameMode("Box").startGame();
                });
            }
            if (e.getSource() == menuModern.getJButton_Tunnel()) {
                EventQueue.invokeLater(() -> {
                    menuView.dispose();
                    new GameMode("Tunnel").startGame();
                });
            }
            if (e.getSource() == menuModern.getJButton_Mill()) {
                EventQueue.invokeLater(() -> {
                    menuView.dispose();
                    new GameMode("Mill").startGame();
                });
            }
            if (e.getSource() == menuModern.getJButton_Rails()) {
                EventQueue.invokeLater(() -> {
                    menuView.dispose();
                    new GameMode("Rails").startGame();
                });
            }
            if (e.getSource() == menuModern.getJButton_Apartment()) {
                EventQueue.invokeLater(() -> {
                    menuView.dispose();
                    new GameMode("Apartment").startGame();
                });
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (e.getSource() == menuModern.getJButton_NoMaze()) {
                uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light",
                                        menuModern.getJButton_NoMaze());
            }
            if (e.getSource() == menuModern.getJButton_Box()) {
                uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light",
                                        menuModern.getJButton_Box());
            }
            if (e.getSource() == menuModern.getJButton_Tunnel()) {
                uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light",
                                        menuModern.getJButton_Tunnel());
            }
            if (e.getSource() == menuModern.getJButton_Mill()) {
                uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light",
                                        menuModern.getJButton_Mill());
            }
            if (e.getSource() == menuModern.getJButton_Rails()) {
                uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light",
                                        menuModern.getJButton_Rails());
            }
            if (e.getSource() == menuModern.getJButton_Apartment()) {
                uiHovers.setHoverButton(HoverState.ENABLE.isStatus(), "light",
                                        menuModern.getJButton_Apartment());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == menuModern.getJButton_NoMaze()) {
                uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light",
                                        menuModern.getJButton_NoMaze());
            }
            if (e.getSource() == menuModern.getJButton_Box()) {
                uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light",
                                        menuModern.getJButton_Box());
            }
            if (e.getSource() == menuModern.getJButton_Tunnel()) {
                uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light",
                                        menuModern.getJButton_Tunnel());
            }
            if (e.getSource() == menuModern.getJButton_Mill()) {
                uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light",
                                        menuModern.getJButton_Mill());
            }
            if (e.getSource() == menuModern.getJButton_Rails()) {
                uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light",
                                        menuModern.getJButton_Rails());
            }
            if (e.getSource() == menuModern.getJButton_Apartment()) {
                uiHovers.setHoverButton(HoverState.DISABLE.isStatus(), "light",
                                        menuModern.getJButton_Apartment());
            }
        }

        private void prepareUnsupportFeature() {
            InputStream unsupported = getClass().getResourceAsStream(ResourcePaths.URL_EATING);
            audioUtils.playAudio(unsupported);
            AppAlert.IS_NOT_SUPPORT();
        }

        @Override
        public void initComponents() {

        }

        @Override
        public void doAction() {

        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MenuView menuView = new MenuView();
            MenuController menuController = new MenuController(menuView);
            menuView.setVisible(true);
        });
    }

}
