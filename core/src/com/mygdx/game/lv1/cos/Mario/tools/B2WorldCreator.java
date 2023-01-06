package com.mygdx.game.lv1.cos.Mario.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Screens.PlayScreen;
import com.mygdx.game.lv1.cos.Mario.Sprites.Brick;
import com.mygdx.game.lv1.cos.Mario.Sprites.Coin;
import com.mygdx.game.lv1.cos.Mario.Sprites.EndGame;
import com.mygdx.game.lv1.cos.Mario.Sprites.Sugar;



public class B2WorldCreator {
    private Array<Sugar> sugars;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
//Poniższe pętle rozpoznają obiekty z title mapy nastawione sa na wyszukiwanie prostokątów.


        //to jest podłoga
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mario.PPM, (rect.getY() + rect.getHeight() / 2) / Mario.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Mario.PPM, rect.getHeight() / 2 / Mario.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //to są rury
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mario.PPM, (rect.getY() + rect.getHeight() / 2) / Mario.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Mario.PPM, rect.getHeight() / 2 / Mario.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Mario.OBJECT_BIT;
            body.createFixture(fdef);
        }
        // to beda jabłka/coiny
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            new Coin(screen, object);
        }
        // to sa boxy
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            new Brick(screen, object);
        }
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {

            new EndGame(screen, object);
        }

        //donaty
        sugars = new Array<Sugar>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            sugars.add(new Sugar(screen, rect.getX() / Mario.PPM, rect.getY() / Mario.PPM));
        }
    }

    public Array<Sugar> getSugars() {
        return sugars;
    }
}

