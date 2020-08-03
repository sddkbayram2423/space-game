package com.bayram.mygame.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackround {

    public static final int DEFAULT_SPEDD=80;
    private static final int ACCLELERATION=50;
    private static final int GOAL_REACH_ACCLELERATION=200;
    private Texture image;
    private int speed;
    private int goalSpeed;
    float imageScale;
    private boolean speedFixed;

    float y1,y2;

    public ScrollingBackround() {

        speedFixed=true;
        image=new Texture("space_backround.png");
        y1=0;
        y2=image.getHeight();
        goalSpeed=DEFAULT_SPEDD;
        imageScale=0;

    }

    public void updateAndRender(float deltaTime, SpriteBatch batch){
        //Speed adjustment to reach speed
        if(speed<goalSpeed){
            speed+=GOAL_REACH_ACCLELERATION*deltaTime;
            if(speed>goalSpeed){
                speed=goalSpeed;
            }
        }else if(speed>goalSpeed){
            speed-=GOAL_REACH_ACCLELERATION*deltaTime;
            if(speed<goalSpeed){
                speed=goalSpeed;
            }
        }
        if(!speedFixed){
            speed+=ACCLELERATION*deltaTime;

        }
        y1-=speed*deltaTime;
        y2-=speed*deltaTime;


        //if image reached top of the screen and is not visible ,put it back on top
        if(y1+image.getHeight()*imageScale<=0){
            y1=y2+image.getHeight()*imageScale;
        }
        if(y2+image.getHeight()*imageScale<=0){
            y2=y1+image.getHeight()*imageScale;
        }

        batch.draw(image,0,y1, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*imageScale);
        batch.draw(image,0,y2, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*imageScale);
    }

    public void resize(int width, int height) {

        imageScale=width/image.getWidth();

    }

    public void setSpeed(int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFixed(boolean speedFixed) {
        this.speedFixed = speedFixed;
    }
}
