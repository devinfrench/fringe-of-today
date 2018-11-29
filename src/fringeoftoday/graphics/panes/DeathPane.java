package fringeoftoday.graphics.panes;

import acm.graphics.GImage;
import acm.graphics.GObject;
import fringeoftoday.MainApplication;
import fringeoftoday.PlayerData;
import fringeoftoday.entities.Entity;
import fringeoftoday.entities.Player;
import fringeoftoday.graphics.GButtonMD;
import fringeoftoday.graphics.GParagraph;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class DeathPane extends GraphicsPane {
    public static final int BUTTON_WIDTH = MainApplication.BUTTON_WIDTH;
    public static final int BUTTON_HEIGHT = MainApplication.BUTTON_HEIGHT;
    private MainApplication program;
    //TODO: connect variables to actual values
    private String level = "-1";
    private String coins;
    private Entity killer;
    private Player player;

    private Font txtFont = new Font("PKMN Mystery Dungeon", 0, 40);
    private GImage deathScreen;
    private GParagraph deathMessage;
    private GButtonMD btnContinue;

    public DeathPane(MainApplication app) {
        this.program = app;
        loadFont();
        deathScreen = new GImage("deathscreen.jpg", 0, 0);
    }

    public void setKiller(Entity killer) {
        this.killer = killer;
    }

    private void makeDeathMessage() {
        level = PlayerData.getMap().get("PreviousRun");
        coins = PlayerData.getMap().get("Coin");    //Pulls the coinage from the map
        deathMessage = new GParagraph("Highest level: " + level + ", Coins: " + coins + ", Killed by: " + killer.toString() + "\n", 0, 0);
        deathMessage.setColor(Color.WHITE);
        deathMessage.setFont(txtFont);
        deathMessage.move((MainApplication.WINDOW_WIDTH - deathMessage.getWidth()) / 2, 600);
        btnContinue = new GButtonMD("Menu", MainApplication.WINDOW_WIDTH - BUTTON_WIDTH, MainApplication.WINDOW_HEIGHT - BUTTON_HEIGHT, BUTTON_WIDTH,
          BUTTON_HEIGHT, "blue");
    }

    @Override
    public void showContents() {
        makeDeathMessage();
        program.add(deathScreen);
        program.add(deathMessage);
        program.add(btnContinue);
    }

    @Override
    public void hideContents() {
        program.remove(deathScreen);
        program.remove(deathMessage);
        program.remove(btnContinue);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = program.getElementAt(e.getX(), e.getY());
        if (obj == btnContinue) {
            program.switchToMenu();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //program.switchToMenu();
    }
}
