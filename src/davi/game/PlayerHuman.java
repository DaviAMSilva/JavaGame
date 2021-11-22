package davi.game;

public class PlayerHuman extends Player {

    public PlayerHuman(float x, float y, float width, float height, String name) {
        super(x, y, width, height, name);

        this.stats[0] = this.ataqueComum = new Stat(25.0f + GameData.random.nextInt(50), GameData.MAX_STAT_VALUE,
                "Ataque Comum");
        this.stats[1] = this.ataqueEspecial = new Stat(25.0f + GameData.random.nextInt(50), GameData.MAX_STAT_VALUE,
                "Ataque Especial");
        this.stats[2] = this.defesaComum = new Stat(25.0f + GameData.random.nextInt(50), GameData.MAX_STAT_VALUE,
                "Defesa Comum");
        this.stats[3] = this.defesaEspecial = new Stat(25.0f + GameData.random.nextInt(50), GameData.MAX_STAT_VALUE,
                "Defesa Especial");
        this.stats[4] = this.energia = new Stat(GameData.MAX_STAT_VALUE, GameData.MAX_STAT_VALUE, "Energia");

        // We don't need the energy button
        this.energia.setButton(null);
    }

    @Override
    public synchronized TurnResult play(String turnType) {
        // Update the description
        GameData.descriptionText.setText("Próximo a jogar: " + name);
        waitTime(GameData.DEFAULT_DELAY);
        GameData.descriptionText.setText("Escolha sua jogada:");

        // Setting the visibility of the buttons based on the type of turn
        if (turnType.equals(GameData.ATAQUE)) {
            this.defesaComum.setButtonVisible(false);
            this.defesaEspecial.setButtonVisible(false);
            this.ataqueComum.setButtonVisible(true);
            this.ataqueEspecial.setButtonVisible(true);
        } else if (turnType.equals(GameData.DEFESA)) {
            this.ataqueComum.setButtonVisible(false);
            this.ataqueEspecial.setButtonVisible(false);
            this.defesaComum.setButtonVisible(true);
            this.defesaEspecial.setButtonVisible(true);
        } else {
            throw new IllegalArgumentException("Invalid turn type: " + turnType);
        }

        // Wait for the player to choose a button of a stat and return it
        Stat choice = getClickedChoice();

        // Decide what type/value the player chose
        String type = "";
        float value = choice.getValue();

        if (choice == this.ataqueComum || choice == this.defesaComum)
            type = GameData.COMUM;
        else if (choice == this.ataqueEspecial || choice == this.defesaEspecial)
            type = GameData.ESPECIAL;
        else
            throw new IllegalArgumentException("Invalid type: " + type);

        GameData.descriptionText.setText(name + " escolheu: " + turnType + " " + type + " (" + value + ")");
        waitTime(GameData.DEFAULT_DELAY);

        // For every stat decrease the value except for the one chosen
        for (Stat stat : stats) {
            if (stat == choice) {
                stat.subtractValue(10);
            } else if (stat != energia) {
                stat.addValue(5);
            }
        }

        // Return the type/value to the game
        return new TurnResult(type, value);
    }

    // Wait for the player to click on a stat and return it
    private Stat getClickedChoice() {
        Stat choice = null;

        while (choice == null) {
            for (Stat stat : this.stats) {
                // Test if the mouse is over the button
                synchronized (GameData.MOUSE_POSITION) {
                    if (stat.isButtonInside(GameData.MOUSE_POSITION.x, GameData.MOUSE_POSITION.y)) {
                        stat.setButtonHighlighted(true);

                        // Test if the mouse is pressed
                        synchronized (GameData.MOUSE_PRESSED) {
                            if (Boolean.TRUE.equals(GameData.MOUSE_PRESSED)) {
                                choice = stat;
                                break;
                            }
                        }
                    } else {
                        stat.setButtonHighlighted(false);
                    }
                }
            }
        }

        return choice;
    }

    @Override
    public void draw(GameRenderer gr) {
        if (state == GameData.GANHADOR) {
            gr.tint(GameData.WINNER_TINT_COLOR);
        } else if (state == GameData.PERDEDOR) {
            gr.tint(GameData.LOSER_TINT_COLOR);
        }

        gr.image(GameData.humanPlayerImage, position.x, position.y, size.x, size.y);

        super.draw(gr);

        gr.noTint();
    }
}
