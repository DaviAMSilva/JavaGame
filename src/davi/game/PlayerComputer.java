package davi.game;

public class PlayerComputer extends Player {
    public PlayerComputer(float x, float y, float width, float height, String name) {
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
        GameData.descriptionText.setText("Próximo a jogar: " + name);
        waitTime(1000);

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

        ataqueComum.setButtonHighlighted(false);
        ataqueEspecial.setButtonHighlighted(false);
        defesaComum.setButtonHighlighted(false);
        defesaEspecial.setButtonHighlighted(false);

        String type = GameData.random.nextBoolean() ? GameData.COMUM : GameData.ESPECIAL;
        float value = 0;

        Stat choice = null;

        if (turnType.equals(GameData.ATAQUE)) {
            if (GameData.random.nextBoolean()) {
                // Choosing randomly
                if (GameData.random.nextBoolean()) {
                    choice = this.ataqueComum;
                } else {
                    choice = this.ataqueEspecial;
                }
            } else {
                // Choosing the attack with the highest value
                if (this.ataqueComum.getValue() > this.ataqueEspecial.getValue()) {
                    choice = this.ataqueComum;
                } else {
                    choice = this.ataqueEspecial;
                }
            }
        } else if (turnType.equals(GameData.DEFESA)) {
            if (GameData.random.nextBoolean()) {
                // Choosing randomly
                if (GameData.random.nextBoolean()) {
                    choice = this.defesaComum;
                } else {
                    choice = this.defesaEspecial;
                }
            } else {
                // Choosing the attack with the highest value
                if (this.defesaComum.getValue() > this.defesaEspecial.getValue()) {
                    choice = this.defesaComum;
                } else {
                    choice = this.defesaEspecial;
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid type: " + turnType);
        }

        choice.setButtonHighlighted(true);
        value = choice.getValue();

        GameData.descriptionText.setText(name + " escolheu: " + turnType + " " + type + " (" + value + ")");
        waitTime(1000);

        // For every stat decrease the value except for the one chosen
        for (Stat stat : stats) {
            if (stat == choice) {
                stat.subtractValue(10);
            } else if (stat != energia) {
                stat.addValue(5);
            }
        }

        return new TurnResult(type, value);
    }

    @Override
    public void draw(GameRenderer gr) {
        if (state == GameData.GANHADOR) {
            gr.tint(GameData.WINNER_TINT_COLOR);
        } else if (state == GameData.PERDEDOR) {
            gr.tint(GameData.LOSER_TINT_COLOR);
        }

        gr.image(GameData.computerPlayerImage, position.x, position.y, size.x, size.y);

        super.draw(gr);

        gr.noTint();
    }
}
