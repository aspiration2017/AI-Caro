package model;

public class Chessman {
	int x, y, playerID;

	public Chessman(int x, int y, int player) {
		super();
		this.x = x;
		this.y = y;
		this.playerID = player;
	}

	public Chessman(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public String toString() {
		return playerID + ": " + x + "; " + y;
	}
}
