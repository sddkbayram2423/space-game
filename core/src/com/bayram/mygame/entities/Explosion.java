package com.bayram.mygame.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {

    public static final float FRAME_LENGHT = 0.2f;
    public static final int OFFSET = 8;
    public static final int SIZE = 72;
    public static final int IMAGE_SIZE = 32;
    private static Texture explosionImage;

    private boolean remove = false;
    private Animation anim=null;
    float stateTime;
    float x, y;


    public Explosion(float x, float y) {
        this.x = x-OFFSET;
        this.y = y-OFFSET;
        stateTime=0;
        if (explosionImage == null) {
            explosionImage = new Texture("explosion.png");
        }
        if(anim==null){

            anim=new Animation(FRAME_LENGHT, TextureRegion.split(explosionImage,IMAGE_SIZE, IMAGE_SIZE)[0]);
        }

    }

    public void update(float delta) {
        stateTime += delta;
        if (anim.isAnimationFinished(stateTime)) {
            remove = true;
        }

    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw((TextureRegion) anim .getKeyFrame(stateTime),x,y, SIZE, SIZE);

    }

    public boolean isRemove() {
        return remove;
    }
}
