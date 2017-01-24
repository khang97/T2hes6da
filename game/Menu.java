package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Menu extends Frame implements WindowListener,ActionListener {
	
        Button play;
        Button instructions;
        Button exit;
        
        public static void main(String[] args) {
                Menu menuScreen = new Menu("Tähesõda v0.1");
                menuScreen.setSize(325,100);
                menuScreen.setVisible(true);
                menuScreen.setResizable(false);
                menuScreen.setLocation(800,400);
        }

        public Menu(String title) {

                super(title);
                setLayout(new FlowLayout());
                addWindowListener(this);
                
                play = new Button("Play!");
                add(play);
                play.addActionListener(this);
                
                instructions = new Button("Instructions");
                add(instructions);
                instructions.addActionListener(this);
                
                exit = new Button("Exit");
                add(exit);
                exit.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
        	
        	Object react = e.getSource();
        	
        	if (react == play) {
        		
        	} else if (react == instructions) {
        		JOptionPane.showMessageDialog(null, "The goal of the game is to destroy all the aliens "
						+ "before they reach you. To \nmove your spaceship use the ARROW KEYS and to "
						+ "shoot press SPACEBAR. If \nyou kill all the aliens you win. If they touch "
						+ "you or reach the end of the \nscreen you lose. Good luck!", "Instructions",
						+ JOptionPane.INFORMATION_MESSAGE);
        	} else if (react == exit) {
        		dispose();
        		System.exit(0);
        	}	
       
		}

		public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
        }

        public void windowOpened(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
}
