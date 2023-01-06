package com.mygdx.game.lv1.cos.Mario.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Screens.PlayScreen;

import com.mygdx.game.lv1.cos.Mario.sceenes.Hud;

public class Sugar extends Enemy {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    public Sugar(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i=0;i<1;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("donat"),i*16,0,48,33));
        walkAnimation = new Animation<TextureRegion>(0.4f,frames);
        stateTime =0;
        setBounds(getX(),getY(),16/Mario.PPM,16/Mario.PPM);
        setToDestroy = false;
        destroyed= false;
    }
    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
        }
        else if(!destroyed) {
           b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }



    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/Mario.PPM);
        fdef.filter.categoryBits = Mario.ENEMY_BIT;
        fdef.filter.maskBits = Mario.GROUND_BIT |
                Mario.BRICK_BIT |
                Mario.COIN_BIT|
                Mario.ENEMY_BIT|
                Mario.OBJECT_BIT|
                Mario.MARIO_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        //gora do wykrywania
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5,8).scl(1/Mario.PPM);
        vertice[1] = new Vector2(5,8).scl(1/Mario.PPM);
        vertice[2] = new Vector2(-3,3).scl(1/Mario.PPM);
        vertice[3] = new Vector2(3,3).scl(1/Mario.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits=Mario.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void hitOnHead(CMario cmario) {
    setToDestroy = true;
        Hud.addScore(100);
    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    }


