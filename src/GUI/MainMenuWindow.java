package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import game.Bishop;
import game.Board;
import game.Human;
import game.King;
import game.Knight;
import game.Pawn;
import game.Piece;
import game.Queen;
import game.RandomBot;
import game.Rook;

public class MainMenuWindow implements Runnable {
	JTextField nameOfGamefile;
	
	public void run() {
		JFrame menuFrame = new JFrame("Menu");
		menuFrame.setResizable(false);
		
		JPanel first = new JPanel();
		JLabel welcomeLabel = new JLabel("Welcome to the game of chess!");
		
		JPanel second = new JPanel();
		JButton newGamePvpButton = new JButton("Play against each other!");
		newGamePvpButton.addActionListener(new newGamePvpButtonActionListener());
		JButton newGamePvcButton = new JButton("Play against the computer!");
		newGamePvcButton.addActionListener(new newGamePvcButtonActionListener());
		
		JPanel third = new JPanel();
		JLabel loadLabel = new JLabel("Type in the name of a saved gamefile:");
		nameOfGamefile = new JTextField(15);
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new LoadButtonActionListener());
		
		menuFrame.setLayout(new GridLayout(3, 1));
		
		first.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		first.add(welcomeLabel);
		second.setLayout(new GridLayout(1, 2));
		second.add(newGamePvpButton);
		second.add(newGamePvcButton);
		third.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		third.add(loadLabel);
		third.add(nameOfGamefile);
		third.add(loadButton);
		
		menuFrame.add(first);
		menuFrame.add(second);
		menuFrame.add(third);
		menuFrame.pack();
		
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setVisible(true);
	}
	
	class newGamePvpButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new GameWindow(new Board()));
		}
		
	}
	
	class newGamePvcButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Board b = new Board();
			b.setPlayerWhite(new Human(b));
			b.setPlayerBlack(new RandomBot(b));
			SwingUtilities.invokeLater(new GameWindow(b));
		}
		
	}
	
	class LoadButtonActionListener implements ActionListener {
		private Board savedBoard = new Board();

		@Override
		public void actionPerformed(ActionEvent e) {
			String filename = nameOfGamefile.getText();
			try {
				FileInputStream f =
				new FileInputStream("saved_positions/" + filename);
				ObjectInputStream in =
				new ObjectInputStream(f);
				savedBoard = (Board) in.readObject();
				in.close();
				initializeImages();
			} catch(IOException ex) {
					System.out.println("Error with file: " + ex.getMessage());
			} catch(ClassNotFoundException ex) {
					System.out.println("Error with serialization: " + ex.getMessage());
			}
			
			if(savedBoard.getPlayerBlack().getClass().equals(RandomBot.class)) {
				savedBoard.setPlayerBlack(new RandomBot(savedBoard));
			}
				
			SwingUtilities.invokeLater(new GameWindow(savedBoard));

		}
		
		public void initializeImages() {
			try {
				BufferedImage pieces = ImageIO.read(new File("src/res/pieces.png"));
        		
        		String[] names = new String[] {"whiteking", "whitequeen", "whitebishop", "whiteknight", "whiterook", "whitepawn",
        				"blackking", "blackqueen", "blackbishop", "blackknight", "blackrook", "blackpawn"};
        		
        		HashMap<String, Image> imagesWithNames = new HashMap<>();
        		int i = 0;
        		for(int y = 0; y < 400; y += 200) {
        			for(int x = 0; x < 1200; x += 200) {
        				imagesWithNames.put(names[i++], pieces.getSubimage(x, y, 200, 200).getScaledInstance(80, 80, BufferedImage.SCALE_SMOOTH));
        			}
        		}
        		
        		for(Piece p : savedBoard.whitePieces) {
        			if(p.getClass().equals(Rook.class)) {
    					p.setImage(imagesWithNames.get("whiterook"));
    				}
        			if(p.getClass().equals(Knight.class)) {
    					p.setImage(imagesWithNames.get("whiteknight"));
    				}
        			if(p.getClass().equals(Bishop.class)) {
    					p.setImage(imagesWithNames.get("whitebishop"));
    				}
        			if(p.getClass().equals(Queen.class)) {
    					p.setImage(imagesWithNames.get("whitequeen"));
    				}
        			if(p.getClass().equals(King.class)) {
    					p.setImage(imagesWithNames.get("whiteking"));
    				}
    				if(p.getClass().equals(Pawn.class)) {
    					p.setImage(imagesWithNames.get("whitepawn"));
    				}
    			}
        		for(Piece p : savedBoard.blackPieces) {
        			if(p.getClass().equals(Rook.class)) {
    					p.setImage(imagesWithNames.get("blackrook"));
    				}
        			if(p.getClass().equals(Knight.class)) {
    					p.setImage(imagesWithNames.get("blackknight"));
    				}
        			if(p.getClass().equals(Bishop.class)) {
    					p.setImage(imagesWithNames.get("blackbishop"));
    				}
        			if(p.getClass().equals(Queen.class)) {
    					p.setImage(imagesWithNames.get("blackqueen"));
    				}
        			if(p.getClass().equals(King.class)) {
    					p.setImage(imagesWithNames.get("blackking"));
    				}
    				if(p.getClass().equals(Pawn.class)) {
    					p.setImage(imagesWithNames.get("blackpawn"));
    				}
    			}
        		
			} catch(IOException ioe) {
					System.out.println("Error with pieces.png: " + ioe.getMessage());
			}
		}
		
	}
}
