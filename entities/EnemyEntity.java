package entities;

import game.Main;

public class EnemyEntity extends Entity {
	
	private Main game; // exists in phase game
	private double enemySpeed = 145; // alien speed
	
	public EnemyEntity(Main game,String ref,int x,int y) {
		super(ref,x,y); // alien location
		mx = -enemySpeed;
		this.game = game;

	}

	public void move(long delta) { 
		if ((mx < 0) && (x < 15)) { // if reach left side change direction
			game.updateAction();
		}
		if ((mx > 0) && (x > 745)) { // if reach right side change direction
			game.updateAction();
		}
		super.move(delta); // move normal
	}
	
	public void doAction() { // when reach either side move down and change
		mx = -mx;			 // direction 
		y += 15;
		
		if (y > 580) { // when reach end of screen end game
			game.notifyLose();
		}
	}
	
	public void collidedWith(Entity other) { // when collide with player
	}
}