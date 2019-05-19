package model;

public class HumanPlayer implements IPlayer {
	int id;
	BoardState board;
	public static int countId = 0;

	public HumanPlayer(BoardState boardArr) {
		super();
		this.id = ++countId;
		this.board = boardArr;
	}

	@Override
	public boolean move(Chessman chessman) {
		if (!board.isValid(chessman)) {
			return false;
		}
		board.setPosition(chessman);
		board.lastMove = chessman;
		board.count++;
		return true;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Chessman getProfitableMove(int idCompetitor) {
		// TODO Auto-generated method stub
		return null;
	}
}
