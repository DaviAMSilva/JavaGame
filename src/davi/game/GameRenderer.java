package davi.game;

import processing.core.PApplet;
import processing.core.PConstants;

public class GameRenderer extends PApplet {
    @Override
    public synchronized void settings() {
        size((int) GameData.SIZE.x, (int) GameData.SIZE.y);
    }

    @Override
    public void setup() {
        fill(0);
        textAlign(PConstants.CENTER, PConstants.CENTER);
        text("Loading background...", width / 2, height / 2);
    }

    @Override
    public synchronized void draw() {
        if (GameData.state == GameState.NAMING || GameData.state == GameState.STARTING) {
            drawStarting();
        } else if (GameData.state == GameState.PLAYING) {
            drawPlaying();
        } else if (GameData.state == GameState.ENDING) {
            drawEnding();
        }
    }

    private void drawStarting() {
        if (GameData.startingBackground != null)
            image(GameData.startingBackground[(frameCount / 2) % GameData.startingBackground.length], 0, 0, width,
                    height);

        // Drawing the description
        textAlign(PConstants.LEFT, PConstants.CENTER);
        GameData.descriptionText.draw(this);
    }

    private void drawPlaying() {
        synchronized (GameData.MOUSE_POSITION) {
            GameData.MOUSE_POSITION.x = mouseX;
            GameData.MOUSE_POSITION.y = mouseY;
        }
        synchronized (GameData.MOUSE_PRESSED) {
            GameData.MOUSE_PRESSED = mousePressed;
        }

        if (GameData.playingBackground != null)
            image(GameData.playingBackground[(frameCount / 2) % GameData.playingBackground.length], 0, 0, width,
                    height);

        for (Stat s : GameData.humanPlayer.getStats()) {
            s.draw(this);
        }

        for (Stat s : GameData.computerPlayer.getStats()) {
            s.draw(this);
        }

        GameData.humanPlayer.draw(this);
        GameData.computerPlayer.draw(this);

        textAlign(PConstants.LEFT, PConstants.CENTER);
        GameData.descriptionText.draw(this);
    }

    private void drawEnding() {
        if (GameData.playingBackground != null)
            image(GameData.playingBackground[(frameCount / 2) % GameData.playingBackground.length], 0, 0, width,
                    height);

        GameData.humanPlayer.draw(this);
        GameData.computerPlayer.draw(this);

        textAlign(PConstants.CENTER, PConstants.CENTER);
        GameData.descriptionText.draw(this);
    }

    // Unfortunately, because this is the window that receives the key events,
    // we need to detect them here instead of in the GameUpdater thread
    @Override
    public synchronized void keyTyped() {
        if (GameData.state == GameState.NAMING) {
            // Add every key pressed to GameData.humanName
            if (key == BACKSPACE) {
                // Remove the last character from GameData.humanName
                if (GameData.humanName.length() > 0) {
                    GameData.humanName = GameData.humanName.substring(0, GameData.humanName.length() - 1);
                }
            } else if (key == ENTER) {
                // Start the game
                GameData.state = GameState.STARTING;
            } else {
                GameData.humanName += key;
                if (GameData.humanName.length() > 20)
                    GameData.humanName = GameData.humanName.substring(0, 20);
            }
        } else if (GameData.state == GameState.PLAYING) {
            // When the C key is pressed the energy of both players is set to 1
            // Only for testing of course :)
            if (key == 'c') {
                GameData.humanPlayer.setEnergyValue(1);
                GameData.computerPlayer.setEnergyValue(1);
            }
        }
    }

    public void waitTime(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
