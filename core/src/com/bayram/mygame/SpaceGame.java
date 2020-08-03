package com.bayram.mygame;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;



import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.bayram.mygame.screens.MainMenuScreen;
import com.bayram.mygame.tools.ScrollingBackround;


public class SpaceGame extends Game {


    private SpriteBatch batch;
    private ScrollingBackround backround;

    @Override
    public void create() {
        backround=new ScrollingBackround();
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));

    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void resize(int width, int height) {
        backround.resize(width,height);
        super.resize(width, height);
    }

    public ScrollingBackround getBackround() {
        return backround;
    }
}
