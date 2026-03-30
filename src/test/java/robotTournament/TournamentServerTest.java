package robotTournament;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT,
		classes = TournamentServerApplication.class)
@AutoConfigureRestTestClient
class TournamentServerTest
{
	@Autowired
	private TournamentServer server;

	@Autowired
	private RestTestClient tClient;

	@BeforeEach
	void setUp()
	{
		// Clear server state before each test
		server.serverTournaments.clear();
		server.serverClients.clear();
		
		// Add test tournaments
		server.addTournament(new RoundRobinTournament(
			new ArrayList<>(), 
			new PrisonersDilemmaGame(5), 
			"tournament1"
		));
		server.addTournament(new RoundRobinTournament(
			new ArrayList<>(), 
			new PrisonersDilemmaGame(10), 
			"tournament2"
		));
	}

	@Test
	void testViewTournaments()
	{
		tClient.get()
			.uri("/tournaments")
			.exchange()
			.expectBody(Map.class);
	}

	@Test
	void testRegisterClientSuccess()
	{
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");
		
		// Verify client was added
		assertThat(server.serverClients).containsKey("alice");
		assertThat(server.serverClients.get("alice").getIP()).isEqualTo("localhost");
		assertThat(server.serverClients.get("alice").getPort()).isEqualTo("8081");
	}

	@Test
	void testRegisterClientDuplicateUsername()
	{
		// Register first client
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		// Try to register same username
		tClient.get()
			.uri("/registerClient/alice/192.168.1.1/9000")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This username has already been taken");
	}

	@Test
	void testRegisterClientDuplicateIPPort()
	{
		// Register first client
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		// Try to register different username, same IP/Port
		tClient.get()
			.uri("/registerClient/bob/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This IP and Port is already a registered combination");
	}

	@Test
	void testRegisterForTournamentSuccess()
	{
		// Register a client first
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		// Register client for tournament
		tClient.get()
			.uri("/registerRobot/tournament1/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have successfully registered");

		// Verify client is in tournament
		Tournament tournament = server.serverTournaments.get("tournament1");
		assertThat(tournament.getParticipants()).hasSize(1);
		assertThat(tournament.getParticipants().get(0).getName()).isEqualTo("alice");
	}

	@Test
	void testRegisterForTournamentNotFound()
	{
		// Register a client first
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		// Try to register for non-existent tournament
		tClient.get()
			.uri("/registerRobot/nonexistent/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This tournament does not exist");
	}

	@Test
	void testRegisterForTournamentClientNotFound()
	{
		// Try to register non-existent client for tournament
		tClient.get()
			.uri("/registerRobot/tournament1/nonexistent")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This user does not exist");
	}

	@Test
	void testRegisterForTournamentDuplicate()
	{
		// Register a client first
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		// Register client for tournament
		tClient.get()
			.uri("/registerRobot/tournament1/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have successfully registered");

		// Try to register same client again
		tClient.get()
			.uri("/registerRobot/tournament1/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This user is already registered for this tournament");
	}

	@Test
	void testStartTournamentSuccess()
	{
		// Register clients and tournament
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		tClient.get()
			.uri("/registerRobot/tournament1/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have successfully registered");

		// Start tournament
		tClient.get()
			.uri("/runTournament/tournament1")
			.exchange()
			.expectBody(String.class);

		// Verify tournament was removed from registration (no longer in hashmap)
		assertThat(server.serverTournaments).doesNotContainKey("tournament1");
	}

	@Test
	void testStartTournamentNotFound()
	{
		tClient.get()
			.uri("/runTournament/nonexistent")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This tournament does not exist");
	}

}