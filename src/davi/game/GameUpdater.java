package davi.game;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.sound.SoundFile;

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
            GameData.humanPlayerImage = loadImage("data/Human.png");
            GameData.computerPlayerImage = loadImage("data/Computer.png");

            GameData.playingBackground = Gif.getPImages(this, "data/PlayingBackground.gif");
            GameData.startingBackground = Gif.getPImages(this, "data/StartingBackground.gif");

            GameData.music = new SoundFile(this, "data/09battle2.wav");
            GameData.music.loop();
        } catch (Exception e) {
            System.out.println("Error loading data");
        }
    }

    @Override
    public synchronized void draw() {
        if (GameData.state == GameState.NAMING) {
            namingDraw();
        } else if (GameData.state == GameState.STARTING) {
            startingDraw();
        } else if (GameData.state == GameState.PLAYING) {
            playingDraw();
        }
    }

    private synchronized void namingDraw() {
        if ((frameCount / 60) % 2 == 0) {
            GameData.descriptionText
                    .setText("A sua aventura começa aqui!\nQual o seu nome?\n> " + GameData.humanName + "_");
        } else {
            GameData.descriptionText.setText("A sua aventura começa aqui!\nQual o seu nome?\n> " + GameData.humanName);
        }
    }

    private synchronized void startingDraw() {
        if (GameData.humanName.equals("")) {
            GameData.humanPlayer.setName("HUMANO");
        } else {
            GameData.humanPlayer.setName(GameData.humanName);
        }

        GameData.descriptionText.setText("Que o jogo comece!");
        waitTime(GameData.MEDIUM_DELAY);

        GameData.state = GameState.PLAYING;
    }

    private synchronized void playingDraw() {
        Player attacker;
        Player defender;

        if (frameCount % 2 == 1) {
            attacker = GameData.humanPlayer;
            defender = GameData.computerPlayer;
        } else {
            attacker = GameData.computerPlayer;
            defender = GameData.humanPlayer;
        }

        GameData.descriptionText.setText("Próxima rodada!");
        waitTime(GameData.DEFAULT_DELAY);

        TurnResult attackerResult = attacker.play(GameData.ATAQUE);
        TurnResult defenderResult = defender.play(GameData.DEFESA);

        if (attackerResult.getType().equals(defenderResult.getType())) {
            // If of the same type, the attacker wins if it has more value
            if (attackerResult.getValue() > defenderResult.getValue()) {
                defender.subtractEnergy(attackerResult.getValue() - defenderResult.getValue());
                GameData.descriptionText.setText(attacker.getName() + " atacou! ("
                        + (attackerResult.getValue() - defenderResult.getValue()) + ")");
            } else {
                GameData.descriptionText.setText(defender.getName() + " se defendeu!");
            }
        } else {
            // If of different types, the attacker wins a third of the value
            defender.subtractEnergy(attackerResult.getValue() / 3);

            GameData.descriptionText.setText(attacker.getName() + " atacou! (" + (attackerResult.getValue() / 3) + ")");
        }

        waitTime(GameData.DEFAULT_DELAY);

        for (Stat stat : attacker.getStats()) {
            stat.setButtonHighlighted(false);
        }

        for (Stat stat : defender.getStats()) {
            stat.setButtonHighlighted(false);
        }

        // If the defender has no energy, the attacker wins
        if (defender.getEnergyValue() <= 0) {
            GameData.descriptionText
                    .setText(defender.getName() + " foi eliminado! " + attacker.getName() + " é o vencedor!");

            GameData.descriptionText.setPosition(GameData.SIZE.x / 2, GameData.SIZE.y / 2);

            attacker.setState(GameData.GANHADOR);
            defender.setState(GameData.PERDEDOR);

            GameData.state = GameState.ENDING;

            waitTime(GameData.LONG_DELAY);

            GameData.descriptionText.setText("Obrigado por Jogar!!!");
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
