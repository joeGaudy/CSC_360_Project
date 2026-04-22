package tournamentViews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import robotTournament.Tournament;
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
    private ListView<Tournament> tournamentListView;

    private TournamentModel model;
    private ViewTransitionalModel vtm;

    public void setModel(TournamentModel model, ViewTransitionalModel vtm) {
        this.model = model;
        this.vtm = vtm;
        tournamentListView.setItems(model.getTournaments());
    }

    @FXML
    void onClickConnect(ActionEvent event) {
        model.connect(ipTextBox.getText(), portTextBox.getText());
    }

    @FXML
    void onClickRefresh(ActionEvent event) {
        model.fetchTournaments();
    }

    @FXML
    void onClickTournament(MouseEvent event) {
        Tournament t = tournamentListView.getSelectionModel().getSelectedItem();
        if (t != null) {
            model.selectTournament(t);
            vtm.showMoveView();
        }
    }
}
