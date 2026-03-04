package robotTournament;

public class PrisonerOppositeRobot extends Robot
{

	public PrisonerOppositeRobot(String name)
	{
		super(name);
	}

	@Override
	public String makeDecision()
	{
		if (this.getOppsPrevDecision().equals("Cooperate")) {
			return "Defect";
		}
		else if (this.getOppsPrevDecision().equals("Defect")) {
			return "Cooperate";
		}
		else {
			return "Defect";
		}
	}
	
}