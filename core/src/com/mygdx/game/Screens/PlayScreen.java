package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Mario;
import com.mygdx.game.Sceenes.Hud;

public class PlayScreen implements Screen {
    private Hud hud;
    private Mario game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(Mario game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Mario.v_width,Mario.v_high,gamecam);
        gamePort.apply();
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }
    public void handleInput(float dt){
        if(Gdx.input.isTouched())
            gamecam.position.x += 100*dt;
    }
    public void update (float dt){
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        renderer.render();
      game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
      hud.stage.draw();

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
