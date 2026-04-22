package tournamentViews;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import tournamentModels.TournamentModel;
import tournamentModels.ViewTransitionalModel;

public class MovesViewController {

    @FXML
    private Button exitTournamentButton;

    @FXML
    private TextArea moveDisplay;

    private TournamentModel model;
    private ViewTransitionalModel vtm;

    public void setModel(TournamentModel model, ViewTransitionalModel vtm) {
        this.model = model;
        moveDisplay.setEditable(false);
    }

    public void updateMove(String move) {
        Platform.runLater(() -> moveDisplay.appendText(move + "\n"));
    }

    @FXML
    void onClickExitTournament(ActionEvent event) {
        model.unselectTournament();
        vtm.showTournamentList();
    }
}