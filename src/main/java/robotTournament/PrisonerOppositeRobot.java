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
		if (this.oppsPrevDecision.equals("Cooperate")) {
			return "Defect";
		}
		else if (this.oppsPrevDecision.equals("Defect")) {
			return "Cooperate";
		}
		else {
			return "Defect";
		}
	}
	
}
