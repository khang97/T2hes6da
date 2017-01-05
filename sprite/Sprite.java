package sprite;

import java.awt.Graphics;
import java.awt.Image;


public class Sprite { // image to unit
	private Image image;
	
	public Sprite(Image image) { // create new unit based on image
		this.image = image;
	}
	
	public int getHeight() { // get image height
		return image.getHeight(null);
	}
	
	public int getWidth() { // get image width
		return image.getWidth(null);
	}

	public void draw(Graphics g,int x,int y) { // draw the unit
		g.drawImage(image,x,y,null);
	}
}