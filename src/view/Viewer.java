package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import control.Controller;
import model.BoardState;
import model.Chessman;
import model.IPlayer;

public class Viewer {
	Controller controll;
	BoardState board;
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public Viewer(BoardState board) {
		super();
		this.board = board;
		this.controll = new Controller(board, this);
		controll.run();
	}
	
	public void showBoard() {
		int i = 0;
		System.out.print("  ");
		for (int j = 0, k = 0; j < board.width; j++, k++) {
			if (k == 10)
				k = 0;
			System.out.print(k + "| ");
		}
		System.out.println();
		for (int[] arr : board.boardArr) {
			System.out.println(i + Arrays.toString(arr));
			i++;
			if (i == 10)
				i = 0;
		}
	}
	
	public void showTurn() {
		System.out.print("player" + controll.currentTurn.getId() + ": ");
	}

	public Chessman getChessman() throws IOException {
		StringTokenizer st = new StringTokenizer(input.readLine(), ";");
		try {
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			return new Chessman(x, y, controll.currentTurn.getId());
		} catch (Exception e) {
			showMsg("the input should be: integer1;integer2");
		}
		return null;
	}
	
	public void close() throws IOException {
		input.close();
	}

	public void showResult(IPlayer result) {
		if (result == null)
			showMsg("nobody wins!");
		else
			showMsg(result.getClass().getSimpleName()+result.getId()+" wins!");
	}
	
	public void showMsg(String line) {
		System.out.println(line);
	}
}
