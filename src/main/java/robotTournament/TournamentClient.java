package robotTournament;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TournamentClient
{
private Robot clientRobot;
    
    public TournamentClient() {
        this.clientRobot = new PrisonerDefectRobot("testRobot");
    }
    
    @GetMapping("/decision/{oppsPrevDecision}")
    public String makeDecision(@PathVariable String oppsPrevDecision) {
        clientRobot.setOppsPrevDecision(oppsPrevDecision);
        return clientRobot.makeDecision();
    }
    
}
