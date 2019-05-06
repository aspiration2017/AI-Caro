package control;

import model.BoardState;
import model.Chessman;
import model.HumanPlayer;
import model.IPlayer;
import view.BoardView;

public class Controller {
	public BoardView view;
	public BoardState boardState;
	public IPlayer player1;
	public IPlayer player2;
	public IPlayer currentTurn;
	public IPlayer winner = null;
	public static boolean isEnd = false;

	public Controller(BoardView view) {
		this.view = view;
		this.boardState = view.boardState;
		this.player1 = new HumanPlayer(view.boardState);
		currentTurn = player1;
	}

	/**
	 * turn player
	 */
	public void oneTurnHuman(Chessman c) {
		if (move(c)) {
			if (checkEndForPlayer(currentTurn, c)) 
				winner = currentTurn;
			changeTurn();
			updateView();
			if (isOver()) 
				showWinner();
		}
	}
	
	/**
	 * turn computer
	 */
	public void oneTurnComputer() {
		// TODO Auto-generated method stub
		System.out.println("computer's turn!");
		changeTurn();
	}

	public boolean checkEndForPlayer(IPlayer player, Chessman chessman) {
		return boardState.checkEndForOnePlayer(player, chessman);
	}

	public void changeTurn() {
		if (currentTurn.getId() == player1.getId())
			currentTurn = player2;
		else
			currentTurn = player1;
	}

	public void updateView() {
		view.repaint();
	}

	public void showWinner() {
		view.gameOver();
	}

	public boolean move(Chessman c) {
		return currentTurn.move(c);
	}

	public boolean isOver() {
		return boardState.isOver() || winner != null;
	}
	
	public void reset() {
		boardState.resetBoard();
		currentTurn = player1;
		winner = null;
		updateView();
	}

}
