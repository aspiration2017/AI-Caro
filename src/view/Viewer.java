package view;

import javax.swing.JFrame;

public class Viewer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Viewer() {
		super("AI - caro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new BoardView());
		pack();
		setSize(407, 430);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}
