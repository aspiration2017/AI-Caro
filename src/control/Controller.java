package control;

import java.io.IOException;

import model.BoardState;
import model.Chessman;
import model.HumanPlayer;
import model.IPlayer;
import view.Viewer;

public class Controller {
	public Viewer view;
	public BoardState board;
	public IPlayer player1, player2;
	public IPlayer currentTurn;

	public Controller(BoardState board, Viewer view) {
		super();
		this.view = view;
		this.board = board;
		this.player1 = new HumanPlayer(1, board);
		this.player2 = new HumanPlayer(2, board);
		this.currentTurn = player1;
	}
	
	public void run() {
		updateView();
		int result;
		while ((result = checkEnd()) == 0) {
			view.showTurn();
			try {
				oneTurn(view.getChessman());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		changeTurn();
		showResult(result);
		try {
			view.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void oneTurn(Chessman c) {
		if (c == null || !move(c)) {
			view.showMsg("invalid move!");
			return;
		}
		changeTurn();
		updateView();
	}

	public boolean move(Chessman c) {
		return currentTurn.move(c);
	}

	public void changeTurn() {
		if (currentTurn.getId() == player1.getId())
			currentTurn = player2;
		else
			currentTurn = player1;
	}

	public int checkEnd() {
		return board.checkEnd();
	}

	public void showResult(int result) {
		view.showResult(getResult(result));
	}

	public IPlayer getResult(int result) {
		if (result == -1)
			return null;
		return currentTurn;
	}

	public void updateView() {
		view.showBoard();
	}

}
