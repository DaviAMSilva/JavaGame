package davi.game;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

public record GameData() {
    // Setting the variables
    public static final float MAX_STAT_VALUE = 100;

    public static final PVector SIZE = new PVector(1600, 900);
    public static final int BORDER = 20;
    public static final int BUTTON_SPACING = 40;
    public static final int BAR_SPACING = 10;

    public static final int PLAYER_COLOR = new PApplet().color(255, 0, 0);
    public static final int BAR_COLOR = new PApplet().color(0, 255, 127);
    public static final int BAR_OFF_COLOR = new PApplet().color(200, 255, 255);
    public static final int BUTTON_COLOR = new PApplet().color(0, 127, 255);

    public static final int BUTTON_TEXT_COLOR = new PApplet().color(255);
    public static final int BAR_TEXT_COLOR = new PApplet().color(0);
    public static final int TEXTBOX_TEXT_COLOR = new PApplet().color(0);
    public static final int TEXTBOX_TEXT_OUTLINE_COLOR = new PApplet().color(255);

    public static final int WINNER_TINT_COLOR = new PApplet().color(127, 255, 127);
    public static final int LOSER_TINT_COLOR = new PApplet().color(255, 127, 127);

    public static final PVector BUTTON_SIZE = new PVector(200, 100);
    public static final PVector BAR_SIZE = new PVector(360, 20);
    public static final PVector PLAYER_SIZE = new PVector(200, 200);

    public static final int BUTTON_TEXT_SIZE = 20;
    public static final int BAR_TEXT_SIZE = 10;
    public static final int TEXTBOX_TEXT_SIZE = 50;

    public static final int DEFAULT_DELAY = 1500;
    public static final int MEDIUM_DELAY = 2500;
    public static final int LONG_DELAY = 5000;

    public static final String ATAQUE = "ATAQUE";
    public static final String DEFESA = "DEFESA";
    public static final String COMUM = "COMUM";
    public static final String ESPECIAL = "ESPECIAL";
    public static final String GANHADOR = "GANHADOR";
    public static final String PERDEDOR = "PERDEDOR";
    public static final String NORMAL = "NORMAL";

    public static final Random RANDOM = new Random();

    public static PVector gd_mousePosition = new PVector();
    public static Boolean gd_mousePressed = false;

    public static String gd_humanName = "";

    public static PImage gd_humanPlayerImage;
    public static PImage gd_computerPlayerImage;

    public static PImage[] gd_playingBackground;
    public static PImage[] gd_startingBackground;

    public static SoundFile gd_music;

    public static GameState gd_state = GameState.NAMING;

    public static PlayerHuman gd_humanPlayer;
    public static PlayerComputer gd_computerPlayer;
    public static TextBox gd_descriptionText;

    public static void waitTime(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
