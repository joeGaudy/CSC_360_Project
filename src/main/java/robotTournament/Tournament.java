package robotTournament;

import java.util.ArrayList;

public abstract class Tournament
{
	ArrayList<Robot> participants = new ArrayList<>();
	Game game;
	
	public Tournament(ArrayList<Robot> participants, Game game) {
		this.participants = participants;
		this.game = game;
	}
	
	public abstract Robot runTournament();
}
