package davi.game;

import processing.core.PConstants;
import processing.core.PVector;

public class Bar extends GameObject {
    private int color = 0;
    private String text = "";
    private float percentage = 0;

    public Bar(String text) {
        color = GameData.BAR_COLOR;
        this.text = text;
    }

    public Bar(PVector position, PVector size, int color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public void draw(GameRenderer gr) {
        // Draw a full rectangle
        gr.noStroke();
        gr.fill(GameData.BAR_OFF_COLOR);
        gr.rect(position.x, position.y, size.x, size.y);

        // Draw a rectangle proportional to the percentage
        gr.fill(color);
        gr.rect(position.x, position.y, size.x * percentage, size.y);

        // Add text to it
        gr.textAlign(PConstants.CENTER, PConstants.CENTER);
        gr.textSize(GameData.BAR_TEXT_SIZE);
        gr.fill(GameData.BAR_TEXT_COLOR);
        gr.text(text + " (" + (int) (percentage * 100) + ")", position.x + size.x / 2, position.y + size.y / 2);

        // Draw a border
        gr.stroke(0);
        gr.strokeWeight(1);
        gr.noFill();
        gr.rect(position.x, position.y, size.x, size.y);
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
