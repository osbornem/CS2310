package wordGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game implements Controller {

	Board board;
	Rack rack;

	ArrayList<String> words;

	public Game() {
		board = Board.getBoard();
		rack = Rack.getRack();
		new FileIO();
		words = FileIO.getWords();
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
	 * Returns a string version of the board
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
		
		//checkForWordsOnBoard("HELLO", play);

		List<String> letters = getConnectingLetters(play);

		List<String> possibleWords = new ArrayList<String>();

		for (String w : words) {

			String[] wordStringArray = w.toUpperCase().split("");

			ArrayList<String> word = new ArrayList<String>(Arrays.asList(wordStringArray));

			if (word.containsAll(letters) && word.size() == letters.size()) {
				for (String l : letters) {
					word.remove(l);
				}
				if (word.size() == 0) {
					possibleWords.add(w.toUpperCase());
				}

			}
		}

		if (possibleWords.isEmpty()) {
			return "INVALID FOR LETTERS:" + letters.toString().replace('[', ' ').replace(']', ' ');
		}

		return "VALID WORDS:" + possibleWords.toString().replace('[', ' ').replace(']', ' ');

	}

	private List<String> getConnectingLetters(Play play) {

		String cellLocation = play.cell();
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(cellLocation.charAt(0));
		// gets the cell number
		int cellNumber = Integer.parseInt(cellLocation.substring(1));
		// creates a new direction
		Direction dir = play.dir();
		// creates a char[] array of letter
		char[] letterPositions = play.letterPositionsInRack().toCharArray();

		List<String> letters = new ArrayList<String>();

		letters = board.checkSurroundingCells(startingLetter, cellNumber, dir);

		for (char c : letterPositions) {
			letters.add("" + rack.pull(Integer.parseInt("" + c)));
		}

		return letters;

	}


	private Boolean checkForWordsOnBoard(String word, Play play) {

		String cellLocation = play.cell();
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(cellLocation.charAt(0));
		// gets the cell number
		int cellNumber = Integer.parseInt(cellLocation.substring(1));
		// creates a new direction
		Direction dir = play.dir();

		Direction direction = null;

		String[] letters = word.split("");

		String posInRack = "";

		ArrayList<Integer> letterIndexs = rack.getLettersIndex(letters);
		
		for(int i : letterIndexs) {
			posInRack += (i+1);
		}

		play(new Play(cellLocation, dir.toString(), posInRack));
		
		System.out.println(gameState());

		switch (dir) {
		case DOWN:
			direction = Direction.ACROSS;
			break;
		case ACROSS:
			direction = Direction.DOWN;
			break;
		}

		if (dir == Direction.DOWN) {
			for (int i = 0; i < word.length(); i++) {
				if(board.checkSurroundingCells(startingLetter, cellNumber - i, direction).isEmpty()) {
					return false;
				};
			}
		} else {
			for (int i = 0; i < word.length(); i++) {
				board.checkSurroundingCells(startingLetter - i, cellNumber, direction);
			}
		}


		return false;
	}

}
