package control;

import java.io.IOException;

import model.BoardState;
import model.Chessman;
import model.ComputerPlayer;
import model.HumanPlayer;
import model.IPlayer;
import view.Viewer;

public class Controller {
	public Viewer view;
	public BoardState board;
	public IPlayer player1, player2;
	public IPlayer currentTurn;

	/**
	 * play 1vs1
	 */
	public Controller(BoardState board, Viewer view) {
		super();
		this.view = view;
		this.board = board;
		this.player1 = new HumanPlayer(board);
		this.player2 = new HumanPlayer(board);
		this.currentTurn = player1;
	}
	
	/**
	 * play with computer
	 */
	public Controller(Viewer view) {
		this.view = view;
		this.board = view.board;
		this.player1 = new HumanPlayer(board);
		this.player2 = new ComputerPlayer(board);
	}
	
	public void run() {
		updateView();
		boolean isEnd = false;
		Chessman chessman;
		while (!isOver()) {
			view.showTurn();
			try {
				chessman = view.getChessman();
				oneTurn(chessman);
				isEnd = checkEndForPlayer(currentTurn, chessman);
				changeTurn();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (isEnd) {
				changeTurn();
				showWinner(currentTurn);
				break;
			}
		}
		if (!isEnd) {
			view.showMsg("nobody wins!");
		}
		
		try {
			view.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOver() {
		return board.isOver();
	}
	
	public void showWinner(IPlayer player) {
		view.showMsg(player.getClass().getSimpleName()+player.getId()+" wins!");
	}
	
	public void oneTurn(Chessman c) {
		if (c == null || !move(c)) {
			view.showMsg("invalid move!");
			return;
		}
		updateView();
	}
	
	public boolean checkEndForPlayer(IPlayer player, Chessman chessman) {
		return board.checkEndForOnePlayer(player, chessman);
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

	public IPlayer getResult(int result) {
		if (result == -1)
			return null;
		return currentTurn;
	}

	public void updateView() {
		view.showBoard();
	}

}
