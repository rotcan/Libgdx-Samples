package com.rotatingcanvasgames.sample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.rotatingcanvasgames.box2d.BoxConstants;
import com.rotatingcanvasgames.box2d.RotateBodyExample;

public class Main extends ApplicationAdapter implements InputProcessor {

	OrthographicCamera camera;
	SpriteBatch spritebatch;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;

	RotateBodyExample example;
	boolean isDisposed;
	boolean isPaused;

	@Override
	public void create () {
		camera=CameraHelper.GetCamera(720,480);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		isDisposed=false;

		spritebatch=new SpriteBatch() ;
		debugMatrix=new Matrix4(camera.combined);
		debugMatrix.scale(BoxConstants.BOX_TO_WORLD, BoxConstants.BOX_TO_WORLD, 1f);
		debugRenderer=new Box2DDebugRenderer();
		example=new RotateBodyExample();
		isPaused=false;
	}

	@Override
	public void render () {
		float dt=Gdx.graphics.getDeltaTime();
		Update(dt);

		Draw();
	}

	public void Draw(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();

		spritebatch.begin();
		debugRenderer.render(example.GetWorld(),debugMatrix);
		spritebatch.end();
	}

	public void Update(float dt){
		example.Update(dt,isPaused);
	}
	
	@Override
	public void dispose () {
		if(!isDisposed) {
			debugRenderer.dispose();
			spritebatch.dispose();
			example.DestoryAll();
			isDisposed=true;
		}
	}

	public void ExitApplication(){
		dispose();
		Gdx.app.exit();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode== Input.Keys.BACK || keycode== Input.Keys.SPACE){
			ExitApplication();
		}
		if(keycode==Input.Keys.P){
			isPaused=!isPaused;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
