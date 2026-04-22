package tournamentModels;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tournamentViews.MovesViewController;
import tournamentViews.TournamentsListViewController;
import viewerApp.ViewerEndpoint;

public class ViewTransitionalModel {

    Stage stage;
    TournamentModel model;
    ViewerEndpoint viewerEndpoint;

    public ViewTransitionalModel(Stage stage, TournamentModel model, ViewerEndpoint viewerEndpoint) {
        this.stage = stage;
        this.model = model;
        this.viewerEndpoint = viewerEndpoint;
    }

    public Scene getTournamentListScene() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TournamentsListViewController.class
            .getResource("../tournamentViews/TournamentsListView.fxml"));
        try {
            Parent view = loader.load();
            TournamentsListViewController cont = loader.getController();
            cont.setModel(model, this);
            return new Scene(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showTournamentList() {
        stage.setScene(getTournamentListScene());
    }

    public Scene getMoveViewScene() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MovesViewController.class
            .getResource("../tournamentViews/TournamentMovesView.fxml"));
        try {
            Parent view = loader.load();
            MovesViewController cont = loader.getController();
            cont.setModel(model, this);
            
            viewerEndpoint.setMovesViewController(cont);
            
            return new Scene(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showMoveView() {
        stage.setScene(getMoveViewScene());
    }
}