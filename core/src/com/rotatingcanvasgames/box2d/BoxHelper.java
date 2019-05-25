package com.rotatingcanvasgames.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by admin on 5/18/2019.
 */

public class BoxHelper {

    public static Body GetBody(World world, BodyDef.BodyType type,float px,float py){
        BodyDef bd=new BodyDef();
        bd.type=type;
        float bx=ConvertToBoxCoordinate(px);
        float by=ConvertToBoxCoordinate(py);
        bd.position.set(bx,by);
        bd.angle=0;
        Body body=world.createBody(bd);

        return body;
    }

    public static void AddCircleShape(float density,float restitution,float friction,float radius,boolean isSensor, Body body){
        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=density;
        fixtureDef.restitution=restitution;
        fixtureDef.friction=friction;
        fixtureDef.shape=new CircleShape();
        fixtureDef.shape.setRadius(ConvertToBoxCoordinate(radius));
        fixtureDef.isSensor=isSensor;
        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();
    }

    public static void AddRectShape(float density,float restitution,float friction,float width,float height,Body body){

        float w=ConvertToBoxCoordinate(width/2f);
        float h=ConvertToBoxCoordinate(height/2f);
        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(w,h);

        FixtureDef fixtureDef=new FixtureDef();
        fixtureDef.density=density;
        fixtureDef.restitution=restitution;
        fixtureDef.friction=friction;
        fixtureDef.shape=bodyShape;

        body.createFixture(fixtureDef);
        bodyShape.dispose();
    }

    public static float ConvertToBoxCoordinate(float v){
        return v*BoxConstants.WORLD_TO_BOX;
    }


}
