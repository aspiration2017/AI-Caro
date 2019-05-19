package model;

public interface IPlayer {
	public boolean move(Chessman chessman);
	public int getId();
	public Chessman getProfitableMove(int idCompetitor);	
}
