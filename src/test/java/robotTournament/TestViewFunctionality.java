package robotTournament;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.Node;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tournamentModels.TestingTournamentModel;
import tournamentModels.ViewTransitionalModel;
import viewerApp.ViewerEndpoint;

@ExtendWith(ApplicationExtension.class)
public class TestViewFunctionality
{
	ViewTransitionalModel vtm;
	TestingTournamentModel model;
	ViewerEndpoint endpoint;
	
	@Start
    public void start(Stage stage) throws Exception 
    {
		endpoint = new ViewerEndpoint();
        model = new TestingTournamentModel();
        vtm = new ViewTransitionalModel(stage, model, endpoint);
        vtm.showTournamentList();
        stage.show();
    }
    
    private void connectToFakeServer(FxRobot robot, String ip, String port)
	{
	    robot.clickOn("#enterIpBox");
	    robot.write(ip);
	    robot.clickOn("#enterPortBox");
	    robot.write(port);
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
		
		for(String id: tournamentsList)
	    {
	     Assertions.assertThat(lv).hasListCell(id); 
	      
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
    public void testFuctionalListView(FxRobot robot)
    {
    	for (int i = 0; i < 4; i++) {
    		model.addTestTournament("tournament"+i);
    	}
    	connectToFakeServer(robot, "1234567", "891");
    	checkIfListViewHasTournaments(robot, model.getTestTournaments());
    	model.addTestTournament("tournament5");
    	pressRefresh(robot);
    	checkIfListViewHasTournaments(robot, model.getTestTournaments());
    	clickTournament(robot, 0);
    	
    	Node moveDisplay = robot.lookup("#tournamentMoves").query();
    	Node exitButton = robot.lookup("#disconnectButton").query();

    	Assertions.assertThat(moveDisplay).isVisible();
    	Assertions.assertThat(exitButton).isVisible();
    	
    	pressExit(robot);
    	checkIfListViewHasTournaments(robot, model.getTestTournaments());
    }
    
    
}
