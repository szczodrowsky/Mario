package com.mygdx.game.lv1.cos.Mario.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Screens.PlayScreen;
import com.mygdx.game.lv1.cos.Mario.Sprites.CMario;
import com.mygdx.game.lv1.cos.Mario.sceenes.Hud;

public class Apple extends Item{

    public Apple(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("apple"),0,0,16,16);
        velocity = new Vector2(0,0);
    }



    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ Mario.PPM);
        fdef.filter.categoryBits = Mario.ITEM_BIT;
        fdef.filter.maskBits = Mario.MARIO_BIT|
                Mario.OBJECT_BIT|
                Mario.GROUND_BIT|
                Mario.COIN_BIT|
                Mario.BRICK_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }
    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        body.setLinearVelocity(velocity);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }

    @Override
    public void use(CMario mario) {
        destroy();
        Hud.addScore(50);
    }
}

