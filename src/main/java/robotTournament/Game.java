package robotTournament;

import java.util.ArrayList;

public abstract class Game
{
	int gameRounds;
	ArrayList<ScoreObserver> scoreObservers = new ArrayList<ScoreObserver>();
	ArrayList<MoveObserver> moveObservers = new ArrayList<MoveObserver>();
	
	public Game(int gameRounds) {
		this.gameRounds = gameRounds;
	}
	
	public abstract void playGame(Robot p1, Robot p2);
	
	public void registerScoreObserver(ScoreObserver scoreObs) {
		scoreObservers.add(scoreObs);
	}
	
	public void registerMoveObserver(MoveObserver moveObs) {
		moveObservers.add(moveObs);
	}
	
	public void unregisterScoreObserver(ScoreObserver scoreObs) {
		scoreObservers.remove(scoreObs);
	}
	
	public void unregisterMoveObserver(MoveObserver moveObs) {
		moveObservers.remove(moveObs);
	}
	
	public void notifyScoreObserver(String score) {
		for (ScoreObserver scoreObs : scoreObservers) {
			scoreObs.updateScore(score);
		}
	}
	
	public void notifyMoveObserver(String move) {
		for (MoveObserver moveObs : moveObservers) {
			moveObs.updateMove(move);
		}
	}
	
}
