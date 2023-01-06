package com.mygdx.game.lv1.cos.Mario.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Mario;

public class Menu implements Screen {
    private Mario game;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture playButtonInactive;
    private  Texture playButtonActive;
    private Texture logo;

    public static final int exbWidhth = 60;
    public static final int exbHigh = 60;
    public static final int exbY = 20;
    public static final int playWidth = 60;
    public static final int playHigh = 60;
    public static final int playY = 70;
    public static final int logoW = 100;
    public static final int logoH = 100;
    public static final int logoY = 120;




    public Menu(Mario game) {
    this.game = game;

        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
        logo = new Texture("menuBack.jpg");



    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.batch.begin();
        game.batch.draw(logo,0,0,Mario.v_width,Mario.v_high);


        int x = Mario.v_width/2 - exbWidhth/2;
       if(Gdx.input.getX()<x + exbWidhth && Gdx.input.getX() >x && Mario.v_high - Gdx.input.getY() < exbY+exbHigh && Mario.v_high - Gdx.input.getY() > exbY) {
            game.batch.draw(exitButtonActive,x,exbY,exbWidhth,exbHigh);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        else{
            game.batch.draw(exitButtonInactive,x,exbY,exbWidhth,exbHigh);
        }
         x = Mario.v_width/2 - playWidth/2;
        if(Gdx.input.getX()<x + playWidth && Gdx.input.getX() >x && Mario.v_high - Gdx.input.getY() < playY+playHigh && Mario.v_high - Gdx.input.getY() > playY) {
            game.batch.draw(playButtonActive,x,playY,exbWidhth,exbHigh);
            if(Gdx.input.isTouched()){
                game.setScreen(new PlayScreen(game));
            }
        }
        else{
            game.batch.draw(playButtonInactive,x,playY,playWidth,playHigh);
        }

        x = Mario.v_width/2 - logoW/2;


        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
