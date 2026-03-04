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
		p1.setScore(0);
		p2.setScore(0);
		p1.setOppsPrevDecision("");
		p2.setOppsPrevDecision("");
		
		for (int i = 0; i < gameRounds; i++) {
			String p1Decision = p1.makeDecision();
			String p2Decision = p2.makeDecision();
			p1.setOppsPrevDecision(p2Decision);
			p2.setOppsPrevDecision(p1Decision);
			
			if (p1Decision.equals("Cooperate") && p2Decision.equals("Cooperate")) {
				p1.setScore(p1.getScore() + 3);
				p2.setScore(p2.getScore() + 3);
				p1.setTotalScore(p1.getTotalScore() + 3);
				p2.setTotalScore(p2.getTotalScore() + 3);
			} 
			else if (p1Decision.equals("Defect") && p2Decision.equals("Defect")) {
				p1.setScore(p1.getScore() + 1);
				p2.setScore(p2.getScore() + 1);
				p1.setTotalScore(p1.getTotalScore() + 1);
				p2.setTotalScore(p2.getTotalScore() + 1);
			}
			else if (p1Decision.equals("Defect") && p2Decision.equals("Cooperate")) {
				p1.setScore(p1.getScore() + 5);
				p1.setTotalScore(p1.getTotalScore() + 5);
			} 
			else {
				p2.setScore(p2.getScore() + 5);
				p2.setTotalScore(p2.getTotalScore() + 5);
			}
			
			notifyMoveObserver(p1.getName() + ": " + p1Decision + " | " + p2.getName() + ": " + p2Decision);
		}
		
		notifyScoreObserver(p1.getName() + ": " + p1.getScore() + " | " + p2.getName() + ": " + p2.getScore());
	}

}