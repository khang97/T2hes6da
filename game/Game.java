package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.EnemyEntity;
import entities.Entity;
import entities.PlayerEntity;
import entities.ShotEntity;


public class Game extends Canvas {
	
	private static final long serialVersionUID = -1576587291685810930L;
	private BufferStrategy strategy;
	private boolean gameRunning = true; // game is looping
	@SuppressWarnings("rawtypes")
	private ArrayList removeList = new ArrayList(); // list to remove this loop
	@SuppressWarnings("rawtypes")
	private ArrayList entities = new ArrayList(); // all existing units
	private Entity player; // player
	private double moveSpeed = 450; //player speed
	private long fireSpeed = 500; // time between shots
	private long lastFire = 0; // last fire time
	private int enemiesAlive; // counts aliens left
	
	private String message = ""; // current message on screen
	private boolean waitingForKeyPress = true; // start game on key press
	private boolean firePressed = false;
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean actionRequiredThisLoop = false;
	

	public Game() { // game screen and window settings
		
		JFrame container = new JFrame("Tähesõda v0.1");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800,600)); // set resolution
		panel.setLayout(null);
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		setBounds(0,0,800,600); // set canvas bound
		panel.add(this);
		container.getContentPane().setBackground(Color.BLACK);
		
		container.addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		addKeyListener(new KeyInputHandler());
		requestFocus();
		
		createBufferStrategy(2); // buffer strategy for faster render
		strategy = getBufferStrategy();

		spawn(); // spawn on startup
	}
	

	private void startGame() {
		entities.clear(); // fresh board on starting game
		spawn();

	}
	
	@SuppressWarnings("unchecked")
	private void spawn() {
		
		player = new PlayerEntity(this,"sprite/ship.png",370,542); // create player
		entities.add(player);
		
		enemiesAlive = 0; // create aliens
		for (int row=0;row<6;row++) {
			for (int x=0;x<10;x++) {
				Entity alien = new EnemyEntity(this,"sprite/alien.png",150+(x*50),(35)+row*35);
				entities.add(alien);
				enemiesAlive++;
			}
		}	
	}

	public void updateAction() { // update action loop
		actionRequiredThisLoop = true;
	}
	
	@SuppressWarnings("unchecked")
	public void removeEntity(Entity entity) { // add alien to removed list
		removeList.add(entity);
	}
	
	public void notifyWin() { // when win send message and wait for input
		message = "You defeated the enemy forces, you win!";
		waitingForKeyPress = true;
	}
	
	public void notifyLose() { // when dead send message and wait for input
		message = "You lost!";
		waitingForKeyPress = true;
	}

	public void notifyAlienKilled() { // count aliens killed, when 0 left
		enemiesAlive--;				  // trigger player win
		
		if (enemiesAlive == 0) {
			notifyWin();
		}
	}
	

	@SuppressWarnings("unchecked")
	public void tryFire() { // only fire if enough time passed since last fire
		if (System.currentTimeMillis() - lastFire < fireSpeed) {
			return;
		}
		
		lastFire = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this,"sprite/shoot.png",player.getX()+10,player.getY()-30);
		entities.add(shot); // creates bullet
	}
	
	
	@SuppressWarnings("unchecked")
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis(); // game loop
		while (gameRunning) {
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();

			Graphics2D g = (Graphics2D) strategy.getDrawGraphics(); // draw graphics
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			
			for (int i=0;i<entities.size();i++) { // draw all spawns
				Entity entity = (Entity) entities.get(i);
				
				entity.draw(g);
			}
			if (!waitingForKeyPress) { // move all spawns
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					
					entity.move(delta);
				}
			}
			
			for (int p=0;p<entities.size();p++) { // collision between spawns
				for (int s=p+1;s<entities.size();s++) {
					Entity me = (Entity) entities.get(p);
					Entity him = (Entity) entities.get(s);
					
					if (me.collidesWith(him)) {
						me.collidedWith(him);
						him.collidedWith(me);
					}
				}
			}
			
			entities.removeAll(removeList); // delete spawns in remove list
			removeList.clear();

			if (actionRequiredThisLoop) { // ask to change action this loop if action change triggered
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					entity.doAction();
				}
				
				actionRequiredThisLoop = false;
			}
			
			if (waitingForKeyPress) { // waiting for key press print message
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Press any key to continue!",(800-g.getFontMetrics().stringWidth("Press any key to continue!"))/2,300);
			}
			
			g.dispose(); // drawing finished
			strategy.show();
			
			player.setHorizontalMovement(0); // move ship when key pressed, stop when both
			if ((leftPressed) && (!rightPressed)) {
				player.setHorizontalMovement(-moveSpeed);
			} else if ((rightPressed) && (!leftPressed)) {
				player.setHorizontalMovement(moveSpeed);
			}
			
			if (firePressed) { // check time from last fire
				tryFire();
			}
		}
	}
	
	
	private class KeyInputHandler extends KeyAdapter {
		private int pressCount = 1; // act on key press, return while waiting
		public void keyPressed(KeyEvent e) {
			if (waitingForKeyPress) {
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) { // move left
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // move right
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) { // shoot
				firePressed = true;
			}
		} 

		public void keyReleased(KeyEvent e) { // when release stop event
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) { // stop moving left
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // stop moving right
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) { // stop shooting
				firePressed = false;
			}
		}

		
		public void keyTyped(KeyEvent e) {
			if (waitingForKeyPress) {
				if (pressCount == 1) { // when 1 key pressed 
					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
		}
	}
	

	public static void main(String argv[]) { // create and start game loop
		Game g =new Game();
		g.gameLoop();
	}
	
}
