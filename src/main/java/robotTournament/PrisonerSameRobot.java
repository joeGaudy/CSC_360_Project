package robotTournament;

public class PrisonerSameRobot extends Robot
{

	public PrisonerSameRobot(String name)
	{
		super(name);
	}

	@Override
	public String makeDecision()
	{
		if (getOppsPrevDecision().equals("Cooperate")) {
			return "Cooperate";
		}
		else if (this.getOppsPrevDecision().equals("Defect")) {
			return "Defect";
		}
		else {
			return "Cooperate";
		}
	}

}
