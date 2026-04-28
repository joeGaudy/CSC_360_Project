package robotTournament;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tournamentModels.TournamentModel;
import tournamentModels.ViewTransitionalModel;
import viewerApp.ViewerEndpoint;

@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = TournamentServerApplication.class) 
@AutoConfigureRestTestClient
@ExtendWith(ApplicationExtension.class)
public class TestNetworkedListView
{
	@Autowired
	private TournamentServer server;
	
	@LocalServerPort
    private int port;
	private String ip = "127.0.0.1";
	
	TournamentModel model;
	ViewTransitionalModel vtm; 
	ViewerEndpoint endpoint;
	
	@Start  //Before
	private void start(Stage stage)
	{
		endpoint = new ViewerEndpoint();
		model = new TournamentModel();
		vtm = new ViewTransitionalModel(stage, model, endpoint);
		vtm.showTournamentList();
		stage.show();
		
		server.serverTournaments.clear();
		server.serverClients.clear();
		
		server.addTournament(new RoundRobinTournament(
			new ArrayList<>(), 
			new PrisonersDilemmaGame(5, 0), 
			"tournament1"
		));
		server.addTournament(new RoundRobinTournament(
			new ArrayList<>(), 
			new PrisonersDilemmaGame(10, 0), 
			"tournament2"
		));
	}
	
	 private void connectToServer(FxRobot robot, String ip, int port)
	 {
		robot.clickOn("#enterIpBox");
		robot.write(ip);
		robot.clickOn("#enterPortBox");
		robot.write(String.valueOf(port));
		robot.clickOn("#connectToServerButton");
	 }
	 
	 private void pressRefresh(FxRobot robot)
	 {
	    robot.clickOn("#tournamentRefreshButton");
	 }
	 
	 private void pressExit(FxRobot robot)
	 {
	    	robot.clickOn("#disconnectButton");
	 }
	 
	@SuppressWarnings("unchecked")
	public void checkIfListViewHasTournaments(FxRobot robot,ArrayList<String> tournamentsList)
	{
		ListView<Tournament> lv = (ListView<Tournament>) robot.lookup("#listOfTournaments")
	    .queryAll().iterator().next();
		
		int length = tournamentsList.size();
		
		Assertions.assertThat(lv).hasExactlyNumItems(length);
		
		for(String t: tournamentsList)
	    {
	     Assertions.assertThat(lv).hasListCell(t); 
	      
	    }
		
	}
	
	@SuppressWarnings("unchecked")
	public void clickTournament(FxRobot robot, int index)
    {
    	Platform.runLater(()->{
    		ListView<Tournament> lv = (ListView<Tournament>) robot.lookup("#listOfTournaments")
     		       .queryAll().iterator().next();
  		  lv.scrollTo(index);
  		  lv.getSelectionModel().clearAndSelect(index);
  	  });
  	  WaitForAsyncUtils.waitForFxEvents();
    	
    }
	
	@Test
	public void testNetworkedListView(FxRobot robot)
	{
		ArrayList<String> tournaments = server.viewTournaments();
		connectToServer(robot, ip, port);
    	checkIfListViewHasTournaments(robot, tournaments);
    	server.addTournament(new RoundRobinTournament(
    			new ArrayList<>(), 
    			new PrisonersDilemmaGame(10, 0), 
    			"tournament3"
    		));
    	pressRefresh(robot);
    	tournaments = server.viewTournaments();
    	checkIfListViewHasTournaments(robot, tournaments);
    	clickTournament(robot, 0);
    	assertEquals(tournaments.get(0), model.getSelectedTournamentID());
    	
    	
    	Node moveDisplay = robot.lookup("#tournamentMoves").query();
    	Node exitButton = robot.lookup("#disconnectButton").query();
    	
    	Assertions.assertThat(moveDisplay).isVisible();
    	Assertions.assertThat(exitButton).isVisible();
    	
    	pressExit(robot);
    	checkIfListViewHasTournaments(robot, tournaments);
    	assertEquals(null, model.getSelectedTournamentID());
	}
	 
}
