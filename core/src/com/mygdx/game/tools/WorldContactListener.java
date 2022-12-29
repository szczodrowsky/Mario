package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Mario;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTitleObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits |fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;
            if (object.getUserData() != null && InteractiveTitleObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTitleObject) object.getUserData()).onHeadHit();
            }
        }
       switch (cDef){
           case Mario.ENEMY_HEAD_BIT | Mario.MARIO_BIT:
               if(fixA.getFilterData().categoryBits == Mario.ENEMY_HEAD_BIT)
                   ((Enemy)fixA.getUserData()).hitOnHead();
               else
                   ((Enemy)fixB.getUserData()).hitOnHead();
               break;
           case Mario.ENEMY_BIT | Mario.OBJECT_BIT:
               if(fixA.getFilterData().categoryBits == Mario.ENEMY_BIT)
                   ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
               else
                   ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
               break;
           case Mario.MARIO_BIT | Mario.ENEMY_BIT:
               Gdx.app.log("MARIO", "DIED");
               break;
           case Mario.ENEMY_BIT | Mario.ENEMY_BIT:
               ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
               ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
               break;
       }
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
