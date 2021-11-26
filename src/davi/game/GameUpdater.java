package davi.game;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.sound.SoundFile;

import static davi.game.GameData.*;
import static davi.game.GameState.*;

public class GameUpdater extends PApplet {
    @Override
    public void setup() {
        // Hides the window
        surface.setVisible(false);
    }

    @Override
    public synchronized void settings() {
        // Loading the images
        try {
            gd_humanPlayerImage = loadImage("data/Human.png");
            gd_computerPlayerImage = loadImage("data/Computer.png");

            gd_playingBackground = Gif.getPImages(this, "data/PlayingBackground.gif");
            gd_startingBackground = Gif.getPImages(this, "data/StartingBackground.gif");

            gd_music = new SoundFile(this, "data/09battle2.wav");
            gd_music.loop();
        } catch (Exception e) {
            System.out.println("Error loading data");
        }
    }

    @Override
    public synchronized void draw() {
        if (gd_state == NAMING) {
            namingDraw();
        } else if (gd_state == STARTING) {
            startingDraw();
        } else if (gd_state == PLAYING) {
            playingDraw();
        }
    }

    private synchronized void namingDraw() {
        if ((frameCount / 60) % 2 == 0) {
            gd_descriptionText.setText("A sua aventura começa aqui!\nQual o seu nome?\n> " + gd_humanName + "_");
        } else {
            gd_descriptionText.setText("A sua aventura começa aqui!\nQual o seu nome?\n> " + gd_humanName);
        }
    }

    private synchronized void startingDraw() {
        if (gd_humanName.equals("")) {
            gd_humanPlayer.setName("HUMANO");
        } else {
            gd_humanPlayer.setName(gd_humanName);
        }

        gd_descriptionText.setText("Que o jogo comece!");
        waitTime(MEDIUM_DELAY);

        gd_state = PLAYING;
    }

    private synchronized void playingDraw() {
        Player attacker;
        Player defender;

        if (frameCount % 2 == 1) {
            attacker = gd_humanPlayer;
            defender = gd_computerPlayer;
        } else {
            attacker = gd_computerPlayer;
            defender = gd_humanPlayer;
        }

        gd_descriptionText.setText("Próxima rodada!");
        waitTime(DEFAULT_DELAY);

        TurnResult attackerResult = attacker.play(ATAQUE);
        TurnResult defenderResult = defender.play(DEFESA);

        if (attackerResult.getType().equals(defenderResult.getType())) {
            // If of the same type, the attacker wins if it has more value
            if (attackerResult.getValue() > defenderResult.getValue()) {
                defender.subtractEnergy(attackerResult.getValue() - defenderResult.getValue());
                gd_descriptionText.setText(attacker.getName() + " atacou! ("
                        + (attackerResult.getValue() - defenderResult.getValue()) + ")");
            } else {
                gd_descriptionText.setText(defender.getName() + " se defendeu!");
            }
        } else {
            // If of different types, the attacker wins a third of the value
            defender.subtractEnergy(attackerResult.getValue() / 3);

            gd_descriptionText.setText(attacker.getName() + " atacou! (" + (attackerResult.getValue() / 3) + ")");
        }

        waitTime(DEFAULT_DELAY);

        for (Stat stat : attacker.getStats()) {
            stat.setButtonHighlighted(false);
        }

        for (Stat stat : defender.getStats()) {
            stat.setButtonHighlighted(false);
        }

        // If the defender has no energy, the attacker wins
        if (defender.getEnergyValue() <= 0) {
            gd_descriptionText.setText(defender.getName() + " foi eliminado! " + attacker.getName() + " é o vencedor!");

            gd_descriptionText.setPosition(SIZE.x / 2, SIZE.y / 2);

            attacker.setState(GANHADOR);
            defender.setState(PERDEDOR);

            gd_state = ENDING;

            waitTime(LONG_DELAY);

            gd_descriptionText.setText("Obrigado por Jogar!!!");
        }
    }
}
