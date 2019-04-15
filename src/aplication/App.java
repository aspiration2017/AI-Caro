package aplication;

import model.BoardState;
import view.Viewer;

public class App {
	public static void main(String[] args) {
		new Viewer(new BoardState(5, 5));
	}
}
