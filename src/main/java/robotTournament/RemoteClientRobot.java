package robotTournament;

import org.springframework.web.client.RestClient;

public class RemoteClientRobot extends Robot
{
	String IP;
	String port;

	public RemoteClientRobot(String name, String IP, String port)
	{
		super(name);
		this.IP = IP;
		this.port = port;
	}

	@Override
	public String makeDecision()
	{
		try {
			RestClient client = RestClient.create();
			String oppsPrevDecision = this.getOppsPrevDecision();
			String url = "http://"+this.getIP()+":"+this.getPort()+"/decision/{oppsPrevDecision}";
			String decision = client.get()
					.uri(url, oppsPrevDecision)
					.retrieve()
					.body(String.class);
			
			return decision;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "Error";
		}
	}
	
	public String getIP() { return IP; }
	public String getPort() { return port; }

}
