package wordGame;

import java.util.ArrayList;

/**
 * A board holding the values for the game.
 * 
 * @author Matthew Osborne
 * @version 28/11/2019
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *  Stores a Board object and creates a board Map.
 * 
 * @author Matthew Osborne  
 *
 */

public class Board {

	// A board object
	private static Board board;
	// A map to store the grid
	private Map<String, Character> boardMap;
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

	private Board() {
		// If the number of columns is greater than the number in the alphabet array
		// throw an exception
		if (!(numberOfRows <= alphabet.length)) {
			throw new IllegalArgumentException(
					"" + numberOfRows + " is greater than the possible number of columns of " + alphabet.length);
		}

		// Create a new HashMap to store the Board Values
		boardMap = new HashMap<String, Character>(((numberOfRows * numberOfColumns)));

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
				boardMap.put(alphabet[letter] + ((i % numberOfRows) + 1), '+');
			} else {
				// Put the Cells and corresponding values in the HashMap
				boardMap.put(alphabet[letter] + ((i % numberOfRows) + 1), '.');

			}
		}
	}

	/**
	 * 
	 * Returns the Board object. If no Board class is found, make one.
	 * 
	 * @return The Board object
	 */
	public static Board getBoard() {
		// If there isn't a board object make one
		if (board == null) {
			board = new Board();
		}
		return board;
	}

	/**
	 * Clear the Board object
	 */
	protected static void clearBoard() {
		board = null;
	}

	/**
	 * 
	 * Used for testing the board.
	 * 
	 * @return The Map used as the board
	 */
	protected Map<String, Character> boardMapForTest() {
		return boardMap;
	}

	/**
	 *
	 * Turns the boardMap object into a string, which represents the board in an
	 * understandable way for play.
	 *
	 * @return The formatted string version of the boardMap
	 */
	@Override
	public String toString() {

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
			sb.append(" " + boardMap.get(alphabet[i % numberOfColumns] + (row - 1)));
//			System.out.println(alphabet[i % numberOfColumns] + (row - 1));
		}

		return sb.toString();
	}

	/**
	 * 
	 * Replace a cell in the board with another value. Used for testing.
	 * 
	 * @param letter     The letter of the cell being indexed
	 * @param cellNumber The number of the cell being indexed
	 * @param value      The value to replace the value of the cell
	 */
	public void replace(int letter, int cellNumber, char value) {
		// Replace the cell of the board with the given value
		boardMap.replace("" + alphabet[letter] + cellNumber, value);
	}

	/**
	 * 
	 * Returns the cell back to its instantiated state. Used for testing.
	 * 
	 * @param letter     The letter of the cell being indexed
	 * @param cellNumber The number of the cell being indexed
	 */
	public void resetCell(int letter, int cellNumber) {
		// Put the Cells and corresponding values in the HashMap
		if (Arrays.asList(specialCells).contains(alphabet[letter] + cellNumber) && setSpecialCells == true) {
			boardMap.put(alphabet[letter] + cellNumber, '+');
		} else {
			boardMap.put(alphabet[letter] + cellNumber, '.');
		}
	}

	/**
	 * 
	 * Returns the index of a letter passed to it.
	 * 
	 * @param c A letter
	 * @return The index of the letter
	 */
	public int getLetterIndex(char c) {
		// Return the int of the array that contains the character
		return Arrays.asList(alphabet).indexOf("" + c);
	}

	/**
	 * 
	 * Returns the letter at an index of the array alphabet.
	 * 
	 * @param i The index variable of a letter
	 * @return The letter at the index passed
	 */
	public String getLetter(int i) {

		// Check if the index is in the array
		if (i > alphabet.length) {
			throw new IllegalArgumentException("Index " + i
					+ " is greater than the number of letters in the array alphabet of " + alphabet.length);
		}

		// Return the letter of the int location
		return alphabet[i];
	}

	/**
	 * 
	 * Returns the value of the cell.
	 * 
	 * @param cell The cell index of the object being returned
	 * @return The value of the cell
	 */
	public char getCellValue(String cell) {
		
		if(!boardMap.containsKey(cell)) {
			return '!';
		}
		
		return boardMap.get(cell);
	}

	/**
	 * 
	 * Checks if the specified cell is in the board.
	 * 
	 * @param startingLetter The letter of the cell being indexed
	 * @param cellNumber     The number of the cell being indexed
	 * @param dir            The direction the play is going
	 * @return A boolean value of if the cell is in the board
	 */
	public Boolean checkIfInBoard(int startingLetter, int cellNumber, Direction dir) {

		// Checks that the cell is in the board

		if (dir == Direction.DOWN) {
			if ((cellNumber) > 0 && (cellNumber) < numberOfRows) {
				return true;
			}
		} else {
			if ((startingLetter) > -1 && (startingLetter) < numberOfColumns) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 	Searches for other letters on the board which are connected to the ones being played.
	 * 
	 * @param startingLetter The letter of the cell being indexed
	 * @param cellNumber     The number of the cell being indexed
	 * @param dir            The direction the play is going
	 * @return The letters found in the cells that have been searched
	 */
	public List<String> checkSurroundingCells(int startingLetter, int cellNumber, Direction dir) {

		List<String> letters = new ArrayList<String>();

		int holdStartingLetter = startingLetter;
		int holdCellNumber = cellNumber;

		// Checks for letters in each direction
		if (dir == Direction.DOWN) {
			cellNumber--;
			for (; board.checkIfInBoard(startingLetter, cellNumber, dir); cellNumber--) {
				if (getCellValue("" + getLetter(startingLetter) + cellNumber) == '.'
						|| getCellValue("" + getLetter(startingLetter) + cellNumber) == '+') {
					break;
				} else {
					letters.add("" + getCellValue("" + getLetter(startingLetter) + cellNumber));
				}
			}
			cellNumber = holdCellNumber;
			cellNumber++;
			for (; checkIfInBoard(startingLetter, cellNumber, dir); cellNumber++) {
				if (getCellValue("" + getLetter(startingLetter) + cellNumber) == '.'
						|| getCellValue("" + getLetter(startingLetter) + cellNumber) == '+') {
					break;
				} else {
					letters.add("" + getCellValue("" + getLetter(startingLetter) + cellNumber));
				}
			}
		} else {
			startingLetter++;
			for (; checkIfInBoard(startingLetter, cellNumber, dir); startingLetter++) {
				if (getCellValue("" + getLetter(startingLetter) + cellNumber) == '.'
						|| getCellValue("" + getLetter(startingLetter) + cellNumber) == '+') {
					break;
				} else {
					letters.add("" + getCellValue("" + getLetter(startingLetter) + cellNumber));
				}
			}
			startingLetter = holdStartingLetter;
			startingLetter--;
			for (; board.checkIfInBoard(startingLetter, cellNumber, dir); startingLetter--) {
				if (getCellValue("" + getLetter(startingLetter) + cellNumber) == '.'
						|| getCellValue("" + getLetter(startingLetter) + cellNumber) == '+') {
					break;
				} else {
					letters.add("" + getCellValue("" + getLetter(startingLetter) + cellNumber));
				}
			}
		}

		return letters;
	}

}
