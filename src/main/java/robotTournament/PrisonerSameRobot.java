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
		if (oppsPrevDecision.equals("Cooperate")) {
			return "Cooperate";
		}
		else if (this.oppsPrevDecision.equals("Defect")) {
			return "Defect";
		}
		else {
			return "Cooperate";
		}
	}

}
