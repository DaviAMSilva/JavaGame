package davi.game;

import static davi.game.GameData.*;

public class PlayerComputer extends Player {
    public PlayerComputer(float x, float y, float width, float height, String name) {
        super(x, y, width, height, name);

        this.stats[0] = this.ataqueComum = new Stat(25.0f + RANDOM.nextInt(50), MAX_STAT_VALUE, "Ataque Comum");
        this.stats[1] = this.ataqueEspecial = new Stat(25.0f + RANDOM.nextInt(50), MAX_STAT_VALUE, "Ataque Especial");
        this.stats[2] = this.defesaComum = new Stat(25.0f + RANDOM.nextInt(50), MAX_STAT_VALUE, "Defesa Comum");
        this.stats[3] = this.defesaEspecial = new Stat(25.0f + RANDOM.nextInt(50), MAX_STAT_VALUE, "Defesa Especial");
        this.stats[4] = this.energia = new Stat(MAX_STAT_VALUE, MAX_STAT_VALUE, "Energia");

        // We don't need the energy button
        this.energia.setButton(null);
    }

    @Override
    public synchronized TurnResult play(String turnType) {
        gd_descriptionText.setText("Próximo a jogar: " + name);
        waitTime(DEFAULT_DELAY);

        // Setting the visibility of the buttons based on the type of turn
        if (turnType.equals(ATAQUE)) {
            this.defesaComum.setButtonVisible(false);
            this.defesaEspecial.setButtonVisible(false);
            this.ataqueComum.setButtonVisible(true);
            this.ataqueEspecial.setButtonVisible(true);
        } else if (turnType.equals(DEFESA)) {
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

        String type = "";
        float value = 0;

        Stat choice = null;

        if (turnType.equals(ATAQUE)) {
            if (RANDOM.nextBoolean()) {
                // Choosing randomly
                if (RANDOM.nextBoolean()) {
                    choice = this.ataqueComum;
                    type = COMUM;
                } else {
                    choice = this.ataqueEspecial;
                    type = ESPECIAL;
                }
            } else {
                // Choosing the attack with the highest value
                if (this.ataqueComum.getValue() > this.ataqueEspecial.getValue()) {
                    choice = this.ataqueComum;
                    type = COMUM;
                } else {
                    choice = this.ataqueEspecial;
                    type = ESPECIAL;
                }
            }
        } else if (turnType.equals(DEFESA)) {
            if (RANDOM.nextBoolean()) {
                // Choosing randomly
                if (RANDOM.nextBoolean()) {
                    choice = this.defesaComum;
                    type = COMUM;
                } else {
                    choice = this.defesaEspecial;
                    type = ESPECIAL;
                }
            } else {
                // Choosing the attack with the highest value
                if (this.defesaComum.getValue() > this.defesaEspecial.getValue()) {
                    choice = this.defesaComum;
                    type = COMUM;
                } else {
                    choice = this.defesaEspecial;
                    type = ESPECIAL;
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid type: " + turnType);
        }

        choice.setButtonHighlighted(true);
        value = choice.getValue();

        gd_descriptionText.setText(name + " escolheu: " + turnType + " " + type + " (" + value + ")");
        waitTime(DEFAULT_DELAY);

        // For every stat decrease the value except for the one chosen
        for (Stat stat : stats) {
            if (stat == choice) {
                stat.subtractValue(10);
            } else if (stat != energia) {
                stat.addValue(5);
            }
        }

        // Remove energy
        if (type.equals(ESPECIAL)) {
            energia.subtractValue(5);
        }

        return new TurnResult(type, value);
    }

    @Override
    public void draw(GameRenderer gr) {
        if (state.equals(GANHADOR)) {
            gr.tint(WINNER_TINT_COLOR);
        } else if (state.equals(PERDEDOR)) {
            gr.tint(LOSER_TINT_COLOR);
        }

        gr.image(gd_computerPlayerImage, position.x, position.y, size.x, size.y);

        super.draw(gr);

        gr.noTint();
    }
}
