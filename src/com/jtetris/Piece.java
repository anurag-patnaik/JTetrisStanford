package com.jtetris;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 An immutable representation of a tetris piece in a particular rotation.
 Each piece is defined by the blocks that make up its body.
 See the Tetris-Architecture.html for an overview.
 
 This is the starter file version -- a few simple things are filled in already
 
 @author	Nick Parlante
 @version	1.0, Mar 1, 2001
*/
public final class Piece {
/*
 Implementation notes:
 -The starter code does out a few simple things for you
 -Store the body as a Point[] array
 -The ivars in the Point class are .x and .y
 -Do not assume there are 4 points in the body -- use array.length
 to keep the code general
*/
	private Point[] body;
	private int[] skirt;
	private int width;
	private int height;
	private Piece next;	// "next" rotation
	
	static private Piece[] pieces;	// singleton array of first rotations
	
	/**
	 Defines a new piece given the Points that make up its body.
	 Makes its own copy of the array and the Point inside it.
	 Does not set up the rotations.
	 
	 This constructor is PRIVATE -- if a client
	 wants a piece object, they must use Piece.getPieces().
	*/
	private Piece(Point[] points) {
		// Anurag
		this.body = points;
		
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for(int i = 0; i < points.length; i++) {
			if(points[i].x < minX) {
				minX = points[i].x;
			}
			 
			if(points[i].y < minY) {
				minY = points[i].y;
			}
			
			if(points[i].x > maxX) {
				maxX = points[i].x;
			}
			 
			if(points[i].y > maxY) {
				maxY = points[i].y;
			}
		}
		
		// Calculate width: Max xVal - min XVal
		width = maxX - minX;
		height = maxY - minY;
		
		System.out.println("minX: " +minX);
		System.out.println("maxX: " +maxX);
		System.out.println("minY: " +minY);
		System.out.println("maxY: " +maxY);
		
		System.out.println("Width: " +width);
		System.out.println("Height: " +height);
		
		skirt = new int[width+1];
		// Calculate skirt: lowest y for every x
		int skirtValue = Integer.MAX_VALUE;
		int skirtIndex = 0;
		for(int i = minX; i <= maxX; i++) {
			skirtValue = Integer.MAX_VALUE;
			for(int j = 0; j <  points.length; j++) {
				if(points[j].x == i) { // For every x
					if(skirtValue > points[j].y) {
						skirtValue = points[j].y; // Calculate lowest y
					}
				}
			}
			skirt[skirtIndex] = skirtValue;
			skirtIndex++;
		}
	}	

	
    /**
     Returns the width of the piece measured in blocks.
    */
	public int getWidth() {
		return(width);
	}
	
    /**
     Returns the height of the piece measured in blocks.
    */
	public int getHeight() {
		return(height);
	}

    /**
     Returns a pointer to the piece's body. The caller
     should not modify this array.
    */
	public Point[] getBody() {
		return(body);
	}
	
    /**
     Returns a pointer to the piece's skirt. For each x value
     across the piece, the skirt gives the lowest y value in the body.
     This useful for computing where the piece will land.
     The caller should not modify this array.
    */
	public int[] getSkirt() {
		return(skirt);
	}


