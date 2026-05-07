package viewerApp;

import javafx.application.Application;
import javafx.stage.Stage;
import tournamentModels.TournamentModel;
import tournamentModels.ViewTransitionalModel;

public class ViewerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String[] args = {};
        ViewerApplication.main(args);

        ViewerEndpoint endpoint = ViewerEndpoint.getInstance();

        TournamentModel model = new TournamentModel();
        ViewTransitionalModel vtm = new ViewTransitionalModel(stage, model, endpoint);

        vtm.showTournamentList();
        stage.show();
    }
}