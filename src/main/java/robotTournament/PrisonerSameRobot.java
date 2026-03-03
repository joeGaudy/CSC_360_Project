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
		if (oppsPrevDecision.equals("Defect")) {
			return "Cooperate";
		}
		else if (this.oppsPrevDecision.equals("Cooperate")) {
			return "Defect";
		}
		else {
			return "Defect";
		}
	}

}
