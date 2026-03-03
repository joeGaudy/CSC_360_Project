package robotTournament;

import java.util.ArrayList;

public abstract class Game
{
	int gameRounds;
	ArrayList<ScoreObserver> ScoreObservers = new ArrayList<ScoreObserver>();
	ArrayList<MoveObserver> MoveObservers = new ArrayList<MoveObserver>();
	
	public Game(int gameRounds) {
		this.gameRounds = gameRounds;
	}
	
	public abstract void playGame(Robot p1, Robot p2);
	
	public void registerScoreObserver(ScoreObserver ScoreObs) {
		ScoreObservers.add(ScoreObs);
	}
	
	public void registerMoveObserver(MoveObserver MoveObs) {
		MoveObservers.add(MoveObs);
	}
	
	public void unregisterScoreObserver(ScoreObserver ScoreObs) {
		ScoreObservers.remove(ScoreObs);
	}
	
	public void unregisterMoveObserver(MoveObserver MoveObs) {
		MoveObservers.remove(MoveObs);
	}
	
	public void notifyScoreObserver(String score) {
		for (ScoreObserver ScoreObserver : ScoreObservers) {
			ScoreObserver.updateScore(score);
		}
	}
	
	public void notifyMoveObserver(String move) {
		for (MoveObserver MoveObserver : MoveObservers) {
			MoveObserver.updateMove(move);
		}
	}
	
}
