package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import sprite.Sprite;
import sprite.SpriteLoad;

public abstract class Entity {
	public double x; // x of unit
	public double y; // y of unit
	public Sprite sprite; // unit that represents this unit
	public double mx; // x movement
	public double my; // y movement
	private Rectangle me = new Rectangle(); // collision zone this unit
	private Rectangle him = new Rectangle(); // collision zone other unit
	
	public Entity(String ref,int x,int y) { // contruct unit on location and image
		this.sprite = SpriteLoad.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}
	

	public void move(long delta) { // move unit based on time
		x += (delta * mx) / 1000;  // update location (ms)
		y += (delta * my) / 1000;
	}
	
	
	public void setHorizontalMovement(double mx) { // set x speed
		this.mx = mx;
	}

	
	public void setVerticalMovement(double my) { // set y speed
		this.my = my;
	}
	

	public double getHorizontalMovement() { // get x speed
		return mx;
	}


	public double getVerticalMovement() { // get y speed
		return my;
	}
	

	public void draw(Graphics g) { // draw sprite at x y
		sprite.draw(g,(int) x,(int) y);
	}
	
	
	public void doAction() { // trigger action
	}
	

	public int getX() { // get x location
		return (int) x;
	}

	
	public int getY() { // get y location
		return (int) y;
	}
	
	
	public boolean collidesWith(Entity other) { // if collide, collision bounds
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());

		return me.intersects(him);
	}
	
	
	public abstract void collidedWith(Entity other); // notify collision
}

// most of the code from this class
// https://www.youtube.com/watch?v=0RSqbDKbnXc&list=PL8MfEpjBKpQTG7WZMWM0FA9dWjHc40dxU