package davi.game;

import processing.core.PImage;

public abstract class Player extends Entity implements Clickable {
    protected PImage image;
    protected String name;
    
    protected Stat ataqueComum;
    protected Stat ataqueEspecial;
    protected Stat defesaComum;
    protected Stat defesaEspecial;
    protected Stat energia;
    
    protected String state = GameData.NORMAL;
    protected Stat[] stats = new Stat[5];

    public abstract TurnResult play(String turnType);

    @Override
    public boolean isInside(float x, float y) {
        return x >= position.x && x <= position.x + size.x && y >= position.y && y <= position.y + size.y;
    }

    protected Player(float x, float y, float width, float height, String name) {
        this.position.x = x;
        this.position.y = y;
        this.size.x = width;
        this.size.y = height;
        this.name = name;
    }

    public void draw(GameRenderer gr) {
        // Draws the outline of the player
        gr.strokeWeight(3);
        gr.stroke(0);
        gr.noFill();
        gr.rect(this.position.x, this.position.y, this.size.x, this.size.y);
    }

    public Stat[] getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public float getEnergyValue() {
        return energia.getValue();
    }

    public void setEnergyValue(float energy) {
        energia.setValue(energy);
    }

    public void subtractEnergy(float value) {
        this.energia.subtractValue(value);
    }

    public void waitTime(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
