package robotTournament;

public class PrisonerDefectRobot extends Robot
{

	public PrisonerDefectRobot(String name)
	{
		super(name);
	}

	@Override
	public String makeDecision()
	{
		return "Defect";
	}

}
