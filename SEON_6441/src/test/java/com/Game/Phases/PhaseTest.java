package com.Game.Phases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

// Dummy StartupPhase class for testing setPhase
class DummyStartupPhase extends Phase {
    @Override
    public void StartPhase(com.Game.controller.GameController p_gameController, 
                           java.util.List<com.Game.model.Player> p_players, 
                           com.Game.view.CommandPromptView p_commandPromptView, 
                           String[] p_commandParts, 
                           com.Game.model.Map p_gameMap) {
        // empty implementation
    }
}

public class PhaseTest {
    
    // Dummy concrete subclass of Phase to test setPhase
    class DummyPhase extends Phase {
        @Override
        public void StartPhase(com.Game.controller.GameController p_gameController, 
                               java.util.List<com.Game.model.Player> p_players, 
                               com.Game.view.CommandPromptView p_commandPromptView, 
                               String[] p_commandParts, 
                               com.Game.model.Map p_gameMap) {
            // empty implementation
        }
    }
    
    @Test
    public void testSetPhaseIssueOrder() {
        DummyPhase phase = new DummyPhase();
        Phase next = phase.setPhase(PhaseType.ISSUE_ORDER);
        assertEquals(IssueOrderPhase.class, next.getClass());
    }
    
    @Test
    public void testSetPhaseOrderExecution() {
        DummyPhase phase = new DummyPhase();
        Phase next = phase.setPhase(PhaseType.ORDER_EXECUTION);
        assertEquals(OrderExecutionPhase.class, next.getClass());
    }
    
    @Test
    public void testSetPhaseStartup() {
        DummyPhase phase = new DummyPhase();
        Phase next = phase.setPhase(PhaseType.STARTUP);
        assertEquals(StartupPhase.class, next.getClass());
    }
    
    @Test
    public void testSetPhaseInvalid() {
        DummyPhase phase = new DummyPhase();
        // Expect a NullPointerException when passing null
        assertThrows(NullPointerException.class, () -> phase.setPhase(null));
    }
}
