package tournamentViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tournamentModels.TournamentModel;
import tournamentModels.ViewTransitionalModel;

public class TournamentsListViewController {

    @FXML
    private Button connectButton;

    @FXML
    private TextField ipTextBox;

    @FXML
    private TextField portTextBox;

    @FXML
    private Button refreshButton;

    @FXML
    private ListView<String> tournamentListView;

    private TournamentModel model;
    private ViewTransitionalModel vtm;

    public void setModel(TournamentModel model, ViewTransitionalModel vtm) {
        this.model = model;
        this.vtm = vtm;
        tournamentListView.setItems(model.getTournaments());
        
        tournamentListView.getSelectionModel().selectedItemProperty().addListener(
        	    (obs, oldVal, newVal) -> {
        	        if (newVal != null) {
        	            model.selectTournament(newVal);
        	            vtm.showMoveView();
        	        }
        	    }
        	);
    }

    @FXML
    void onClickConnect(ActionEvent event) {
        model.connect(ipTextBox.getText(), portTextBox.getText());
        
        ipTextBox.clear();
        portTextBox.clear();
    }

    @FXML
    void onClickRefresh(ActionEvent event) {
        model.fetchTournaments();
    }

    
}
