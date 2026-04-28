package tournamentModels;

import java.net.InetAddress;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TournamentModel {

	public ObservableList<String> tournaments = FXCollections.observableArrayList();
    
    public String selectedTournamentID;
        
    private String viewerIP;
    private String viewerPort;
    
    RestClient client = RestClient.create();
	
	String uriBase = "";
	
    public TournamentModel() {
        try {
            viewerIP = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            viewerIP = "localhost";
        }
        viewerPort = "8081";
    }

    public void connect(String ip, String port) {
        uriBase = "http://" + ip + ":" + port;
        fetchTournaments();
    }

    public void fetchTournaments() {
    	List<String> list = client.get()
    		    .uri(uriBase + "/tournaments")
    		    .retrieve()
    		    .body(new ParameterizedTypeReference<List<String>>() {});
        tournaments.setAll(list);
    }

    public void selectTournament(String id) {
        this.selectedTournamentID = id;

        client.get()
            .uri(uriBase + "/registerViewer/" + viewerIP + "/" + viewerPort + "/" + id)
            .retrieve()
            .body(String.class);
    }

    public void unselectTournament() {
        if (selectedTournamentID != null) {
            client.get()
                .uri(uriBase + "/unregisterViewer/" + viewerIP + "/" + viewerPort + "/" + selectedTournamentID)
                .retrieve()
                .body(String.class);
        }
        selectedTournamentID = null;
    }

    public ObservableList<String> getTournaments() {
        return tournaments;
    }

    public String getSelectedTournamentID() {
        return selectedTournamentID;
    }
    
    public String getViewerIP() { return viewerIP; }
    public String getViewerPort() { return viewerPort; }
}