package robotTournament;

import org.springframework.web.client.RestClient;

public class RemoteMoveObserver implements MoveObserver 
{
	String IP;
	String port;
	
	 public RemoteMoveObserver(String IP, String port) {
		 	this.IP = IP;
	        this.port = port;
	    }

	@Override
	public void updateMove(String move)
	{
		// TODO Auto-generated method stub
		try {
			RestClient client = RestClient.create();
			String url = "http://" + this.getIP() + ":" + this.getPort();
			client.post()
					.uri(url+"/move")
					.body(move)
					.retrieve()
					.toEntity(Void.class);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String getIP() { return IP; }
	public String getPort() { return port; }
}
