package robotTournament;

import java.util.ArrayList;

public abstract class Tournament
{
	ArrayList<Robot> participants = new ArrayList<>();
	Game game;
	String ID;
	
	public Tournament(ArrayList<Robot> participants, Game game, String ID) {
		this.participants = participants;
		this.game = game;
		this.ID = ID;
	}
	
	public Game getGame() { return game; }
	public String getID() { return ID; }
	public ArrayList<Robot> getParticipants() { return participants; }
	
	public void addParticipant(Robot robot)
	{
		participants.add(robot);
	}
	
	public abstract Robot runTournament();
	
}
