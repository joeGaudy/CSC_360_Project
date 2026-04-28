package robotTournament;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
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

	@Test
	void testViewTournaments()
	{
	    tClient.get()
	        .uri("/tournaments")
	        .exchange()
	        .expectBody(new ParameterizedTypeReference<List<String>>() {})
	        .value(list -> {
	            assertEquals(2, list.size());
	            assertTrue(list.contains("tournament1"));
	            assertTrue(list.contains("tournament2"));
	        });
	}

	@Test
	void testRegisterClientSuccess()
	{
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");
		
		assertThat(server.serverClients).containsKey("alice");
		assertThat(server.serverClients.get("alice").getIP()).isEqualTo("localhost");
		assertThat(server.serverClients.get("alice").getPort()).isEqualTo("8081");
	}

	@Test
	void testRegisterClientDuplicateUsername()
	{
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		tClient.get()
			.uri("/registerClient/alice/192.168.1.1/9000")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This username has already been taken");
	}

	@Test
	void testRegisterClientDuplicateIPPort()
	{
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		tClient.get()
			.uri("/registerClient/bob/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This IP and Port is already a registered combination");
	}

	@Test
	void testRegisterForTournamentSuccess()
	{
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

		Tournament tournament = server.serverTournaments.get("tournament1");
		assertThat(tournament.getParticipants()).hasSize(1);
		assertThat(tournament.getParticipants().get(0).getName()).isEqualTo("alice");
	}

	@Test
	void testRegisterForTournamentNotFound()
	{
		tClient.get()
			.uri("/registerClient/alice/localhost/8081")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("You have been successfully registered");

		tClient.get()
			.uri("/registerRobot/nonexistent/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This tournament does not exist");
	}

	@Test
	void testRegisterForTournamentClientNotFound()
	{
		tClient.get()
			.uri("/registerRobot/tournament1/nonexistent")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This user does not exist");
	}

	@Test
	void testRegisterForTournamentDuplicate()
	{
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

		tClient.get()
			.uri("/registerRobot/tournament1/alice")
			.exchange()
			.expectBody(String.class)
			.isEqualTo("This user is already registered for this tournament");
	}

	@Test
	void testStartTournamentSuccess()
	{
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

		tClient.get()
			.uri("/runTournament/tournament1")
			.exchange()
			.expectBody(String.class);

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