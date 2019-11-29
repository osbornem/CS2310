package wordGame;

import java.util.ArrayList;
import java.util.Random;

public class Game implements Controller {

	Board board;

	public Game() {
		board = Board.getBoard();
	}

	/**
	 * Create a new game and TUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a new Game
		Game g = new Game();
		// Pass game onto the TUI
		new TUI(g);
	}

	
	
	public int getRand(int max)
	{
	
		Random rand = new Random();
		return rand.nextInt(max);
		
	}
	
	/**
	 * Create a string format of the board
	 */

	@Override
	public String gameState() {
		return board.toString();
	}

	
	@Override
	public String refillRack() {
		
		
		ArrayList<Character> rack = new ArrayList<Character>();                
		ArrayList<Character> letters = new ArrayList<Character>();
		
		   for(int count = 0; count<rack.size(); count++)
		   
		
		   while(rack.size()!= 5)
		   {
		      
		      int rand = getRand(letters.size());
			  rack.add(letters.get(rand));
			  letters.remove(rand);
			  
		   }
		return "Your letters are " + letters.get(1) + " " + letters.get(2) + " " + letters.get(3) + " " + letters.get(4) + " " + letters.get(5) + ". Board: " + board.toString();
		
		 

	}

	/**
	 * Create a string format of the board
	 */


	@Override
	public String play(Play play) {
		int startingLetter = board.getLetterIndex(play.cell().charAt(0));

		int cellNumber = Integer.parseInt(play.cell().substring(1));

		Direction dir = play.dir();

		char[] letterPositions = play.letterPositionsInRack().toCharArray();

		for (char c : letterPositions) {
			board.replace(startingLetter, cellNumber, c); // c currently doesn't do shit
			if (dir == Direction.DOWN) { 
				cellNumber++;
			} else {
				startingLetter++;
			}
		}
		return gameState();
	}

	@Override
	public String calculateScore(Play play) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkValidity(Play play) {
		// TODO Auto-generated method stub
		return null;
	}

}
