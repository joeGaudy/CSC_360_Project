package tournamentModels;

import java.util.ArrayList;

public class TestingTournamentModel extends TournamentModel
{
	public ArrayList<String> tournamentTestList = new ArrayList<>();
	
	@Override
	public void fetchTournaments() {
		tournaments.setAll(tournamentTestList);
	}
	
	@Override
	public void selectTournament(String id) {
        this.selectedTournamentID = id;
    }
	
	@Override
	public void connect(String ip, String port) {
        fetchTournaments();
    }
	
	@Override
	public void unselectTournament() {
		
	}
	
	public ArrayList<String> getTestTournaments() {
		return tournamentTestList;
		
	}
	
	public void addTestTournament(String id) {
		tournamentTestList.add(id);
	}
	
}
