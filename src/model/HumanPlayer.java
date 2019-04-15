package model;

public class HumanPlayer implements IPlayer {
	int id;
	BoardState boardArr;

	public HumanPlayer(int id, BoardState boardArr) {
		super();
		this.id = id;
		this.boardArr = boardArr;
	}

	@Override
	public boolean move(Chessman chessman) {
		if (!boardArr.isValid(chessman)) {
			return false;
		}
		boardArr.setPosition(chessman);
		return true;
	}

	@Override
	public int getId() {
		return id;
	}
}
