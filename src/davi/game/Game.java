package davi.game;

import processing.core.PApplet;

import static davi.game.GameData.*;

public class Game {
    public static void main(String[] args) {
        try {

            String[] updaterArgs = { "Game" };
            String[] rendererArgs = { "Game" };

            GameUpdater gu = new GameUpdater();
            GameRenderer gr = new GameRenderer();

            // Adding all the necessary information to the GameData object
            // ----------------------------------------------------------

            // Human
            gd_humanPlayer = new PlayerHuman(BORDER, SIZE.y - BORDER - PLAYER_SIZE.y, PLAYER_SIZE.x, PLAYER_SIZE.y,
                    "JOGADOR");

            // Setting the human stats
            for (int i = 0; i < gd_humanPlayer.getStats().length; i++) {
                gd_humanPlayer.getStats()[i].setButtonDimensions(
                        BORDER + PLAYER_SIZE.x + BUTTON_SPACING + i * (BUTTON_SIZE.x + BUTTON_SPACING),
                        SIZE.y - BORDER - BUTTON_SIZE.y, BUTTON_SIZE.x, BUTTON_SIZE.y);

                gd_humanPlayer.getStats()[i].setBarDimensions(SIZE.x - BORDER - BAR_SIZE.x,
                        SIZE.y - BORDER - BAR_SIZE.y - i * (BAR_SIZE.y + BAR_SPACING), BAR_SIZE.x, BAR_SIZE.y);
            }

            // Computer
            gd_computerPlayer = new PlayerComputer(SIZE.x - BORDER - PLAYER_SIZE.x, BORDER, PLAYER_SIZE.x,
                    PLAYER_SIZE.y, "COMPUTADOR");

            // Setting the computer stats
            for (int i = 0; i < gd_computerPlayer.getStats().length; i++) {
                gd_computerPlayer.getStats()[i].setButtonDimensions(SIZE.x - BORDER - PLAYER_SIZE.x - BUTTON_SPACING
                        - BUTTON_SIZE.x - i * (BUTTON_SIZE.x + BUTTON_SPACING), BORDER, BUTTON_SIZE.x, BUTTON_SIZE.y);

                gd_computerPlayer.getStats()[i].setBarDimensions(BORDER, BORDER + i * (BAR_SIZE.y + BAR_SPACING),
                        BAR_SIZE.x, BAR_SIZE.y);
            }

            // Text Box
            gd_descriptionText = new TextBox(BORDER, SIZE.y / 2, SIZE.x / 2, TEXTBOX_TEXT_SIZE, "");
            // ----------------------------------------------------------

            // Starting the sketches
            PApplet.runSketch(rendererArgs, gr);
            PApplet.runSketch(updaterArgs, gu);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
