package com.jtetris;

public class PieceTest {
	
	static public void main(String[] args) throws InvalidPieceListException, YouDunGoofedException {
		System.out.println("Starting Piece test suite ..");
		
//		System.out.println("TEST: Piece.equals");
//		Piece.testPieceEqual();
		
		System.out.println("TEST: Piece.PieceRow");
		Piece.testPieceRow();
		
//		System.out.println("TEST: Piece.getNextRotation");
//		Piece.testGetNextRotation();
		
//		System.out.println("TEST: Piece.isDistinct");
//		Piece.testIsDistinct();
	}
}
