package com.rotatingcanvasgames.box2d;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by admin on 5/18/2019.
 */

public class RotateBodyExample {
    World world;
    final Vector2 gravity=new Vector2(0,-1f);
    float accumulator;


    Body ground;
    Body pivotBody;
    Body rotatingBody;

    float radius;
    float rotateVelocity;
    boolean updateBodyPositionForFirstTime;
    Vector2 oldMainBodyPosition;
    Vector2 currentMainBodyPosition;

    public RotateBodyExample(){
        world=new World(gravity,true);
        accumulator=0;
        radius=100;
        rotateVelocity=2;
        oldMainBodyPosition=new Vector2();
        currentMainBodyPosition=new Vector2();
        updateBodyPositionForFirstTime=false;
        AddGround();
        AddBody1();
        AddRotatingBody();

    }

    public World GetWorld(){
        return world;
    }

    public void AddGround(){
        ground=BoxHelper.GetBody(world, BodyDef.BodyType.StaticBody,360,64);
        BoxHelper.AddRectShape(1,1f,1,320,32,ground);
    }

    public void AddBody1(){
        pivotBody =BoxHelper.GetBody(world, BodyDef.BodyType.DynamicBody,360,240);
        BoxHelper.AddCircleShape(1,1f,1,8,false, pivotBody);
    }

    public void AddRotatingBody(){
        rotatingBody=BoxHelper.GetBody(world, BodyDef.BodyType.KinematicBody,360,240+radius);
        rotatingBody.setGravityScale(0);
        rotatingBody.setLinearDamping(0);
        BoxHelper.AddCircleShape(1,1,1,8,true,rotatingBody);

    }

    public void Update(float dt,boolean isPaused){
        accumulator+=dt;
        while(accumulator>=dt){
            world.step(BoxConstants.BOX_STEP,BoxConstants.VELOCITY_ITERATION, BoxConstants.POSITION_ITERATION);
            accumulator-=BoxConstants.BOX_STEP;
        }

        //Update Velocity for rotating body
        UpdateRotatingBodyVelocity(isPaused);

    }

    public void UpdateRotatingBodyVelocity(boolean isPaused){
        if(pivotBody !=null && rotatingBody!=null) {

                Vector2 centripetalVector=new Vector2();
                centripetalVector.set(rotatingBody.getPosition()).sub(pivotBody.getPosition()).nor();

                float dst=rotatingBody.getPosition().dst(pivotBody.getPosition());
                float delta=dst-BoxHelper.ConvertToBoxCoordinate(radius) ;
                float c2=delta/(BoxConstants.BOX_STEP);
                centripetalVector.scl(-1f*c2);

                //CURRENT BASE FROM ORIGIN
                Vector2 temp = new Vector2().set(rotatingBody.getPosition()).sub(pivotBody.getPosition()).nor();
                Vector2 temp2 = new Vector2().set(temp);
                //this angle should be always 90
                float newAngle = -90;
                temp2.rotate(newAngle );
                temp2.nor();

                Vector2 temp3 = new Vector2();
                //SET ROTATING BODY CIRCULAR VELOCITY
                temp3.set(temp2.x * rotateVelocity, temp2.y * rotateVelocity);
                //ADD CENTRIPETAL VELOCITY
                temp3.add(centripetalVector);
                //ADD PIVOT BODY VELOCITY
                temp3.add(pivotBody.getLinearVelocity());
                rotatingBody.setLinearVelocity(temp3);

        }
    }

    public void DestoryAll(){
        Array<Body> bodies=new Array<Body>();
        world.getBodies(bodies);
        for(int i=0;i<bodies.size;i++){
            world.destroyBody(bodies.get(i));
        }
    }
}
