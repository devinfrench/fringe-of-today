package fringeoftoday;

import fringeoftoday.audio.AudioPlayer;
import fringeoftoday.entities.Entity;
import fringeoftoday.entities.EntityManager;
import fringeoftoday.floor.FloorManager;
import fringeoftoday.graphics.GraphicsApplication;
import fringeoftoday.graphics.panes.DeathPane;
import fringeoftoday.graphics.panes.GamePane;
import fringeoftoday.graphics.panes.LevelMaker;
import fringeoftoday.graphics.panes.MenuPane;
import fringeoftoday.graphics.panes.ShopPane;
import fringeoftoday.graphics.panes.TutorialPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainApplication extends GraphicsApplication {
    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 100;

    private ShopPane shopPane;
    private MenuPane menu;
    private TutorialPane tutorial;
    private GamePane game;
    private DeathPane deathPane;
    private LevelMaker makerPane;
    private FloorManager floorManager;
    private EntityManager entityManager;

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static void importAllLayouts() {
        importLayoutsByType(LayoutType.FLOOR);
        importLayoutsByType(LayoutType.STANDARD);
        importLayoutsByType(LayoutType.BOSS);
        importLayoutsByType(LayoutType.SPAWN);
        importLayoutsByType(LayoutType.DEFAULT);
    }

    public static void importer(String fileLocation, int numRows, int numCols) {
        String text = null;
        File file = new File("../media/layouts/" + fileLocation + ".txt");
        char textArr[][] = new char[numRows][numCols];

        Scanner sc;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                for (int row = 0; row < numRows; row++) {
                    text = sc.nextLine();
                    String[] textChars = text.split(" ", numCols);
                    for (int col = 0; col < numCols; col++) {
                        textArr[row][col] = textChars[col].charAt(0);
                    }
                }
                addLayout(fileLocation, textArr);
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error doing this");
            e.printStackTrace();
        }
    }

    public static void addLayout(String fileLocation, char[][] textArr) {
        switch (fileLocation) {
            case ("floors"):
                FloorManager.addFloorLayout(textArr);
                break;
            case ("rooms_standard"):
                FloorManager.addRoomLayout(textArr);
                break;
            case ("rooms_spawn"):
                FloorManager.setSpawnRoom(textArr);
                break;
            case ("rooms_boss"):
                FloorManager.addBossRoomLayout(textArr);
                break;
            case ("default"):
            	FloorManager.setDefaultRoom(textArr);
        }

    }

    public static void importLayoutsByType(LayoutType type) {
        switch (type) {
            case FLOOR:
                importer("floors", FloorManager.FLOOR_ROWS, FloorManager.FLOOR_COLS);
                break;

            case STANDARD:
                importer("rooms_standard", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
                break;

            case BOSS:
                importer("rooms_boss", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
                break;

            case SPAWN:
                importer("rooms_spawn", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
                break;
                
            case DEFAULT:
            	importer("default", FloorManager.ROOM_ROWS, FloorManager.ROOM_COLS);
            	break;
        }
    }

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        PlayerData.playerFileSetup();
        floorManager = new FloorManager();
        entityManager = new EntityManager();
        importAllLayouts();
        FloorManager.generateNewFloor();
    }

    public void run() {
        tutorial = new TutorialPane(this);
        shopPane = new ShopPane(this);
        menu = new MenuPane(this);
        game = new GamePane(this);
        deathPane = new DeathPane(this);
        makerPane = new LevelMaker(this);

        switchToMenu();
    }

    public FloorManager getFloorManager() {
        return floorManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void switchToMenu() {
        switchToScreen(menu);
    }

    public void switchToShop() {
        switchToScreen(shopPane);
    }

    public void switchToMaker() {
    	switchToScreen(makerPane);
    }
    
    public void switchToTutorial() {
        PlayerData.updateMap("Tutorial", Integer.parseInt(PlayerData.getMap().get("Tutorial")) + 100);
        switchToScreen(tutorial);
    }

    public void switchToGame() {
        AudioPlayer.getInstance().stopMusic();
        PlayerData.writeFile();
        AudioPlayer.getInstance().pauseMusic();
        if (Integer.parseInt(PlayerData.getMap().get("Tutorial")) == 0) {
            PlayerData.updateMap("Tutorial", 1);
            switchToTutorial();
        } else {
            switchToScreen(game);
        }

    }

    public void switchToDeath(Entity killer) {
        deathPane.setKiller(killer);
        switchToScreen(deathPane);
    }

    public void exitProgram() {
        PlayerData.writeFile();
        System.exit(0);
    }

    public enum LayoutType {
        FLOOR, STANDARD, BOSS, SPAWN, DEFAULT
    }
}
