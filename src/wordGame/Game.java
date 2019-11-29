package wordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game implements Controller {

	Board board;
	Rack rack;

	ArrayList<String> words;

	public Game() {
		board = Board.getBoard();
		rack = Rack.getRack();
		words = new ArrayList<String>(70000);
		getWords();
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

	@Override
	public String refillRack() {

		rack.refill();

		return rack.toString();

	}

	/**
	 * Create a string format of the board
	 */
	@Override
	public String gameState() {
		return board.toString();
	}

	/*
	 * Play method that creates a starting letter, cell number and direction and
	 * changes the letter position to a new position
	 * 
	 */
	@Override
	public String play(Play play) {
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(play.cell().charAt(0));
		// gets the cell number
		int cellNumber = Integer.parseInt(play.cell().substring(1));
		// creates a new direction
		Direction dir = play.dir();
		// creates a char[] array of letter positions
		char[] letterPositions = play.letterPositionsInRack().toCharArray();
		// increments the cell number and the starting letter after
		// checking the letter position
		for (char c : letterPositions) {
			board.replace(startingLetter, cellNumber, rack.pop(Integer.parseInt("" + c)));
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

	/**
	 * Check the validity of a specified play (i.e. move). Display a message stating
	 * "Valid" or "Invalid" after the check. With a valid play, all new English
	 * words introduced to the game board will be displayed. With an invalid play,
	 * the invalid letter sequence introduced to the game board will also be
	 * returned.
	 * 
	 * @param play information about where to place which tile in the form of
	 *             STARTING_CELL; DIRECTION; LETTER_POSITIONS_IN_RACK, e.g. B3,
	 *             DOWN, 513
	 * 
	 * @return a message stating "Valid" or "Invalid". With a valid play, the game
	 *         engine will also display all new English words introduced to the game
	 *         board. With an invalid play, the game engine will also display the
	 *         invalid letter sequence introduced to the game board.
	 */
	@Override
	public String checkValidity(Play play) {

		String cellLocation = play.cell();
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(cellLocation.charAt(0));
		System.out.println(startingLetter);
		// gets the cell number
		int cellNumber = Integer.parseInt(cellLocation.substring(1));
		// creates a new direction
		Direction dir = play.dir();
		// creates a char[] array of letter positions
		char[] letterPositions = play.letterPositionsInRack().toCharArray();

		List<String> letters = new ArrayList<String>();

		if (dir == Direction.DOWN) {
			for (; board.checkIfInBoard(startingLetter, cellNumber, dir); cellNumber--) {
				if (board.getCellValue("" + board.getLetter(startingLetter) + cellNumber) != '.' && board.getCellValue("" + board.getLetter(startingLetter) + cellNumber) != '+') {
					letters.add("" + board.getCellValue("" + board.getLetter(startingLetter) + cellNumber));
				}
			}
		} else {
			for (; board.checkIfInBoard(startingLetter, cellNumber, dir); startingLetter++) {
				if (board.getCellValue("" + board.getLetter(startingLetter) + cellNumber) != '.' && board.getCellValue("" + board.getLetter(startingLetter) + cellNumber) != '+') {
					letters.add("" + board.getCellValue("" + board.getLetter(startingLetter) + cellNumber));
				}
			}
		}

		for (char c : letterPositions) {
			letters.add("" + rack.pull(Integer.parseInt("" + c)));
		}

		System.out.println(letters.toString());

		int test = 0;

		for (String s : words) {
			for (String l : letters) {
				if (s.length() != letters.size()) {
				}
				if (s.contains(l)) {
					test++;
				} else {
				}
			}
			if (test == letters.size()) {
				System.out.println(s);
			}
			test = 0;
		}

		return board.toString();

	}

	private void getWords() {

		File file = new File("wordList.txt");

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			try {
				while ((st = br.readLine()) != null) {
					words.add(st);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
