package robotTournament;

public abstract class Robot
{
	String name;
	int score;
	int totalScore;
	String oppsPrevDecision;
	
	public Robot(String name) {
		this.name = name;
		this.score = 0;
		this.totalScore = 0;
		this.oppsPrevDecision = "";
	}
	
	public String getOppName(Robot opp) {
		return opp.name;
	}
	
	public abstract String makeDecision();
}

