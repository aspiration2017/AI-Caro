package control;

import model.BoardState;
import model.Chessman;
import model.ComputerPlayer;
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
		if (boardState.count == 0) {
			player2.move(new Chessman(9, 9, player2.getId()));
			changeTurn();
			return;
		}
		Chessman c = player2.getProfitableMove(player1.getId());
		player2.move(c);
		if (checkEndForPlayer(currentTurn, c)) 
			winner = currentTurn;
		changeTurn();
		updateView();
		if (isOver()) 
			showWinner();
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
		if (winner instanceof ComputerPlayer)
			player2.move(new Chessman(9, 9, player2.getId()));
		winner = null;
		updateView();
	}
	
	public void playWithComputer() {
		player2 = new ComputerPlayer(boardState);
		player2.move(new Chessman(9, 9, player2.getId()));
		currentTurn = player1;
	}

}
