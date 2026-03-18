package robotTournament;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class RobotTournamentTest
{
	PrisonerDefectRobot defectRobot;
	PrisonerOppositeRobot oppositeRobot;
	PrisonerSameRobot sameRobot;
	PrisonersDelimmaGame game;

	@BeforeEach
	void setUp()
	{
		defectRobot = new PrisonerDefectRobot("Defector");
		oppositeRobot = new PrisonerOppositeRobot("Contrarian");
		sameRobot = new PrisonerSameRobot("Mimic");
		game = new PrisonersDelimmaGame(5);
	}

	@AfterEach
	void tearDown() throws IOException
	{
		Files.deleteIfExists(Paths.get("test_moves.txt"));
		Files.deleteIfExists(Paths.get("test_scores.txt"));
	}

	@Test
	void testDefectRobotAlwaysDefects()
	{
		assertEquals("Defect", defectRobot.makeDecision());
	}

	@Test
	void testOppositeRobotDefectsFirst()
	{
		assertEquals("Defect", oppositeRobot.makeDecision());
	}

	@Test
	void testOppositeRobotPrevDecDefect()
	{
		oppositeRobot.setOppsPrevDecision("Defect");
		assertEquals("Cooperate", oppositeRobot.makeDecision());
	}

	@Test
	void testOppositeRobotPrevDecCooperate()
	{
		oppositeRobot.setOppsPrevDecision("Cooperate");
		assertEquals("Defect", oppositeRobot.makeDecision());
	}

	@Test
	void testSameRobotCooperateFirst()
	{
		assertEquals("Cooperate", sameRobot.makeDecision());
	}

	@Test
	void testSameRobotMimicDefect()
	{
		sameRobot.setOppsPrevDecision("Defect");
		assertEquals("Defect", sameRobot.makeDecision());
	}

	@Test
	void testSameRobotMimicCooperate()
	{
		sameRobot.setOppsPrevDecision("Cooperate");
		assertEquals("Cooperate", sameRobot.makeDecision());
	}

	@Test
	void testBothCooperateScoring()
	{
		PrisonerSameRobot r1 = new PrisonerSameRobot("R1");
		PrisonerSameRobot r2 = new PrisonerSameRobot("R2");
		game.playGame(r1, r2);
		assertEquals(15, r1.getScore());
		assertEquals(15, r2.getScore());
	}

	@Test
	void testBothDefectScoring()
	{
		PrisonerDefectRobot r1 = new PrisonerDefectRobot("R1");
		PrisonerDefectRobot r2 = new PrisonerDefectRobot("R2");
		game.playGame(r1, r2);
		assertEquals(5, r1.getScore());
		assertEquals(5, r2.getScore());
	}

	@Test
	void testOneDefectScoring()
	{
		PrisonerDefectRobot r1 = new PrisonerDefectRobot("R1");
		PrisonerOppositeRobot r2 = new PrisonerOppositeRobot("R2");
		game.playGame(r1, r2);
		assertEquals(21, r1.getScore());
		assertEquals(1, r2.getScore());
	}

	@Test
	void testEmptyTournamentReturnsNull()
	{
		ArrayList<Robot> robots = new ArrayList<>();
		RoundRobinTournament tournament = new RoundRobinTournament(robots, game, "0");
		assertNull(tournament.runTournament());
	}

	@Test
	void testRoundRobinDefectorWins()
	{
		ArrayList<Robot> robots = new ArrayList<>();
		robots.add(defectRobot);
		robots.add(oppositeRobot);
		robots.add(sameRobot);

		RoundRobinTournament tournament = new RoundRobinTournament(robots, game, "1");
		Robot winner = tournament.runTournament();

		assertEquals("Defector", winner.getName());
	}
	
	@Test
	void testRoundRobinWinnerNotFirstInList()
	{
	    ArrayList<Robot> robots = new ArrayList<>();
	    robots.add(oppositeRobot);  
	    robots.add(sameRobot);
	    robots.add(defectRobot);    

	    RoundRobinTournament tournament = new RoundRobinTournament(robots, game, "2");
	    Robot winner = tournament.runTournament();

	    assertEquals("Defector", winner.getName());
	}

	@Test
	void testMoveLoggingCreatesFile() throws IOException
	{
		game.registerMoveObserver(new MoveLoggingSystem("test_moves.txt"));
		game.playGame(defectRobot, oppositeRobot);
		assertTrue(Files.exists(Paths.get("test_moves.txt")));
	}

	@Test
	void testScoreLoggingCreatesFile() throws IOException
	{
		game.registerScoreObserver(new ScoreLoggingSystem("test_scores.txt"));
		game.playGame(defectRobot, oppositeRobot);
		assertTrue(Files.exists(Paths.get("test_scores.txt")));
	}

	@Test
	void testMoveLoggingWritesContent() throws IOException
	{
		game.registerMoveObserver(new MoveLoggingSystem("test_moves.txt"));
		game.playGame(defectRobot, oppositeRobot);
		String content = Files.readString(Paths.get("test_moves.txt"));
		assertFalse(content.isEmpty());
	}

	@Test
	void testScoreLoggingWritesContent() throws IOException
	{
		game.registerScoreObserver(new ScoreLoggingSystem("test_scores.txt"));
		game.playGame(defectRobot, oppositeRobot);
		String content = Files.readString(Paths.get("test_scores.txt"));
		assertFalse(content.isEmpty());
	}
}