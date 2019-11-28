package wordGame;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Game implements Controller {

	// A map to store the grid
	private Map<String, Character> board;
	// Number of columns in the grid
	private int numberOfRows = 10;
	// Number of rows in the grid
	private int numberOfColumns = 10;
	// Sets special cells for 10x10 array
	private Boolean setSpecialCells = true;
	// An array containing the possible column letters
	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	// An array containing the special cell locations
	private String[] specialCells = { "E2", "F2", "D4", "G4", "D7", "G7", "E9", "F9" };

	public Game() {

		// If the number of columns is greater than the number in the alphabet array
		// throw an exception
		if (!(numberOfRows <= alphabet.length)) {
			throw new IllegalArgumentException(
					"" + numberOfRows + " is greater than the possible number of columns of " + alphabet.length);
		}

		// Create a new HashMap to store the Board Values
		board = new HashMap<String, Character>(((numberOfRows * numberOfColumns)));

		// Create a variable to start counting the number of lettered rows there are
		int letter = 0;

		// Fill the HashMap with all the possible cells
		for (int i = 0; i < (numberOfRows * numberOfColumns); i++) {
			if ((i % numberOfRows) == 0 && i != 0) {
				letter++;
			}
			// If the place has a multiplier put a +
			if (Arrays.asList(specialCells).contains(alphabet[letter] + ((i % numberOfRows) + 1))
					&& setSpecialCells == true) {
				board.put(alphabet[letter] + ((i % numberOfRows) + 1), '+');
			} else {
				// Put the Cells and corresponding values in the HashMap
				board.put(alphabet[letter] + ((i % numberOfRows) + 1), '.');
//				System.out.println(alphabet[letter] + ((i % numberOfRows) + 1));

			}
		}

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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a string format of the board
	 */
	@Override
	public String gameState() {

		// Get the start time of the function
		long startTime = (new Date()).getTime();

		// Create a StringBuffer to append to
		StringBuffer sb = new StringBuffer();

		// Row variable to keep track of what row is it on
		int row = 0;

		// If it is the first row add a line of letters corresponding to the columns
		if (row == 0) {
			// Add a double space
			sb.append("  ");
			for (int i = 0; i < numberOfColumns; i++) {
				// Add the Letters to the top row
				sb.append(" " + alphabet[i]);
			}
			// Increase the row count
			row++;

		}
		// Access the number of spaces in the grid
		for (int i = 0; i < (numberOfRows * numberOfColumns); i++) {
			if ((i % numberOfColumns) == 0) {
				if (row < 10) {
					// If the number of rows is less than 10 add an extra space then create a new
					// row and add the row number
					sb.append("\n" + " " + row);
				} else {
					// Create a new row and add the row number
					sb.append("\n" + row);
				}
				// Increase the row count
				row++;
			}
			// Put the value of the cell into the StringBuffer
			sb.append(" " + board.get(alphabet[i % numberOfColumns] + (row - 1)));
//			System.out.println(alphabet[i % numberOfColumns] + (row - 1));
		}

		// Work out the time it takes
		long endTime = (new Date()).getTime();
		long time = endTime - startTime;
		System.out.println("Time: " + endTime + " - " + startTime + " = " + time + "ms");

		// Return the StringBuffer as a String
		return sb.toString();
	}

	@Override
	public String play(Play play) {
		int startingLetter = Arrays.asList(alphabet).indexOf("" + play.cell().charAt(0));
		System.out.println(startingLetter);
		int cellNumber = Integer.parseInt(play.cell().substring(1));
		Direction dir = play.dir();
		char[] letterPositions = play.letterPositionsInRack().toCharArray();
		for (char c : letterPositions) {
			if (dir == Direction.DOWN) { // c currently doesn't do shit 
				board.replace("" + alphabet[startingLetter] + cellNumber, c); 
				cellNumber++;
			} else {
				board.replace("" + alphabet[startingLetter] + cellNumber, c);
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