	/**
	 Returns a piece that is 90 degrees counter-clockwise
	 rotated from the receiver.
	 
	 <p>Implementation:
	 The Piece class pre-computes all the rotations once.
	 This method just hops from one pre-computed rotation
	 to the next in constant time.
	*/	
	public Piece nextRotation() {
		return next;
	}
	
	
	/**
	 Returns true if two pieces are the same --
	 their bodies contain the same points.
	 Interestingly, this is not the same as having exactly the
	 same body arrays, since the points may not be
	 in the same order in the bodies. Used internally to detect
	 if two rotations are effectively the same.
	*/
	public boolean equals(Piece other) {
		boolean isPresent;

		for(int i = 0; i < this.body.length; i++) {
			isPresent = false;
			for(int j = 0; j < other.body.length; j++) {
				if(this.body[i].x == other.body[j].x && this.body[i].y == other.body[j].y) {
					isPresent = true; // Match found
					break;
				}
			}

			if(!isPresent) {
				// Match not found for one of the points. Return false.
				return false;
			}
		}
		
		return true;
	}



	
	/**
	 Returns an array containing the first rotation of
	 each of the 7 standard tetris pieces.
	 The next (counterclockwise) rotation can be obtained
	 from each piece with the {@link #nextRotation()} message.
	 In this way, the client can iterate through all the rotations
	 until eventually getting back to the first rotation.
	 * @throws YouDunGoofedException 
	 * @throws InvalidPieceListException 
	*/
	public static Piece[] getPieces() throws InvalidPieceListException, YouDunGoofedException {
	/*
	 Hint
	 
	 My code to produce the array of the pieces looks like the following.
	 -parsePoints computes the Point[] array
	 -The Piece constructor builds a single piece but not the rotations
	 -The helper function piecerRow() computes all the rotations of that piece
	 and connects them by their .next fields.
	 
	 Your pieces must be in the same 0..6 order to get the same output as
	 the sample solution. Only compute the array when it is first asked for.
	 Then just re-use that array for later requests.
	 
		pieces = new Piece[] {
			pieceRow(new Piece(parsePoints("0 0	0 1	0 2	0 3"))),	// 0
			pieceRow(new Piece(parsePoints("0 0	0 1	0 2	1 0"))),	// 1
			pieceRow(new Piece(parsePoints("0 0	1 0	1 1	1 2"))),	// 2
			pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 1"))),	// 3
			pieceRow(new Piece(parsePoints("0 1	1 1	1 0	2 0"))),	// 4
			pieceRow(new Piece(parsePoints("0 0	0 1	1 0	1 1"))),	// 5
			pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 0"))),	// 6
		};
	*/
		// Compute array only once
		if(pieces == null) {
			pieces = new Piece[] {
					pieceRow(new Piece(parsePoints("0 0	0 1	0 2	0 3"))),	// 0
					pieceRow(new Piece(parsePoints("0 0	0 1	0 2	1 0"))),	// 1
					pieceRow(new Piece(parsePoints("0 0	1 0	1 1	1 2"))),	// 2
					pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 1"))),	// 3
					pieceRow(new Piece(parsePoints("0 1	1 1	1 0	2 0"))),	// 4
					pieceRow(new Piece(parsePoints("0 0	0 1	1 0	1 1"))),	// 5
					pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 0"))),	// 6
				};
		}
		
		return pieces;
	}
	
	// Anurag: Connects piece to its next rotation and returns original piece
	private static Piece pieceRow(Piece piece) throws InvalidPieceListException, YouDunGoofedException {
		// Keep creating next rotation and linking to piece till piece rotates to original position
		Piece root = piece; //root of circular linked list
		
		Piece nextPiece = getNextRotation(piece);
		
		while(isDistinct(nextPiece, root)) {
			piece.next = nextPiece;
			piece = nextPiece; // Move pointer to last element
			
//			rotatedPoints = getNextRotation(piece);
//			nextPiece = new Piece(rotatedPoints);
			
			nextPiece = getNextRotation(piece);
		}
		
		// Point last element to first to complete circular list
		// DISABLE during testIsDistinct() .. else infinite loop
		piece.next = root;
		
		//TO-DO: Validate that list is circular
//		if(!listValidate(root)) {
//			throw new InvalidPieceListException(root);
//		}
		
		return root;
	}
	
	// Anurag: Tells us if the piece is a new rotation or if it already exists in the rotation list
	private static boolean isDistinct(Piece nextPiece, Piece root) throws YouDunGoofedException {
		int count = 0; // Max 4 elements in a list
		while(root != null) {
			if(nextPiece.equals(root)) {
				return false;
			} 
			
			count++;
			root = root.next;
			if(count > 4) {
				throw new YouDunGoofedException("Piece.java -> isDistinct() -- Circular list count should not be more than 4");
			}
		}
		
		return true;
	}
	
	//Anurag:Algorithm that returns point array of rotated body
	private static Piece getNextRotation(Piece piece) {
		// For 90 degree rotation, point is (A,B) -> (-B,A)
		// Calculate and then make all points positive to get results -- Or not, might work that way
		Point[] rotatedPoints = new Point[piece.body.length];
		
		// Initialize rotatedPoints Array
		for(int i = 0; i < piece.body.length; i++) {
			rotatedPoints[i] = new Point();
		}
		
		for(int i = 0; i < piece.body.length; i++) {
			rotatedPoints[i].x = -1 * piece.body[i].y; // -B
			rotatedPoints[i].y = piece.body[i].x; // A			
		}
		
		// Normalize pieces. Move coordinates horizontally to start at (0,0)
		// Step A: Find minimum x coordinate and if negative, add to all x co-ods
		// ex: (-1,0) (0,0) --> (0,0) (1,0)
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for(int i = 0; i < rotatedPoints.length; i++) {
			if(minX > rotatedPoints[i].x) {
				minX = rotatedPoints[i].x;
			}
			
			if(minY > rotatedPoints[i].y) {
				minY = rotatedPoints[i].y;
			}
		}
		
//		System.out.println("minX = "  + minX);
//		System.out.println("minY = "  + minY);
		
		if(minX < 0) {
			for(int i = 0; i <rotatedPoints.length; i++) {
				rotatedPoints[i].x += (-1) * minX;
			}
		}
		
		if(minY < 0) {
			for(int i = 0; i < rotatedPoints.length; i++) {
				rotatedPoints[i].y += (-1) * minY;
			}
		}
		
		return new Piece(rotatedPoints);
	}

