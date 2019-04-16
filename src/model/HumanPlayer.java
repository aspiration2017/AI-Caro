package model;

public class HumanPlayer implements IPlayer {
	int id;
	BoardState board;
	public static int count = 0;

	public HumanPlayer(BoardState boardArr) {
		super();
		this.id = ++count;
		this.board = boardArr;
	}

	@Override
	public boolean move(Chessman chessman) {
		if (!board.isValid(chessman)) {
			return false;
		}
		board.setPosition(chessman);
		board.count++;
		return true;
	}

	@Override
	public int getId() {
		return id;
	}
}
