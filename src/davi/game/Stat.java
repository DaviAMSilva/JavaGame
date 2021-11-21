package davi.game;

public class Stat {
    private float value = 0;
    private float maxValue = GameData.MAX_STAT_VALUE;

    private Bar bar;
    private Button button;

    public void draw(GameRenderer gr) {
        bar.setPercentage(value / maxValue);
        bar.draw(gr);

        if (button != null) {
            button.draw(gr);
        }
    }

    public Stat(float value, float maxValue, String text) {
        this.value = value;
        this.maxValue = maxValue;
        this.bar = new Bar(text);
        this.button = new Button(text);
    }

    public void setBarDimensions(float x, float y, float width, float height) {
        bar.setPosition(x, y);
        bar.setSize(width, height);
    }

    public void setButtonDimensions(float x, float y, float width, float height) {
        if (button != null) {
            button.setPosition(x, y);
            button.setSize(width, height);
        }
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setButtonHighlighted(boolean highlighted) {
        if (button != null) {
            button.setHighlighted(highlighted);
        }
    }

    public boolean getButtonHighlighted() {
        if (button != null) {
            return button.isHighlighted();
        }

        return false;
    }

    public boolean isButtonInside(float x, float y) {
        if (button != null) {
            return button.isInside(x, y);
        }

        return false;
    }

    public void setButtonVisible(boolean visible) {
        if (button != null) {
            button.setVisible(visible);
        }
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void addValue(float value) {
        this.value += value;

        if (this.value > maxValue) {
            this.value = maxValue;
        }
    }

    public void subtractValue(float value) {
        this.value -= value;

        if (this.value < 0) {
            this.value = 0;
        }
    }
}
