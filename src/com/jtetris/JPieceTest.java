package com.jtetris;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;


/**
 Debugging client for the Piece class.
 The JPieceTest component draws all the rotations of a tetris piece.
 JPieceTest.main()  creates a frame  with one JPieceTest for each
 of the 7 standard tetris pieces.
 
 This is the starter file version -- 
 The outer shell is done. You need to complete paintComponent()
 and drawPiece().
*/
class JPieceTest extends JComponent {
	protected Piece root;	
	

	public JPieceTest(Piece piece, int width, int height) {
		super();
		
		setPreferredSize(new Dimension(width, height));

		root = piece;
	}

	/**
	 Draws the rotations from left to right.
	 Each piece goes in its own little box.
	*/
	public final int MAX_ROTATIONS = 4;
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		
		int SQ_PX_SIZE = 20;
		for(int count = 0; count < 4; count++) {
			Point[] body = root.getBody();
			for(int i = 0; i < body.length; i++) {
				g.fillRect((body[i].x * SQ_PX_SIZE) + 1 + count * 4 * SQ_PX_SIZE, (body[i].y * SQ_PX_SIZE) + 1, SQ_PX_SIZE-1, SQ_PX_SIZE-1);
			}
			
			root = root.nextRotation();
		}
		
		
	}
	
	/**
	 Draw the piece inside the given rectangle.
	*/
	private void drawPiece(Graphics g, Piece piece, Rectangle r) {
	}	


	/**
	 Draws all the pieces by creating a JPieceTest for
	 each piece, and putting them all in a frame.
	 * @throws YouDunGoofedException 
	 * @throws InvalidPieceListException 
	*/
	static public void main(String[] args) throws InvalidPieceListException, YouDunGoofedException
	
	{
		JFrame frame = new JFrame("Piece Tester");
		JComponent container = (JComponent)frame.getContentPane();
		
		// Put in a BoxLayout to make a vertical list
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Piece[] pieces = Piece.getPieces();
		
		for (int i=0; i<pieces.length; i++) {
			JPieceTest test = new JPieceTest(pieces[i], 375, 75);
			container.add(test);
		}
		
		// Size the window and show it on screen
		frame.pack();
		frame.setVisible(true);
		
		// Quit on window close
		frame.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
	}
}