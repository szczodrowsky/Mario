package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Mario;
import com.mygdx.game.sceenes.Hud;
import com.mygdx.game.Sprites.CMario;

public class PlayScreen implements Screen {
    private Hud hud;
    private Mario game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private CMario player;


    public PlayScreen(Mario game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Mario.v_width/Mario.PPM, Mario.v_high/Mario.PPM,gamecam);
        gamePort.apply();
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("1-1.tmx");
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0,-10),true);
        player = new CMario(world); // initialization of  Mario class object
        b2dr = new Box2DDebugRenderer();
        renderer = new OrthogonalTiledMapRenderer(map,1/Mario.PPM);



        BodyDef bdf = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //to jest podłoga
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdf.type = BodyDef.BodyType.StaticBody;
            bdf.position.set((rect.getX()+rect.getWidth()/2)/Mario.PPM, (rect.getY() +rect.getHeight()/2)/Mario.PPM);

            body = world.createBody(bdf);

            shape.setAsBox((rect.getWidth()/2)/Mario.PPM ,(rect.getHeight()/2)/Mario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //to są rury
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdf.type = BodyDef.BodyType.StaticBody;
            bdf.position.set((rect.getX()+rect.getWidth()/2)/Mario.PPM, (rect.getY() +rect.getHeight()/2)/Mario.PPM);

            body = world.createBody(bdf);

            shape.setAsBox((rect.getWidth()/2)/Mario.PPM ,(rect.getHeight()/2)/Mario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // to beda monety/melko
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdf.type = BodyDef.BodyType.StaticBody;
            bdf.position.set((rect.getX()+rect.getWidth()/2)/Mario.PPM, (rect.getY() +rect.getHeight()/2)/Mario.PPM);

            body = world.createBody(bdf);

            shape.setAsBox((rect.getWidth()/2)/Mario.PPM ,(rect.getHeight()/2)/Mario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
            // to sa boxy
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdf.type = BodyDef.BodyType.StaticBody;
            bdf.position.set((rect.getX()+rect.getWidth()/2)/Mario.PPM, (rect.getY() +rect.getHeight()/2)/Mario.PPM);

            body = world.createBody(bdf);

            shape.setAsBox((rect.getWidth()/2)/Mario.PPM ,(rect.getHeight()/2)/Mario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }
    public void handleInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update (float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam );

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
        b2dr.render(world,gamecam.combined);
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
