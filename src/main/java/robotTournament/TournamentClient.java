package robotTournament;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TournamentClient
{
	public Robot clientRobot;
    
    public TournamentClient() {
        this.clientRobot = new PrisonerDefectRobot("testRobot");
    }
    
    @GetMapping("/decision")
    public String makeDecision(@RequestParam(required = false, defaultValue = "") String oppsPrevDecision) {
        clientRobot.setOppsPrevDecision(oppsPrevDecision);
        return clientRobot.makeDecision();
    }
    
}
