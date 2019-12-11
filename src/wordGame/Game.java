package wordGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matthew Osborne, Akeel Saleem, Lewis Miller
 *
 */
public class Game implements Controller {

	protected Board board;
	protected Rack rack;
	private int totalScore = 0;
	private boolean firstPlay = true;
	private List<String> words;

	public Game() {
		board = Board.getBoard();
		rack = Rack.getRack();
		new FileIO();
		words = FileIO.getWords();
	}

	protected void newBoard() {
		Board.clearBoard();
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

	/**
	 * Refill the tile rack with randomly selected tiles.
	 * 
	 * @return the state of the tile rack
	 */
	@Override
	public String refillRack() {
		rack.refill();
		return rack.toString();
	}

	/**
	 * Return the current state of the game board and the contents of the player's
	 * tile rack as a String object.
	 * 
	 * @return the current state of the game board and the contents of the player's
	 *         tile rack as a String object
	 */
	@Override
	public String gameState() {
		return rack.toString() + "\n \n" + board.toString();
	}

	/**
	 * Place some tiles horizontally or vertically on a sequence of unoccupied cells
	 * in the game board.
	 * 
	 * The game engine will return the state of the game board after the tiles have
	 * been placed on the board as a String object.
	 * 
	 * @param play information about where to place which tile in the form of
	 *             STARTING_CELL; DIRECTION; LETTER_POSITIONS_IN_RACK, e.g. B3,
	 *             DOWN, 513
	 * 
	 * @return the state of the game board after the tiles have been placed on the
	 *         board as a String object
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

		// Sets the first play to false
		if (firstPlay) {
			firstPlay = false;
		}

		return gameState();
	}

	/**
	 * Calculate the points for the tiles placed on the game board in a intended
	 * play (i.e. move).
	 * 
	 * @param play information about where to place which tile in the form of
	 *             STARTING_CELL; DIRECTION; LETTER_POSITIONS_IN_RACK, e.g. B3,
	 *             DOWN, 513
	 * 
	 * @return a message showing the points to be scored as a String object
	 */
	@Override
	public String calculateScore(Play play) {

//		// Check if the word is valid
//		checkValidity(play);

		String cellLocation = play.cell();
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(cellLocation.charAt(0));
		// gets the cell number
		int cellNumber = Integer.parseInt(cellLocation.substring(1));
		// Get letter positions
		char[] letterPositions = play.letterPositionsInRack().toCharArray();

		int wordScore = 0;

		// Iterate over the letters played
		for (int i = 0; i < letterPositions.length; i++) {

			char letter = rack.pull(Integer.parseInt("" + letterPositions[i]));

			int score;
			// Set the correct score for the letter
			switch (letter) {
			case 'Z':
				score = 3;
				break;
			case 'Y':
				score = 3;
				break;
			case 'X':
				score = 3;
				break;
			case 'Q':
				score = 3;
				break;
			case 'N':
				score = 2;
				break;
			case 'M':
				score = 2;
				break;
			case 'K':
				score = 2;
				break;
			case 'J':
				score = 2;
				break;
			case 'G':
				score = 2;
				break;
			case 'B':
				score = 2;
				break;
			default:
				score = 1;
			}

			if (play.dir() == Direction.DOWN) {
				if (board.getCellValue("" + board.getLetter(startingLetter) + (cellNumber + i)) == '+') {
					score += score;
				}
			} else {
				if (board.getCellValue("" + board.getLetter(startingLetter + i) + (cellNumber)) == '+') {
					score += score;
				}
			}

			if (board.getCellValue(play.cell()) == '+') {
				score = score * 2;
			}

			// Add the score to the letter
			wordScore += score;
		}

		// Update the total score
		totalScore += wordScore;

		return "" + wordScore;
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
		// Get the surrounding letters on the board and from the rack
		List<String> letters = getLetters(play);

		if (letters.contains("invalidPosition")) {
			return "WORD MUST BE PLACED NEXT TO ANOTHER WORD";
		}

		// Get the possible words the letters could be
		List<String> possibleWords = checkPossibleWords(letters);

		// If there are no possible words
		if (possibleWords.isEmpty()) {
			return "INVALID FOR LETTERS:" + letters.toString().replace('[', ' ').replace(']', ' ');
		}

		// Checks if the possible word is valid
		for (String word : possibleWords) {
			if (!checkIfWordIsValid(word, play)) {
				return "INVALID FOR WORD: " + word.replace('[', ' ').replace(']', ' ')
						+ " CLASHES WITH ANOTHER WORD ON BOARD";
			}
		}

		return "VALID WORDS:" + possibleWords.toString().replace('[', ' ').replace(']', ' ');

	}

	/**
	 * 
	 * Find the letters that can be made from the New English words using the
	 * letters given.
	 * 
	 * @param Letters The letters used in the play and from the board
	 * 
	 * @return Possible words that can be made from the letters
	 */
	private List<String> checkPossibleWords(List<String> letters) {
		List<String> possibleWords = new ArrayList<String>();

		// Go through New English words and check if it is possible with the given
		// letters
		for (String w : words) {

			String[] wordStringArray = w.toUpperCase().split("");

			ArrayList<String> word = new ArrayList<String>(Arrays.asList(wordStringArray));

			// If there is a letter check if it works with the word
			if (!letters.isEmpty()) {
				if (word.containsAll(letters) && word.size() == letters.size()) {
					for (String l : letters) {
						word.remove(l);
					}
					if (word.size() == 0) {
						possibleWords.add(w.toUpperCase());
					}

				}
			}
		}
		return possibleWords;
	}

	/**
	 * 
	 * Get the letters from the board that connect to the Play being done.
	 * 
	 * @param play The current Play
	 * @return The connecting letters on the board
	 */
	private List<String> getLetters(Play play) {

		String cellLocation = play.cell();
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(cellLocation.charAt(0));
		// gets the cell number
		int cellNumber = Integer.parseInt(cellLocation.substring(1));
		// Gets the direction
		Direction dir = play.dir();

		List<String> letters = new ArrayList<String>();

		// Gets the connecting letters
		letters = board.checkSurroundingCells(startingLetter, cellNumber, dir);

		// If it is not the first play and there are no connecting letters return
		// invalid
		if (letters.isEmpty() && firstPlay != true) {
			letters.add("invalidPosition");
			return letters;
		}

		// Add the letters to the letters from the rack
		if (play.letterPositionsInRack() != null) {
			// creates a char[] array of letter
			char[] letterPositions = play.letterPositionsInRack().toCharArray();

			for (char c : letterPositions) {
				letters.add("" + rack.pull(Integer.parseInt("" + c)));
			}
		}
		return letters;
	}

	/**
	 * 
	 * Checks if a word is valid by checking if it clashes with any other words on
	 * the board
	 * 
	 * @param word The word being played
	 * @param play The current Play
	 * @return A boolean value of if the word can be played
	 */
	private Boolean checkIfWordIsValid(String word, Play play) {

		// Gets the direction
		Direction dir = play.dir();
		// The opposite of dir
		Direction notDir;
		// The starting cell
		String cellLocation = play.cell();
		// Gets the starting letter index
		int startingLetter = board.getLetterIndex(cellLocation.charAt(0));
		// gets the cell number
		int cellNumber = Integer.parseInt(cellLocation.substring(1));

		List<String> letters;

		// Assign notDir to the opposite direction of dir
		if (dir == Direction.DOWN) {
			notDir = Direction.ACROSS;
		} else {
			notDir = Direction.DOWN;
		}

		// Check for letters surrounding the letter position
		for (int i = 0; i < word.length(); i++) {
			if (dir == Direction.DOWN) {
				letters = board.checkSurroundingCells(startingLetter, cellNumber + i, notDir);
			} else {
				letters = board.checkSurroundingCells(startingLetter + i, cellNumber, notDir);
			}

			letters.add("" + word.charAt(i));

			if (checkPossibleWords(letters).isEmpty() && letters.size() > 1) {
				return false;
			}

		}

		return true;
	}

}
