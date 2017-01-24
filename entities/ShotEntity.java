package entities;

import game.Main;

public class ShotEntity extends Entity {
	private double shotSpeed = -350; // bullet speed
	private Main game; // exists in phase
	private boolean used = false; // used hits something
	

	public ShotEntity(Main game,String sprite,int x,int y) { // shot created, location
		super(sprite,x,y);
		
		this.game = game;
		
		my = shotSpeed;
	}


	public void move(long delta) { // move depending on time
		super.move(delta);
		
		if (y < -50) { // delete when offscreen
			game.removeEntity(this);
		}
	}
	
	
	public void collidedWith(Entity other) { // if collide with others remove
		if (used) {
			return;
		}
		
		if (other instanceof EnemyEntity) { // if hit enemy delete
			game.removeEntity(other); // remove enemy hit
			game.removeEntity(this); // remove bullet
			game.notifyAlienKilled(); // let game know enemy killed
			used = true;
		}
	}
}