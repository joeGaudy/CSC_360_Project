package robotTournament;

public abstract class Robot
{
	private String name;
	private int score;
	private int totalScore;
	private String oppsPrevDecision;
	
	public Robot(String name) {
		this.name = name;
		this.score = 0;
		this.totalScore = 0;
		this.oppsPrevDecision = "";
	}
	
	public String getName() { return name; }
	public int getScore() { return score; }
	public int getTotalScore() { return totalScore; }
	public String getOppsPrevDecision() { return oppsPrevDecision; }
	
	public void setScore(int score) { this.score = score; }
	public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
	public void setOppsPrevDecision(String oppsPrevDecision) { this.oppsPrevDecision = oppsPrevDecision; }
	
	public String getOppName(Robot opp) {
		return opp.getName();
	}
	
	public abstract String makeDecision();
}
