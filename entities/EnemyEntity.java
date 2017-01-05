package entities;

import game.Game;

public class EnemyEntity extends Entity {
	
	private Game game; // exists in phase game
	private double enemySpeed = 145; // alien speed
	
	public EnemyEntity(Game game,String ref,int x,int y) {
		super(ref,x,y); // alien location
		mx = -enemySpeed;
		this.game = game;

	}

	public void move(long regular) { 
		if ((mx < 0) & (x < 10)) { // if reach left side change direction
			game.updateAction();
		}
		if ((mx > 0) & (x > 750)) { // if reach right side change direction
			game.updateAction();
		}
		super.move(regular); // move normal
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