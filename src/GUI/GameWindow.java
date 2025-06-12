package GUI;

import game.Piece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Board;
import game.King;
import game.RandomBot;
import game.Square;

public class GameWindow implements Runnable {
	private Board b;
	
	public GameWindow(Board b) {
		this.b = b;
	}
	
	class GUIBoard extends JPanel implements MouseListener, MouseMotionListener {
		private boolean isMouseEnabled;
		private GUISquare[][] guisquares;
		private Piece selectedPiece;
		private GUISquare selectedGUISquare;
		private int mousePosX;
		private int mousePosY;
		
		public GUIBoard() {
			isMouseEnabled = true;
			guisquares = new GUISquare[8][8];
			
			setBounds(15, 39, 640, 640);
			setLayout(new GridLayout(8, 8, 0, 0));
			addMouseListener(this);
			addMouseMotionListener(this);
			for (int y = 7; y >= 0; y--) {
				for (int x = 0; x <= 7; x++) {
					GUISquare gs = new GUISquare(b.getSquares()[x][y]);
					add(gs);
					guisquares[x][y] = gs;
				}
			}
		}
		
		public GUISquare[][] getGUISquares(){
			return guisquares;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					guisquares[x][y].paintComponent(g);
				}
			}
			if (selectedPiece != null) {
				if ((selectedPiece.isColoredWhite() && b.isWhiteToMove())
				|| (!selectedPiece.isColoredWhite() && !b.isWhiteToMove())) {
					g.drawImage(selectedPiece.getImage(), mousePosX, mousePosY, null);
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(!isMouseEnabled) return;
			mousePosX = e.getX() - 32;
			mousePosY = e.getY() - 32;
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(!isMouseEnabled) return;
			mousePosX = e.getX() - 32;
			mousePosY = e.getY() - 32;
			selectedGUISquare = (GUISquare) this.getComponentAt(new Point(e.getX(), e.getY()));
			Square selectedSquare = selectedGUISquare.getSquare();
			
			if(selectedSquare.getPiece() != null) {
				if((selectedSquare.getPiece().isColoredWhite() && !b.isWhiteToMove())
				|| (!selectedSquare.getPiece().isColoredWhite() && b.isWhiteToMove())) {
					return;
				} else {
					selectedPiece = selectedSquare.getPiece();
					selectedGUISquare.setPieceVisible(false);
				}
			}
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(!isMouseEnabled) return;
			GUISquare targetGUISquare = (GUISquare) this.getComponentAt(new Point(e.getX(), e.getY()));
			try {
				Square targetSquare = targetGUISquare.getSquare();
				
				if(selectedPiece != null && selectedPiece.getLegalSquaresToMoveTo().contains(targetSquare)
				&& !b.getCheckControl().doesMoveIntoCheck(b.isWhiteToMove(), selectedPiece, targetSquare)) {
					selectedPiece.move(targetSquare);
					b.setWhiteToMove(!b.isWhiteToMove());
					
					targetGUISquare.setPieceVisible(true);
					if(targetSquare.getCoordinateX() - selectedGUISquare.getSquare().getCoordinateX() == 2 && selectedPiece.getClass().equals(King.class)) {
						if(selectedPiece.isColoredWhite()) {
							guisquares[5][0].setPieceVisible(true);
						} else {
							guisquares[5][7].setPieceVisible(true);
						}
					} else if(targetSquare.getCoordinateX() - selectedGUISquare.getSquare().getCoordinateX() == -2 && selectedPiece.getClass().equals(King.class)) {
						if(selectedPiece.isColoredWhite()) {
							guisquares[3][0].setPieceVisible(true);
						} else {
							guisquares[3][7].setPieceVisible(true);
						}
					}
					
					if(b.getCheckControl().whiteIsCheckmated() || b.getCheckControl().blackIsCheckmated()) {
						isMouseEnabled = false;
						repaint();
						if(b.getCheckControl().whiteIsCheckmated()) {
							JOptionPane.showMessageDialog(new JFrame(), "Black wins with checkmate!");
						} else {
							JOptionPane.showMessageDialog(new JFrame(), "White wins with checkmate!");
						}
					} else if(b.getCheckControl().staleMateHappened()) {
						isMouseEnabled = false;
						repaint();
						JOptionPane.showMessageDialog(new JFrame(), "Draw by stalemate!");
					}
					
					if(b.getPlayerBlack().getClass().equals(RandomBot.class)) {
						b.getPlayerBlack().makeMove();
						b.setWhiteToMove(!b.isWhiteToMove());
						targetGUISquare.setPieceVisible(true);
						if(targetSquare.getCoordinateX() - selectedGUISquare.getSquare().getCoordinateX() == 2 && selectedPiece.getClass().equals(King.class)) {
							if(selectedPiece.isColoredWhite()) {
								System.out.println("ok");
								guisquares[5][0].setPieceVisible(true);
							} else {
								guisquares[5][7].setPieceVisible(true);
							}
						} else if(targetSquare.getCoordinateX() - selectedGUISquare.getSquare().getCoordinateX() == -2 && selectedPiece.getClass().equals(King.class)) {
							if(selectedPiece.isColoredWhite()) {
								guisquares[3][0].setPieceVisible(true);
							} else {
								guisquares[3][7].setPieceVisible(true);
							}
						}
						
						if(b.getCheckControl().whiteIsCheckmated() || b.getCheckControl().blackIsCheckmated()) {
							isMouseEnabled = false;
							repaint();
							if(b.getCheckControl().whiteIsCheckmated()) {
								JOptionPane.showMessageDialog(new JFrame(), "Black wins with checkmate!");
							} else {
								JOptionPane.showMessageDialog(new JFrame(), "White wins with checkmate!");
							}
						} else if(b.getCheckControl().staleMateHappened()) {
							isMouseEnabled = false;
							repaint();
							JOptionPane.showMessageDialog(new JFrame(), "Draw by stalemate!");
						} else {
							repaint();
						}
						
					}
				} else {
					selectedGUISquare.setPieceVisible(true);
				}
			} catch (NullPointerException npe) {
				System.out.println("nullptr");
				selectedGUISquare.setPieceVisible(true);
			}
			
			selectedGUISquare = null;
			selectedPiece = null;
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class GUISquare extends JComponent {
		private Square square;
		private boolean pieceVisible;
		
		public GUISquare(Square s) {
			this.square = s;
			this.pieceVisible = true;
			this.setBorder(BorderFactory.createEmptyBorder());
		}
		
		public Square getSquare() {
			return square;
		}
		
		public void setPieceVisible(boolean b) {
			pieceVisible = b;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (square.isColoredWhite() == true) {
	            g.setColor(new Color(212,191,146));
	        } else {
	            g.setColor(new Color(101,67,33));
	        }
	        
	        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	        
	        if(square.getPiece() != null && pieceVisible) {
	        	g.drawImage(square.getPiece().getImage(), this.getX(), this.getY(), null);
	        }
		}
	}

	@Override
	public void run() {
		JFrame gameFrame = new JFrame("Game");
		gameFrame.setResizable(false);
		gameFrame.setLocation(450, 0);
		gameFrame.setSize(685, 730);
		
		GUIBoard board = new GUIBoard();
		JPanel buttons = new JPanel();
		JButton newGame = new JButton("New game");
		newGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(gameFrame,
						"Are you sure you want to start a new game?",
						"Confirm new game",
						JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION) {
					b.setUpStartingPosition();
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							board.getGUISquares()[i][j].setPieceVisible(true);
						}
					}
					b.setWhiteToMove(true);
					board.isMouseEnabled = true;
					board.repaint();
				}
			}
		});
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = JOptionPane.showInputDialog("Type in the name of the file:");
				try {
					
					FileOutputStream f = new FileOutputStream("saved_positions/" + filename);
					ObjectOutputStream out = new ObjectOutputStream(f);
					out.writeObject(b);
					out.close();
				} catch(IOException ex) {
					System.out.println("Error with saving: " + ex.getMessage());
				}
				
			}
		});
		
		buttons.add(newGame);
		buttons.add(save);
		
		gameFrame.add(board);
		gameFrame.add(buttons);
		
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setVisible(true);
	}
	
}
