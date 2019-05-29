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
//		Chessman result = EvaluateBoard.getMax(EvaluateBoard.evaluate(board.boardArr, id, idHumanPlayer));
		Chessman result;
		int maxScore = -1;
		Chessman minMove;
		int[][] scoreMin;
		Chessman maxMove;
		int[][] scoreMax;
		int[][] boardState;
		int[][] boardScore = EvaluateBoard.evaluate(board.boardArr, id, idHumanPlayer);
		result = EvaluateBoard.getMax(boardScore);
		result.playerID = 2;
		if (boardScore[result.x][result.y] >= 30000) {
			return result;
		}
		for (int i = 0; i < board.boardArr.length; i++) {
			for (int j = 0; j < board.boardArr.length; j++) {
				if (boardScore[i][j] == 0)
					continue;
				boardState = EvaluateBoard.copy2DArray(board.boardArr);
				boardState[i][j] = id;
				scoreMin = EvaluateBoard.evaluate(boardState, idHumanPlayer, id);
				minMove = EvaluateBoard.getMax(scoreMin);
				if (scoreMin[minMove.x][minMove.y] >= 100000)
					continue;
				boardState[minMove.x][minMove.y] = idHumanPlayer;
				scoreMax = EvaluateBoard.evaluate(boardState, id, idHumanPlayer);
				maxMove = EvaluateBoard.getMax(scoreMax);
				int score = boardScore[i][j] + scoreMax[maxMove.x][maxMove.y];
				if (maxScore < score) {
					maxScore = score;
					result.x = i;
					result.y = j;
				}
			}
		}
		return result;
	}
}
