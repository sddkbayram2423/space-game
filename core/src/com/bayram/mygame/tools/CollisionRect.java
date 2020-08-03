package com.bayram.mygame.tools;

public class CollisionRect {

    private float x, y;
    private float widht, heihgt;

    public CollisionRect(float x, float y, float widht, float heihgt) {
        this.x = x;
        this.y = y;
        this.widht = widht;
        this.heihgt = heihgt;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public boolean collidesWith(CollisionRect rect) {

        return x < (rect.getX() + rect.getWidht())
                &&
                y < (rect.getY() + rect.getHeihgt())
                &&
                (widht + x) > rect.getX()
                &&
                (y + heihgt) > rect.getY();

    }

    //set/get

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidht() {
        return widht;
    }

    public float getHeihgt() {
        return heihgt;
    }
}
