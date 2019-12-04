/**
 * 
 */
package wordGame;

import java.util.Random;

/**
 * @author
 *
 */
public class Rack {

	// static rack object
	private static Rack rack;

	// rack can hold 5 letters
	private int rackSize = 5;

	// Character array of letters
	private char[] letters;

	// An array containing the possible column letters
	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * Constructor for a rack object
	 */
	private Rack() {
		// Declare character array of size 5 to hold letters
		letters = new char[rackSize];

		// Iterate over rackSize
		for (int i = 0; i < rackSize; i++) {
			letters[i] = ' ';
		}

		// Refill rack
		refill();
	}

	/**
	 * Method to create rack
	 */
	public static Rack getRack() {

		// If rack is null create a new one
		if (rack == null) {
			rack = new Rack();
		}
		return rack;
	}

	/**
	 * Refill rack with randomly generated letters once a letter has been used
	 */
	public void refill() {

		Random rand = new Random();

		// Iterate over rackSize
		for (int i = 0; i < rackSize; i++) {

			// If letter index is empty generate a random letter and fill the empty position
			if (letters[i] == ' ') {
				letters[i] = alphabet[rand.nextInt(alphabet.length)].charAt(0);
			}
		}
	}

	public void setRack(String rackLetters) {

		String[] rl = rackLetters.split("");

		if (letters.length > 5) {
			throw new IllegalArgumentException("Inputted number of letters is greater than max letter amount of 5");
		} else {
			for (int i = 0; i < letters.length; i++) {
				letters[i] = rl[i].charAt(0);
			}
		}
	}

	/**
	 * Return a string representation of the rack
	 */
	public String toString() {
		// Create a StringBuffer to append to
		StringBuffer sb = new StringBuffer();

		sb.append("|");
		for (int i = 0; i < rackSize; i++) {
			sb.append(letters[i] + "|");
		}

		return sb.toString();
	}

	/**
	 * Method to remove letter from rack when it`s been placed on board
	 */
	public char pop(int i) {
		// Set char c to be an array of letters -1
		char c = letters[i - 1];

		// Array of i-1 letters set to be empty
		letters[i - 1] = ' ';
		return c;
	}

	public char pull(int i) {
		// Set char c to be an array of letters -1
		char c = letters[i - 1];

		return c;
	}

	public int getIndex(String s) {

		for (int i = 0; i < letters.length; i++) {
			if (s.charAt(0) == letters[i]) {
				return i;
			}
		}
		return -1;
	}
}
