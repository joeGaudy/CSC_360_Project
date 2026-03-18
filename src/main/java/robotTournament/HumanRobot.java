package robotTournament;

import java.util.Scanner;

public class HumanRobot extends Robot
{

	private Scanner scanner;
	
	public HumanRobot(String name)
	{
		super(name);
		this.scanner = new Scanner(System.in);
	}

	@Override
	public String makeDecision()
	{
		if (!this.getOppsPrevDecision().equals(""))
		{
			System.out.println("Your openent's previous decision: " + this.getOppsPrevDecision());
		}
		System.out.println("Enter your decision with the first letter capitilized: ");
		String decision = scanner.nextLine();
		return decision;
	}

}
