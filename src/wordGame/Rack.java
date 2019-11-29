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

	private static Rack rack;

	private int rackSize = 5;

	private char[] letters;

	// An array containing the possible column letters
	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	private Rack() {
		letters = new char[rackSize];

		for (int i = 0; i < rackSize; i++) {
			letters[i] = ' ';
		}

		refill();
	}

	public static Rack getRack() {
		if (rack == null) {
			rack = new Rack();
		}
		return rack;
	}

	public void refill() {

		Random rand = new Random();

		for (int i = 0; i < rackSize; i++) {
			if (letters[i] == ' ') {
				letters[i] = alphabet[rand.nextInt(alphabet.length)].charAt(0);
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("|");
		for (int i = 0; i < rackSize; i++) {
			sb.append(letters[i] + "|");
		}

		return sb.toString();
	}

	public char pop(int i) {
		char c = letters[i - 1];
		letters[i - 1] = ' ';
		return c;
	}
}
