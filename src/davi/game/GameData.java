package davi.game;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


public record GameData() {
    // Setting the variables
    public static final float MAX_STAT_VALUE = 100;

    public static final PVector SIZE = new PVector(1600, 900);
    public static final int BORDER = 20;
    public static final int BUTTON_SPACING = 40;
    public static final int BAR_SPACING = 10;

    public static final int PLAYER_COLOR = new PApplet().color(255, 0, 0);
    public static final int BAR_COLOR = new PApplet().color(0, 255, 0);
    public static final int BAR_OFF_COLOR = new PApplet().color(200, 255, 200);
    public static final int BUTTON_COLOR = new PApplet().color(0, 0, 255);

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

    public static PVector MOUSE_POSITION = new PVector();
    public static Boolean MOUSE_PRESSED = false;

    public static final String ATAQUE = "ATAQUE";
    public static final String DEFESA = "DEFESA";
    public static final String COMUM = "COMUM";
    public static final String ESPECIAL = "ESPECIAL";
    public static final String GANHADOR = "GANHADOR";
    public static final String PERDEDOR = "PERDEDOR";
    public static final String NORMAL = "NORMAL";

    public static String humanName = "";

    public static PImage humanPlayerImage;
    public static PImage computerPlayerImage;
    public static PImage[] backgroundAnimation;

    public static final Random random = new Random();

    public static GameState state = GameState.NAMING;

    public static PlayerHuman humanPlayer = null;
    public static PlayerComputer computerPlayer = null;
    public static TextBox descriptionText = null;
}
