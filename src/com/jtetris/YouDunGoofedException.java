package com.jtetris;

public class YouDunGoofedException extends Exception {
	
	String message;
	
	public YouDunGoofedException(String msg) {
		this.message = msg;
	}
	
	public String toString() {
		//TO-DO: Implement Piece class toString() so you can print out piece.body
		return message;
	}
}
