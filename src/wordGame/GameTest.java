/**
 * 
 */
package wordGame;


import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;



/**
 * @author Matthew Osborne
 *
 */
public class GameTest {

	Game g;
	Rack r;
	
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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create a new Game
		 g = new Game();
		
		r = Rack.getRack();
	}
	
	@BeforeEach
	private void newBoard() {
		g.newBoard();
	}
	
	private HashMap<String, Character> getTestBoard() {
		HashMap<String, Character> testBoard = new HashMap<String, Character>((numberOfRows * numberOfColumns));

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
				testBoard.put(alphabet[letter] + ((i % numberOfRows) + 1), '+');
			} else {
				// Put the Cells and corresponding values in the HashMap
				testBoard.put(alphabet[letter] + ((i % numberOfRows) + 1), '.');
//						System.out.println(alphabet[letter] + ((i % numberOfRows) + 1));
			}
		}
		
		return testBoard;
	}
	
	/**
	 * Test method for {@link wordGame.Game#checkValidity(wordGame.Play)}.
	 */
	@Test
	public void testCheckValidity() {
		r.setRack("HELLO");
		
		long startTime = (new Date()).getTime(); 
		Assert.assertEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("B1", "ACROSS", "12345")));
		
		long endTime = (new Date()).getTime(); 
		long elapsedTime = endTime - startTime; 
		System.out.println("Time to check Validity = " + elapsedTime);
		
		Assert.assertEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("B1", "DOWN", "12345")));
		
		
		r.setRack("HELOA");
		Assert.assertNotEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("B1", "DOWN", "12345")));
		Assert.assertEquals("INVALID FOR LETTERS: H, E, L, O, A ", g.checkValidity(new Play("B1", "DOWN", "12345")));
	}
	
//	@Test
//	public void testgetConnectingLetters() {
//		
//
//
//		r.setRack("HELLO");
//		//Assert.assertEquals("[HE]", g.checkValidity(new Play("B1", "ACROSS", "12")));
//		g.play(new Play("A1", "ACROSS", "12"));
//		Assert.assertEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("C1", "ACROSS", "345")));
//		g.play(new Play("C1", "ACROSS", "345"));
//		r.setRack("HELLO");
//		
//		long startTime = (new Date()).getTime(); 
//		
//		Assert.assertEquals("INVALID FOR WORD: HELLO CLASHES WITH ANOTHER WORD ON BOARD", g.checkValidity(new Play("A2", "ACROSS", "12345")));
//		
//		long endTime = (new Date()).getTime(); 
//		long elapsedTime = endTime - startTime; 
//		System.out.println("Time to get connecting letters = " + elapsedTime);
//		
//	}
	
	@Test
	public void reRackTime() {
		
		long startTime = (new Date()).getTime(); 

		r.refill();
		
		long endTime = (new Date()).getTime(); 
		long elapsedTime = endTime - startTime; 
		System.out.println("Time to re-rack = " + elapsedTime);
	}
	
	@Test
	public void gameStateTime() {
		
		long startTime = (new Date()).getTime(); 

		g.gameState();
		
		long endTime = (new Date()).getTime(); 
		long elapsedTime = endTime - startTime; 
		System.out.println("Time to print game state = " + elapsedTime);
	}
	
	@Test
	public void calculateScoreTime() {
		
		r.setRack("HELLO");
		
		long startTime = (new Date()).getTime(); 
		
		Assert.assertEquals("6", g.calculateScore(new Play("A2", "ACROSS", "12345")));
		
		long endTime = (new Date()).getTime(); 
		long elapsedTime = endTime - startTime; 
		System.out.println("Time to calculate score = " + elapsedTime);
	}
	
	
	@Test
	public void testPlay() {
	
		HashMap<String, Character> testBoard = getTestBoard();
		
		testBoard.put("A1", 'T');
		testBoard.put("A2", 'E');
		testBoard.put("A3", 'S');
		testBoard.put("A4", 'T');
		
		r.setRack("TESTS");
		
		long startTime = (new Date()).getTime(); 
		
		g.play(new Play("A1", "DOWN", "1234"));
		
		long endTime = (new Date()).getTime(); 
		long elapsedTime = endTime - startTime; 
		System.out.println("Time to play = " + elapsedTime);
		
		Assert.assertEquals(testBoard, Board.getBoard().boardMapForTest());
		
	}

}
