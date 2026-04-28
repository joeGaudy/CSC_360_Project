package robotTournament;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import tournamentModels.TournamentModel;
import tournamentModels.ViewTransitionalModel;
import viewerApp.ViewerApplication;
import viewerApp.ViewerEndpoint;

@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = ViewerApplication.class) 
@AutoConfigureRestTestClient
@ExtendWith(ApplicationExtension.class)
public class TestNetworkedMoveView
{
	@Autowired
	private ViewerEndpoint server;
	
	TournamentModel model;
	ViewTransitionalModel vtm; 
	
	@Start  //Before
	private void start(Stage stage)
	{
		model = new TournamentModel();
		vtm = new ViewTransitionalModel(stage, model, server);
		vtm.showMoveView();
		stage.show();
	}
	
	@Test
	public void testMoveView(FxRobot robot)
	{
		server.receiveMove("move1");

	    robot.sleep(500);

	    TextArea moveDisplay = robot.lookup("#tournamentMoves").queryAs(TextArea.class);

	    assertTrue(moveDisplay.getText().contains("move1"));
	    
	    server.receiveMove("Robot 1 is attacking Robot 2 we should probably intervene and stop the tournament");
	    
	    robot.sleep(500);
	    
	    assertTrue(moveDisplay.getText().contains("Robot 1 is attacking Robot 2 we should probably intervene and stop the tournament"));
	}
}
