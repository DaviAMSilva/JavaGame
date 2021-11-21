package davi.game;

import processing.core.PApplet;
import processing.core.PConstants;
import gifAnimation.Gif;

public class GameRenderer extends PApplet {
    @Override
    public synchronized void settings() {
        size((int) GameData.SIZE.x, (int) GameData.SIZE.y);

        // Loading the images
        try {
            GameData.humanPlayerImage = loadImage("data/Human.png");
            GameData.computerPlayerImage = loadImage("data/Computer.png");
        } catch (Exception e) {
            System.out.println("Error loading images");
        }

        GameData.backgroundAnimation = Gif.getPImages(this, "data/Background.gif");
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
        // Drawing the background animation every 2 frames
        image(GameData.backgroundAnimation[(frameCount / 2) % GameData.backgroundAnimation.length], 0, 0, width,
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

        // Drawing the background animation every 2 frames
        image(GameData.backgroundAnimation[(frameCount / 2) % GameData.backgroundAnimation.length], 0, 0, width,
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
        image(GameData.backgroundAnimation[(frameCount / 2) % GameData.backgroundAnimation.length], 0, 0, width,
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
