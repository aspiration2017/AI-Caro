package model;

public class ComputerPlayer implements IPlayer {
	int id = 2;
	BoardState boardArr;

	public ComputerPlayer(BoardState boardArr) {
		super();
		this.boardArr = boardArr;
	}
	
	@Override
	public boolean move(Chessman chessman) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getId() {
		return id;
	}


}
