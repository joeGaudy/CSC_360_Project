package robotTournament;

public class PrisonersDelimmaGame extends Game
{

	public PrisonersDelimmaGame(int gameRounds)
	{
		super(gameRounds);
	}

	@Override
	public void playGame(Robot p1, Robot p2)
	{
		p1.score = 0;
		p2.score = 0;
		p1.oppsPrevDecision = "";
		p2.oppsPrevDecision = "";
		
		for (int i = 0; i < gameRounds; i++) {
			String p1Decision = p1.makeDecision();
			String p2Decision = p2.makeDecision();
			p1.oppsPrevDecision = p2Decision;
			p2.oppsPrevDecision = p1Decision;
			
			if (p1Decision.equals("Cooperate") && p2Decision.equals("Cooperate")) {
				p1.score += 3;
				p2.score += 3;
				p1.totalScore += 3;
				p2.totalScore += 3;
			} 
			else if (p1Decision.equals("Defect") && p2Decision.equals("Defect")) {
				p1.score += 1;
				p2.score += 1;
				p1.totalScore += 1;
				p2.totalScore += 1;
			}
			else if (p1Decision.equals("Defect") && p2Decision.equals("Cooperate")) {
				p1.score += 5;
				p1.totalScore += 5;
			} 
			else {
				p2.score += 5;
				p2.totalScore += 5;
			}
			
			notifyMoveObserver(p1.name + ": " + p1Decision + " | " + p2.name + ": " + p2Decision);
		}
		
		notifyScoreObserver(p1.name + ": " + p1.score + " | " + p2.name + ": " + p2.score);
		
	}

}
