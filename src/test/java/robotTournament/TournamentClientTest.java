package robotTournament;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = TournamentClientApplication.class)
@AutoConfigureRestTestClient
class TournamentClientTest
{
	
	@LocalServerPort
	private int port;
	 
    @Autowired
    private TournamentClient client;

    @Autowired
    private RestTestClient tClient;

    @BeforeEach
    void setUp()
    {
        client.clientRobot = new PrisonerDefectRobot("testRobot");
    }

    @Test
    void testClientEndpointReturnsDecision()
    {
        tClient.get()
            .uri("/decision")
            .exchange()
            .expectBody(String.class)
            .isEqualTo("Defect");
    }

    @Test
    void testClientWithSameRobot()
    {
        client.clientRobot = new PrisonerSameRobot("testRobot");

        tClient.get()
            .uri("/decision?oppsPrevDecision=Cooperate")
            .exchange()
            .expectBody(String.class)
            .isEqualTo("Cooperate");

        tClient.get()
            .uri("/decision?oppsPrevDecision=Defect")
            .exchange()
            .expectBody(String.class)
            .isEqualTo("Defect");
    }

    @Test
    void testClientWithOppositeRobot()
    {
        client.clientRobot = new PrisonerOppositeRobot("testRobot");

        tClient.get()
            .uri("/decision?oppsPrevDecision=Cooperate")
            .exchange()
            .expectBody(String.class)
            .isEqualTo("Defect");

        tClient.get()
            .uri("/decision?oppsPrevDecision=Defect")
            .exchange()
            .expectBody(String.class)
            .isEqualTo("Cooperate");
    }
    
    @Test
    void testRemoteClientRobotFirstRoundDecision()
    {
        client.clientRobot = new PrisonerDefectRobot("testRobot");

        RemoteClientRobot remoteRobot =
            new RemoteClientRobot("remote", "localhost", String.valueOf(port));

        remoteRobot.setOppsPrevDecision("");
        assertEquals("Defect", remoteRobot.makeDecision());
    }

    @Test
    void testRemoteClientRobotWithSameRobot()
    {
        client.clientRobot = new PrisonerSameRobot("testRobot");

        RemoteClientRobot remoteRobot =
            new RemoteClientRobot("remote", "localhost", String.valueOf(port));

        remoteRobot.setOppsPrevDecision("Cooperate");
        assertEquals("Cooperate", remoteRobot.makeDecision());

        remoteRobot.setOppsPrevDecision("Defect");
        assertEquals("Defect", remoteRobot.makeDecision());
    }

    @Test
    void testRemoteClientRobotWithOppositeRobot()
    {
        client.clientRobot = new PrisonerOppositeRobot("testRobot");

        RemoteClientRobot remoteRobot =
            new RemoteClientRobot("remote", "localhost", String.valueOf(port));

        remoteRobot.setOppsPrevDecision("Cooperate");
        assertEquals("Defect", remoteRobot.makeDecision());

        remoteRobot.setOppsPrevDecision("Defect");
        assertEquals("Cooperate", remoteRobot.makeDecision());
    }

    @Test
    void testRemoteClientRobotReturnsError()
    {
        RemoteClientRobot remoteRobot =
            new RemoteClientRobot("remote", "localhost", "9999");

        remoteRobot.setOppsPrevDecision("Cooperate");
        assertEquals("Error", remoteRobot.makeDecision());
    }
}