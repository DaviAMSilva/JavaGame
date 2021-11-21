package davi.game;

import processing.core.PApplet;

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
            GameData.humanPlayer = new PlayerHuman(GameData.BORDER,
                    GameData.SIZE.y - GameData.BORDER - GameData.PLAYER_SIZE.y, GameData.PLAYER_SIZE.x,
                    GameData.PLAYER_SIZE.y, "JOGADOR");

            // Setting the human stats
            for (int i = 0; i < GameData.humanPlayer.getStats().length; i++) {
                GameData.humanPlayer.getStats()[i].setButtonDimensions(
                        GameData.BORDER + GameData.PLAYER_SIZE.x + GameData.BUTTON_SPACING
                                + i * (GameData.BUTTON_SIZE.x + GameData.BUTTON_SPACING),
                        GameData.SIZE.y - GameData.BORDER - GameData.BUTTON_SIZE.y, GameData.BUTTON_SIZE.x,
                        GameData.BUTTON_SIZE.y);

                GameData.humanPlayer.getStats()[i].setBarDimensions(
                        GameData.SIZE.x - GameData.BORDER - GameData.BAR_SIZE.x,
                        GameData.SIZE.y - GameData.BORDER - GameData.BAR_SIZE.y
                                - i * (GameData.BAR_SIZE.y + GameData.BAR_SPACING),
                        GameData.BAR_SIZE.x, GameData.BAR_SIZE.y);
            }

            // Computer
            GameData.computerPlayer = new PlayerComputer(GameData.SIZE.x - GameData.BORDER - GameData.PLAYER_SIZE.x,
                    GameData.BORDER, GameData.PLAYER_SIZE.x, GameData.PLAYER_SIZE.y, "COMPUTADOR");

            // Setting the computer stats
            for (int i = 0; i < GameData.computerPlayer.getStats().length; i++) {
                GameData.computerPlayer.getStats()[i].setButtonDimensions(
                        GameData.SIZE.x - GameData.BORDER - GameData.PLAYER_SIZE.x - GameData.BUTTON_SPACING
                                - GameData.BUTTON_SIZE.x - i * (GameData.BUTTON_SIZE.x + GameData.BUTTON_SPACING),
                        GameData.BORDER, GameData.BUTTON_SIZE.x, GameData.BUTTON_SIZE.y);

                GameData.computerPlayer.getStats()[i].setBarDimensions(GameData.BORDER,
                        GameData.BORDER + i * (GameData.BAR_SIZE.y + GameData.BAR_SPACING), GameData.BAR_SIZE.x,
                        GameData.BAR_SIZE.y);
            }

            // Text Box
            GameData.descriptionText = new TextBox(GameData.BORDER, GameData.SIZE.y / 2, GameData.SIZE.x / 2,
                    GameData.TEXTBOX_TEXT_SIZE, "");
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
