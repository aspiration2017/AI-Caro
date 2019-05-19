package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Controller;
import model.BoardState;
import model.Chessman;
import model.HumanPlayer;

public class BoardView extends JPanel {
	private static final long serialVersionUID = 1L;
	public int width = 20;
	public int height = 20;
	public BoardState boardState = new BoardState(width, height);
	Controller controll = new Controller(this);
	static Image x;
	static Image o;
	boolean isPlayWithBot = false;
	boolean isStarted = false;
	boolean isEnd = false;
	static {
		try {
			x = ImageIO.read(new File("x.png"));
			o = ImageIO.read(new File("o.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BoardView() {
		setSize(400, 400);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (isStarted) 
					oneTurn(arg0);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (boardState.lastMove != null) {
			g.setColor(new Color(255, 153, 153));
			g.fillRect(boardState.lastMove.x*20, boardState.lastMove.y*20, 20, 20);
		}
		g.setColor(Color.black);
		if (isStarted) {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					g.setColor(Color.gray);
					g.drawRect(i * 20, j * 20, 20, 20);
				}
			}
			for (int i = 0; i < boardState.boardArr.length; i++) {
				for (int j = 0; j < boardState.boardArr[i].length; j++) {
					if (boardState.boardArr[i][j] == controll.player1.getId())
						g.drawImage(o, i*20, j*20, null);
					if (boardState.boardArr[i][j] == controll.player2.getId())
						g.drawImage(x, i*20, j*20, null);
				}
			}
		}
	}
	
	public void oneTurn(MouseEvent e) {
		Chessman chessman = getChessman(e);
		controll.oneTurnHuman(chessman);
		if (isPlayWithBot && controll.currentTurn != controll.player1)
			controll.oneTurnComputer();
		
	}
	
	public Chessman getChessman(MouseEvent e) {
		return new Chessman(e.getX()/20, e.getY()/20, controll.currentTurn.getId());
	}
	
	public void playWithHuman() {
		controll.player2 = new HumanPlayer(boardState);
		controll.currentTurn = controll.player1;
		isPlayWithBot = false;
		isStarted = true;
	}
	
	public void playWithComputer() {
		controll.playWithComputer();
		isPlayWithBot = true;
		isStarted = true;
	}

	public void gameOver() {
		String msg;
		if (controll.winner == null) 
			msg = "Draw!\nPlay again?";
		else
			msg = controll.winner.getClass().getSimpleName()+controll.winner.getId() + " won!\nPlay again?";
		
		int intOption = JOptionPane.showConfirmDialog(null, msg, "Game over!", 1);
		if (intOption == JOptionPane.YES_OPTION) {
			resetGame();
		} else
			System.exit(0);
	}
	
	public void resetGame() {
		controll.reset();
	}
}
