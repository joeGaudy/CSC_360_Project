package tournamentModels;

import java.net.InetAddress;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import robotTournament.Tournament;

public class TournamentModel {

    private ObservableList<Tournament> tournaments = FXCollections.observableArrayList();
    
    private Tournament selectedTournament;
        
    private String viewerIP;
    private String viewerPort;
    private String viewerName;
    
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
        List<Tournament> list = client.get()
            .uri(uriBase+"/tournaments")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Tournament>>() {});
        tournaments.setAll(list);
    }

    public void selectTournament(Tournament t) {
        this.selectedTournament = t;
        client.get()
            .uri("/registerViewer/" + viewerIP + "/" + viewerPort + "/" + t.getID())
            .retrieve()
            .toEntity(Void.class);
    }

    public void unselectTournament() {
        if (selectedTournament != null && viewerName != null) {
            client.get()
                .uri(uriBase + "/unregisterViewer/" + viewerName + "/" + selectedTournament.getID())
                .retrieve()
                .toEntity(Void.class);
        }
        selectedTournament = null;
        viewerName = null;
    }

    public ObservableList<Tournament> getTournaments() {
        return tournaments;
    }

    public Tournament getSelectedTournament() {
        return selectedTournament;
    }
    
    public String getViewerIP() { return viewerIP; }
    public String getViewerPort() { return viewerPort; }
}