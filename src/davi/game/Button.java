package davi.game;

import processing.core.PConstants;
import processing.core.PVector;

public class Button extends GameObject implements Clickable {
    private int color = 0;
    private String text = "";

    private boolean highlighted = false;
    private boolean visible = true;

    public Button(String text) {
        color = GameData.BUTTON_COLOR;
        this.text = text;
    }

    public Button(PVector position, PVector size, int color, String text) {
        this.position = position;
        this.size = size;
        this.color = color;
        this.text = text;
    }

    public boolean isInside(float x, float y) {
        return visible && x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void draw(GameRenderer gd) {
        // Skip if no visible
        if (!visible) {
            return;
        }

        // Draws a solid rectangle
        gd.fill(color);
        gd.rect(position.x, position.y, size.x, size.y);

        // Add text to it
        gd.textAlign(PConstants.CENTER, PConstants.CENTER);
        gd.textSize(GameData.BUTTON_TEXT_SIZE);
        gd.fill(GameData.BUTTON_TEXT_COLOR);
        gd.text(text, position.x + size.x / 2, position.y + size.y / 2);

        // If highlighted, draw a big border
        if (highlighted) {
            gd.stroke(0);
            gd.strokeWeight(5);
            gd.noFill();
            gd.rect(position.x, position.y, size.x, size.y);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getText() {
        return text;
    }
}
