/**
 * 
 */
package wordGame;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * @author Matthew Osborne
 *
 */
public class GameTest {

	Game g;
	Rack r;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create a new Game
		 g = new Game();
		
		r = Rack.getRack();
	}

	/**
	 * Test method for {@link wordGame.Game#checkValidity(wordGame.Play)}.
	 */
	@Test
	public void testCheckValidity() {
		r.setRack("HELLO");
		Assert.assertEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("B1", "ACROSS", "12345")));
		Assert.assertEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("B1", "DOWN", "12345")));
	}
	
	@Test
	public void testgetConnectingLetters() {
		r.setRack("HELLO");
		//Assert.assertEquals("[HE]", g.checkValidity(new Play("B1", "ACROSS", "12")));
		g.play(new Play("B1", "ACROSS", "12"));
		Assert.assertEquals("VALID WORDS: HELLO ", g.checkValidity(new Play("D1", "ACROSS", "345")));
		g.play(new Play("D1", "ACROSS", "345"));
		
	}

}