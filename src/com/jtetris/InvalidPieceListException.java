package com.jtetris;

public class InvalidPieceListException extends Exception {

	Piece root;
	
	public InvalidPieceListException(Piece root) {
		this.root = root;
	}
	
	public String toString() {
		//TO-DO: Implement Piece class toString() so you can print out piece.body
		return "List is not circular. ";
	}
	
}
