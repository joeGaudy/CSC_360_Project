package robotTournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TournamentServer
{
	Map<String, Tournament> serverTournaments = new HashMap<>();
	Map<String, RemoteClientRobot> serverClients = new HashMap<>();
	Map<String, Map<String, RemoteMoveObserver>> viewers = new HashMap<>();
	
	private void addClient(RemoteClientRobot client) 
	{
	    serverClients.put(client.getName(),client);
	}
	
	public void addTournament(Tournament tournament) 
	{
		if (!serverTournaments.containsKey(tournament.getID()))
		{
			serverTournaments.put(tournament.getID(), tournament);
		}
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/tournaments")
	public ArrayList<String> viewTournaments() {
	    return new ArrayList<>(serverTournaments.keySet());
	}
	
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/registerClient/{username}/{IP}/{port}")
	public String registerServerClient(@PathVariable String username, 
			@PathVariable String IP, @PathVariable String port)
	{
		for(RemoteClientRobot client : serverClients.values())
		{
			if(client.getIP().equals(IP) && client.getPort().equals(port))
			{
				return "This IP and Port is already a registered combination";
			}
			else if (client.getName().equals(username))
			{
				return "This username has already been taken";
			}
		}
		
		RemoteClientRobot clientRobot = new RemoteClientRobot(username, IP, port);
		addClient(clientRobot);
		
		return "You have been successfully registered";
		
	}
	
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/runTournament/{tournamentID}")
	public String startTournament(@PathVariable String tournamentID)
	{
		Tournament tournament = serverTournaments.get(tournamentID);
		
		if (tournament == null)
		{
			return "This tournament does not exist";
		}
		
		serverTournaments.remove(tournamentID); 
		Robot winner = tournament.runTournament();
		
		return "The winner of this tournament is: " + winner.getName();
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/registerViewer/{IP}/{port}/{id}")
	public String registerViewer(@PathVariable String IP,
	        @PathVariable String port, @PathVariable String id) {
	    
	    String key = IP + ":" + port;
	    Tournament t = serverTournaments.get(id);
	    if (t == null) return "Tournament does not exist";
	    
	    viewers.putIfAbsent(id, new HashMap<>());
	    if (viewers.get(id).containsKey(key)) {
	        return "Already viewing this tournament";
	    }
	    
	    RemoteMoveObserver viewer = new RemoteMoveObserver(IP, port);
	    viewers.get(id).put(key, viewer);
	    t.getGame().registerMoveObserver(viewer);
	    return "Viewer successfully registered";
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/unregisterViewer/{IP}/{port}/{id}")
	public String unregisterViewer(@PathVariable String IP, @PathVariable String port, 
			@PathVariable String id) {
	    
	    String key = IP + ":" + port;
	    Map<String, RemoteMoveObserver> tournamentViewers = viewers.get(id);
	    Tournament t = serverTournaments.get(id);
	    
	    if (t == null || tournamentViewers == null) {
	        return "Viewer or tournament not found";
	    }
	    
	    RemoteMoveObserver viewer = tournamentViewers.get(key);
	    if (viewer != null) {
	        t.getGame().unregisterMoveObserver(viewer);
	        tournamentViewers.remove(key);
	    }
	    return "Viewer unregistered successfully";
	}
	
	@ResponseStatus(HttpStatus.OK)
    @GetMapping("/registerRobot/{tournamentID}/{username}")
	public String registerForTournament(@PathVariable String tournamentID, @PathVariable String username)
	{
		Tournament tournament = serverTournaments.get(tournamentID);
		RemoteClientRobot clientRobot = serverClients.get(username);
		
		if (tournament == null)
		{
			return "This tournament does not exist";
		}
		else if (clientRobot == null)
		{
			return "This user does not exist";
		}
		
		for(Robot client : tournament.getParticipants())
		{
			if(client.getName().equals(username))
			{
				return "This user is already registered for this tournament";
			}
		}
		
		tournament.addParticipant(clientRobot);
		
		return "You have successfully registered";
		
	}
	
}
