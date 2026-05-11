package viewerApp;

import javafx.application.Application;
import javafx.stage.Stage;
import robotTournament.CooperationBonusModifier;
import robotTournament.Game;
import robotTournament.PrisonerDefectRobot;
import robotTournament.PrisonerOppositeRobot;
import robotTournament.PrisonersDilemmaGame;
import robotTournament.RemoteMoveObserver;
import robotTournament.Robot;
import tournamentModels.TournamentModel;
import tournamentModels.ViewTransitionalModel;

public class Sprint4Demo extends Application {

    @Override
    public void start(Stage stage) {

    	String[] args = {};
        ViewerApplication.main(args);

        ViewerEndpoint endpoint = ViewerEndpoint.getInstance();

        TournamentModel model = new TournamentModel();
        ViewTransitionalModel vtm =
            new ViewTransitionalModel(stage, model, endpoint);

        vtm.showMoveView();
        stage.show();

        Game baseGame = new PrisonersDilemmaGame(5, 2000);
        RemoteMoveObserver viewer = new RemoteMoveObserver("127.0.0.1", "42069");

        baseGame.registerMoveObserver(viewer);

        Game game = new CooperationBonusModifier(baseGame, 50);
        game.registerMoveObserver(viewer);

        Robot r1 = new PrisonerOppositeRobot("Opposite Bot");
        Robot r2 = new PrisonerDefectRobot("Defect Bot");
        
        Thread gameThread = new Thread(() -> {
            game.playGame(r1, r2);

            endpoint.receiveMove("----- FINAL SCORES -----");
            endpoint.receiveMove(r1.getName() + " Final Score: " + r1.getScore());
            endpoint.receiveMove(r2.getName() + " Final Score: " + r2.getScore());
        });

        gameThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}