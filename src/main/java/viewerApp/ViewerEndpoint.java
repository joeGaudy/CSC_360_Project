package viewerApp;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tournamentViews.MovesViewController;

@RestController
@RequestMapping("/")
public class ViewerEndpoint {

    private static ViewerEndpoint instance;
    private MovesViewController movesViewController;

    public ViewerEndpoint() {
        instance = this;
    }

    public ViewerEndpoint getInstance() {
        return instance;
    }

    public void setMovesViewController(MovesViewController controller) {
        this.movesViewController = controller;
    }

    @PostMapping("/move")
    public void receiveMove(@RequestBody String move) {
        if (movesViewController != null) {
            movesViewController.updateMove(move);
        }
    }
}