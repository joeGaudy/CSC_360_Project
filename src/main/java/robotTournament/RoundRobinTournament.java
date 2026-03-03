package robotTournament;

import java.util.ArrayList;

public class RoundRobinTournament extends Tournament
{

	public RoundRobinTournament(ArrayList<Robot> participants, Game game)
	{
		super(participants, game);
	}

	@Override
	public Robot runTournament()
	{
		if (participants.isEmpty()) return null;
		
		int numPlayers = participants.size();
		
		
		for (int i = 0; i < numPlayers; i++) {
			participants.get(i).totalScore = 0;
		}
		
		for (int i = 0; i < numPlayers; i++) {
            for (int j = i + 1; j < numPlayers; j++) {
            	game.playGame(participants.get(i), participants.get(j));
            }
        }
		
		Robot winner = participants.get(0);
		for (Robot robot : participants) {
		    if (robot.totalScore > winner.totalScore) {
		        winner = robot;
		    }
		}
		
		return winner;
	}

}
