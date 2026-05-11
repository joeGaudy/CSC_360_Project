package robotTournament;

public abstract class GameModifier extends Game
{

	Game wrappedGame;
	
	public GameModifier(Game wrappedGame)
	{
		super(wrappedGame.getGameRounds(), wrappedGame.getDelay());
		this.wrappedGame = wrappedGame;
	}
	
	@Override
	public void registerMoveObserver(MoveObserver obs) {
	    super.registerMoveObserver(obs);
	    wrappedGame.registerMoveObserver(obs);
	}

	@Override
	public void registerScoreObserver(ScoreObserver obs) {
	    super.registerScoreObserver(obs);
	    wrappedGame.registerScoreObserver(obs);
	}
}
