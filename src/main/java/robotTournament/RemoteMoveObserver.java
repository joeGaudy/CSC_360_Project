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
	 public void updateMove(String move) {
	     try {
	         RestClient client = RestClient.create();

	         String url = "http://" + getIP() + ":" + getPort() + "/move";

	         client.post()
	             .uri(url)
	             .body(move)
	             .retrieve()
	             .toBodilessEntity();

	     } catch (Exception e) {
	         System.err.println(e.getMessage());
	     }
	 }
	
	public String getIP() { return IP; }
	public String getPort() { return port; }
}
