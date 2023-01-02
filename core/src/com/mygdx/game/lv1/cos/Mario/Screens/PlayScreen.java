package com.mygdx.game.lv1.cos.Mario.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Sprites.EndGame;
import com.mygdx.game.lv1.cos.Mario.Sprites.Enemy;


//import com.mygdx.game.Sprites.Items.Item;
//import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.lv1.cos.Mario.Sprites.Items.Apple;
import com.mygdx.game.lv1.cos.Mario.Sprites.Items.Item;
import com.mygdx.game.lv1.cos.Mario.Sprites.Items.ItemDef;
import com.mygdx.game.lv1.cos.Mario.sceenes.Hud;
import com.mygdx.game.lv1.cos.Mario.Sprites.CMario;
import com.mygdx.game.lv1.cos.Mario.tools.B2WorldCreator;
import com.mygdx.game.lv1.cos.Mario.tools.WorldContactListener;


import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    //gra
    private Mario game;
    private TextureAtlas atlas;
    //zmienne kamery
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    //zmienne dla TiltedMapy
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //zmienne Box2D
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    //sprites
    private CMario player;
     private Array<Item> items;
     private LinkedBlockingQueue<ItemDef> itemToSpawn;




    public PlayScreen(Mario game) {
        atlas = new TextureAtlas("testowy.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Mario.v_width / Mario.PPM, Mario.v_high / Mario.PPM, gamecam);
        gamePort.apply();
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("1-1.tmx");
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        player = new CMario(this);
        b2dr = new Box2DDebugRenderer();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Mario.PPM);
        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener());
        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();


    }
    public void spawnItem(ItemDef idef){
        itemToSpawn.add(idef);
    }
    public void handleSpawningItems(){
        if(!itemToSpawn.isEmpty()){
            ItemDef idef = itemToSpawn.poll();
            if(idef.type == Apple.class){
                items.add(new Apple(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
    public void handleInput(float dt) {
        if (player.currentState != CMario.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update (float dt){
        handleInput(dt);
        handleSpawningItems();
        world.step(1/60f,6,2);
        player.update(dt);
        for (Enemy enemy: creator.getSugars())
            enemy.update(dt);
        for(Item item: items)
            item.update(dt);

        hud.upadte(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam );
        if(player.currentState !=CMario.State.DEAD){
            gamecam.position.x=player.b2body.getPosition().x;
        }

    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        //czyszczenie ekranu na czarno
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        //render naszej mapy
        renderer.render();
        //render Box2D
        b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy: creator.getSugars())
            enemy.draw(game.batch);
        for(Item item: items)
            item.draw(game.batch);
        game.batch.end();

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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();


    }
}
