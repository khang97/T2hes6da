package sprite;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class SpriteLoad {
	private static SpriteLoad single = new SpriteLoad(); // single instance
	public static SpriteLoad get() {
		return single;
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap sprites = new HashMap(); // unit map
	

	@SuppressWarnings("unchecked")
	public Sprite getSprite(String ref) { // get unit
		if (sprites.get(ref) != null) { // if in cache return
			return (Sprite) sprites.get(ref);
		}
		
		BufferedImage sourceImage = null;
		
		try { // try to get image
			URL url = this.getClass().getClassLoader().getResource(ref);
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			fail("Failed to load: "+ref);
		}
		
		// create accelerated image of right size for sprite
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
		
		// draw image into accelerated image
		image.getGraphics().drawImage(sourceImage,0,0,null);
		
		// create unit add to cache
		Sprite sprite = new Sprite(image);
		sprites.put(ref,sprite);
		
		return sprite;
	}
	

	private void fail(String message) { // if not found exit game
		System.err.println(message);
		System.exit(0);
	}
}

// this class mainly from
// https://www.youtube.com/watch?v=0RSqbDKbnXc&list=PL8MfEpjBKpQTG7WZMWM0FA9dWjHc40dxU