	/**
	 Given a string of x,y pairs ("0 0	0 1	0 2	1 0"), parses
	 the points into a Point[] array.
	 (Provided code)
	*/
	private static Point[] parsePoints(String string) {
	    // could use Arraylist here, but use vector so works on Java 1.1
		Vector points = new Vector();
		StringTokenizer tok = new StringTokenizer(string);
		try {
			while(tok.hasMoreTokens()) {
				int x = Integer.parseInt(tok.nextToken());
				int y = Integer.parseInt(tok.nextToken());
				
				points.addElement(new Point(x, y));
			}
		}
		catch (NumberFormatException e) {
			throw new RuntimeException("Could not parse x,y string:" + string);	// cheap way to do assert
		}
		
		// Make an array out of the Vector
		Point[] array = new Point[points.size()];
		points.copyInto(array);
		return(array);
	}
	
	public void printBody() {
		for(int i = 0; i < this.body.length; i++) {
			System.out.print("(" + this.body[i].x + "," + this.body[i].y + ") ");
		}
		System.out.println();
	}
	
	//Anurag -- TEST methods
	public static void testPieceEqual() {
//		Piece piece1 = new Piece(parsePoints("0 0	0 1	0 2	0 3"));
//		Piece piece2 = new Piece(parsePoints("0 1	0 2	0 3	0 0")); //same as piece1
//		Piece piece3 = new Piece(parsePoints("0 0	0 1	0 2	1 0")); //different
		
		Piece piece1 = new Piece(parsePoints("0 1	1 1	1 0	2 0"));
		Piece piece2 = new Piece(parsePoints("0 0	0 1	1 1	1 2"));
		Piece piece3 = new Piece(parsePoints("2 0	1 0	1 1	0 1"));
		Piece piece4 = new Piece(parsePoints("0 0	0 1	0 2	0 3"));
		
		boolean result = piece1.equals(piece3);
		System.out.println("result= " +result);			
	}
	
	public static void testPieceRow() throws InvalidPieceListException, YouDunGoofedException {
		Piece piece = pieceRow(new Piece(parsePoints("0 0	1 0	1 1	1 2")));
		
		while(piece != null) {
			piece.printBody(); 
			piece = piece.next;
		}
	}
	
	public static void testGetNextRotation() throws InvalidPieceListException, YouDunGoofedException {
//		Piece piece = new Piece(parsePoints("0 1	1 1	1 0	2 0"));
		Piece piece = pieceRow(new Piece(parsePoints("0 0	1 0	1 1	1 2")));
		
		Piece nextPiece = getNextRotation(piece);
		nextPiece.printBody();
	}
	
	public static void testIsDistinct() throws YouDunGoofedException {
		Piece piece1 = new Piece(parsePoints("0 1	1 1	1 0	2 0"));
		Piece piece2 = new Piece(parsePoints("0 0	0 1	1 1	1 2"));
		Piece piece3 = new Piece(parsePoints("2 0	1 0	1 1	0 1"));
		Piece piece4 = new Piece(parsePoints("0 0	0 1	0 2	0 3"));
		
		
		piece1.next = piece2;
		
		boolean distinct = isDistinct(piece4, piece1);
		
		System.out.println("distinct= " +distinct);
	}
	
	public static void testPieceInit() {
		Piece piece = new Piece(parsePoints("0 1	1 1	1 0	2 0"));
		piece.printBody();
		
		System.out.println("Width: " +piece.getWidth());
		System.out.println("Height: " +piece.getHeight());
		
		int[] skirt = piece.getSkirt();
		System.out.print("Skirt: {");
		for(int i = 0; i < skirt.length; i++) {
			System.out.print(skirt[i] + ",");
		}
		System.out.print("}");
		System.out.println();
	}	
}