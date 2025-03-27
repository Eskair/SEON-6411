package com.Game.Phases;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.controller.GameController;
import com.Game.model.Map;
import com.Game.model.Player;
import com.Game.model.Territory;
import com.Game.view.CommandPromptView;

// Dummy CommandPromptView that always returns "FINISH"
class DummyCommandPromptView extends CommandPromptView {
    @Override
    public String getPlayerOrder(String p_playerName, int p_remainingArmies) {
        return "FINISH";
    }
}

// Dummy GameController that returns a basic GameView
class DummyGameController extends GameController {
    private com.Game.view.GameView dummyView = new com.Game.view.GameView();
    @Override
    public com.Game.view.GameView getView() {
        return dummyView;
    }
}

public class IssueOrderPhaseTest {
    
    @Test
    public void testStartPhaseNoOrders() {
        IssueOrderPhase phase = new IssueOrderPhase();
        DummyGameController controller = new DummyGameController();
        DummyCommandPromptView promptView = new DummyCommandPromptView();
        List<Player> players = new ArrayList<>();
        Player p = new Player("Alice", 10);
        // Give player a territory for display.
        Territory t = new Territory("A", "Asia", 5);
        t.setOwner(p);
        p.addTerritory(t);
        players.add(p);
        Map gameMap = new Map();
        gameMap.addTerritory(t);
        
        String[] commandParts = new String[0];
        // Since getPlayerOrder always returns FINISH, no orders will be added.
        phase.StartPhase(controller, players, promptView, commandParts, gameMap);
        
        // After phase, player's orders list should be empty.
        assertTrue(p.getOrders().isEmpty());
    }
}
