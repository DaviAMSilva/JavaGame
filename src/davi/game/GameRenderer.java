package davi.game;

import processing.core.PApplet;
import processing.core.PConstants;

import static davi.game.GameData.*;
import static davi.game.GameState.*;

public class GameRenderer extends PApplet {
    @Override
    public synchronized void settings() {
        size((int) SIZE.x, (int) SIZE.y);
    }

    @Override
    public void setup() {
        fill(0);
        textAlign(PConstants.CENTER, PConstants.CENTER);
        text("Loading data...", width / 2.0f, height / 2.0f);
        surface.setTitle("Game");
    }

    @Override
    public synchronized void draw() {
        if (gd_state == NAMING || gd_state == STARTING) {
            drawStarting();
        } else if (gd_state == PLAYING) {
            drawPlaying();
        } else if (gd_state == ENDING) {
            drawEnding();
        }
    }

    private void drawStarting() {
        if (gd_startingBackground != null)
            image(gd_startingBackground[(frameCount / 2) % gd_startingBackground.length], 0, 0, width, height);

        // Drawing the description
        textAlign(PConstants.LEFT, PConstants.CENTER);
        gd_descriptionText.draw(this);
    }

    private void drawPlaying() {
        // Synchronizing because otherwise it causes problems
        synchronized (gd_mousePosition) {
            gd_mousePosition.x = mouseX;
            gd_mousePosition.y = mouseY;
        }
        synchronized (gd_mousePressed) {
            gd_mousePressed = mousePressed;
        }

        if (gd_playingBackground != null)
            image(gd_playingBackground[(frameCount / 2) % gd_playingBackground.length], 0, 0, width, height);

        for (Stat s : gd_humanPlayer.getStats()) {
            s.draw(this);
        }

        for (Stat s : gd_computerPlayer.getStats()) {
            s.draw(this);
        }

        gd_humanPlayer.draw(this);
        gd_computerPlayer.draw(this);

        textAlign(PConstants.LEFT, PConstants.CENTER);
        gd_descriptionText.draw(this);
    }

    private void drawEnding() {
        if (gd_playingBackground != null)
            image(gd_playingBackground[(frameCount / 2) % gd_playingBackground.length], 0, 0, width, height);

        gd_humanPlayer.draw(this);
        gd_computerPlayer.draw(this);

        textAlign(PConstants.CENTER, PConstants.CENTER);
        gd_descriptionText.draw(this);
    }

    // Unfortunately, because this is the window that receives the key events,
    // we need to detect them here instead of in the GameUpdater thread
    @Override
    public synchronized void keyTyped() {
        if (gd_state == NAMING) {
            // Add every key pressed to humanName
            if (key == BACKSPACE) {
                // Remove the last character from humanName
                if (gd_humanName.length() > 0) {
                    gd_humanName = gd_humanName.substring(0, gd_humanName.length() - 1);
                }
            } else if (key == ENTER) {
                // Start the game
                gd_state = STARTING;
            } else {
                gd_humanName += key;
                if (gd_humanName.length() > 20)
                    gd_humanName = gd_humanName.substring(0, 20);
            }
        } else if (gd_state == PLAYING) {
            // When the SPACE key is pressed the energy of both players is set to 1
            // Only for testing of course :)
            if (key == ' ') {
                gd_humanPlayer.setEnergyValue(1);
                gd_computerPlayer.setEnergyValue(1);
            }
        }
    }
}
