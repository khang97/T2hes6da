package entities;

import game.Main;


public class PlayerEntity extends Entity {
	private Main game; // ship in phase
	
	public PlayerEntity(Main game,String ref,int x,int y) { // create unit for ship
		super(ref,x,y);
		
		this.game = game;
	}
	

	public void move(long delta) { // move based on time
		if ((mx < 0) && (x < 20)) { // dont move when end of screen
			return;
		}
	
		if ((mx > 0) && (x > 760)) { // dont move when end of screen
			return;
		}
		
		super.move(delta);
	}
	

	public void collidedWith(Entity other) { // collision
		if (other instanceof EnemyEntity) { // if enemy kill player
			game.notifyLose(); // notify game
		}
	}
}