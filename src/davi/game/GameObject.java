package davi.game;

import processing.core.PVector;

public abstract class GameObject {
    protected PVector position = new PVector();
    protected PVector size = new PVector();

    public PVector getPosition() {
        return position;
    }

    public PVector getSize() {
        return size;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public void setSize(float x, float y) {
        size.x = x;
        size.y = y;
    }

    public abstract void draw(GameRenderer gr);
}
