package com.mygdx.game.lv1.cos.Mario.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Mario;
import com.mygdx.game.lv1.cos.Mario.Sprites.CMario;
import com.mygdx.game.lv1.cos.Mario.Sprites.Enemy;
import com.mygdx.game.lv1.cos.Mario.Sprites.InteractiveTitleObject;
import com.mygdx.game.lv1.cos.Mario.Sprites.Items.Item;


//Zbiór interakcji pomiędzy bitami dwóch różnych obiektów oraz następstwa jakie wynikają z tych kolizji.

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits |fixB.getFilterData().categoryBits;


        switch (cDef){
            case Mario.MARIO_HEAD_BIT| Mario.END_GAME_BIT:
            case Mario.MARIO_HEAD_BIT| Mario.BRICK_BIT:
            case Mario.MARIO_HEAD_BIT | Mario.COIN_BIT:
                if(fixA.getFilterData().categoryBits == Mario.MARIO_HEAD_BIT){
                    ((InteractiveTitleObject) fixB.getUserData()).onHeadHit((CMario) fixA.getUserData());
                }
                else
                    ((InteractiveTitleObject) fixA.getUserData()).onHeadHit((CMario) fixA.getUserData());
                break;
            case Mario.ENEMY_BIT | Mario.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Mario.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Mario.ENEMY_HEAD_BIT | Mario.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == Mario.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((CMario) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((CMario) fixA.getUserData());
                break;
             // to ta szalona
                /*case Mario.MARIO_BIT | Mario.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Mario.MARIO_BIT)
                    ((CMario) fixB.getUserData()).hit((Sugar)fixB.getUserData());
                else
                    ((CMario) fixA.getUserData()).hit((Enemy)fixA.getUserData());
                break;  */
         /*   case Mario.MARIO_BIT | Mario.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Mario.MARIO_HEAD_BIT) {
                    ((CMario) fixA.getUserData()).hit();

                }
                else
                ((CMario)fixB.getUserData()).hit();
                break; */
            case Mario.ENEMY_BIT | Mario.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Mario.ITEM_BIT | Mario.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Mario.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Mario.ITEM_BIT | Mario.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == Mario.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((CMario) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((CMario) fixA.getUserData());
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