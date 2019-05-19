package model;

public class ComputerPlayer implements IPlayer {
	int id = 2;
	BoardState board;

	public ComputerPlayer(BoardState boardArr) {
		super();
		this.board = boardArr;
	}
	
	@Override
	public boolean move(Chessman chessman) {
		board.setPosition(chessman);
		board.lastMove = chessman;
		board.count++;
		return true;
	}

	@Override
	public int getId() {
		return id;
	}
	
	public Chessman getProfitableMove(int idHumanPlayer) {
		int[][] scoreBoard = EvaluateBoard.evaluate(board.boardArr, id, idHumanPlayer);
		Chessman c = EvaluateBoard.getMax(scoreBoard);
		c.setPlayerID(id);
		System.out.println( c.x + "-" + c.y + ": " +scoreBoard[c.x][c.y]);
		return c;
	}
}
