package com.Game.Phases;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.controller.GameController;
import com.Game.model.Map;
import com.Game.view.CommandPromptView;
import com.Game.view.GameView;

// Dummy GameView that captures error messages.
class DummyGameView extends GameView {
    public String lastError;
    @Override
    public void displayError(String p_errorMessage) {
        lastError = p_errorMessage;
    }
}

// Dummy GameController for testing StartupPhase.
class DummyGameControllerForStartup extends GameController {
    public String actionCalled;
    public String playerNameCalled;
    private GameView dummyView = new DummyGameView();
    @Override
    public GameView getView() {
        return dummyView;
    }
    @Override
    public void handleGamePlayer(String p_action, String p_playerName) {
         actionCalled = p_action;
         playerNameCalled = p_playerName;
    }
}

public class StartupPhaseTest {

    @Test
    public void testStartPhaseInsufficientArgs() {
        DummyGameControllerForStartup controller = new DummyGameControllerForStartup();
        String[] commandParts = {"gameplayer"}; // Less than 3 parts.
        StartupPhase phase = new StartupPhase();
        phase.StartPhase(controller, new ArrayList<>(), new CommandPromptView(), commandParts, new Map());
        
        DummyGameView view = (DummyGameView) controller.getView();
        assertEquals("Usage: gameplayer -add playerName OR gameplayer -remove playerName", view.lastError);
    }

    @Test
    public void testStartPhaseValid() {
        DummyGameControllerForStartup controller = new DummyGameControllerForStartup();
        String[] commandParts = {"gameplayer", "-add", "Alice"};
        StartupPhase phase = new StartupPhase();
        phase.StartPhase(controller, new ArrayList<>(), new CommandPromptView(), commandParts, new Map());
        
        assertEquals("-add", controller.actionCalled);
        assertEquals("Alice", controller.playerNameCalled);
    }
}
