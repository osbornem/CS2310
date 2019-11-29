package wordGame;

public class Game implements Controller {

	Board board;
	Rack rack;

	public Game() {
		board = Board.getBoard();
		rack = Rack.getRack();
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

	@Override
	public String play(Play play) {
		int startingLetter = board.getLetterIndex(play.cell().charAt(0));

		int cellNumber = Integer.parseInt(play.cell().substring(1));

		Direction dir = play.dir();

		char[] letterPositions = play.letterPositionsInRack().toCharArray();

		for (char c : letterPositions) {
			board.replace(startingLetter, cellNumber, rack.pop(Integer.parseInt("" + c))); // c currently doesn't do shit
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
