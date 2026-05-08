package robotTournament;

public abstract class GameModifier extends Game
{

	Game wrappedGame;
	
	public GameModifier(Game wrappedGame)
	{
		super(wrappedGame.getGameRounds(), wrappedGame.getDelay());
		this.wrappedGame = wrappedGame;
	}
	
}
