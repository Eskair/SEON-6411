package com.Game.Phases;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Map;
import com.Game.model.Player;
import com.Game.model.order.Order;
import com.Game.view.CommandPromptView;

// Dummy order that marks execution when run.
class DummyOrder extends Order {
    public boolean executed = false;
    public DummyOrder(Player p) {
        super(p);
    }
    @Override
    public void execute() {
        executed = true;
    }
}

// Dummy GameController returning a basic GameView.
class DummyGameController2 extends com.Game.controller.GameController {
    private com.Game.view.GameView dummyView = new com.Game.view.GameView();
    @Override
    public com.Game.view.GameView getView() {
        return dummyView;
    }
}

// Dummy CommandPromptView (not used during execution phase).
class DummyCommandPromptView2 extends CommandPromptView {
}

public class OrderExecutionPhaseTest {
    
    @Test
    public void testExecuteOrdersPhase() {
        OrderExecutionPhase phase = new OrderExecutionPhase();
        DummyGameController2 controller = new DummyGameController2();
        DummyCommandPromptView2 promptView = new DummyCommandPromptView2();
        List<Player> players = new ArrayList<>();
        Player p = new Player("Alice");
        // Add two dummy orders.
        DummyOrder order1 = new DummyOrder(p);
        DummyOrder order2 = new DummyOrder(p);
        p.getOrders().add(order1);
        p.getOrders().add(order2);
        players.add(p);
        Map gameMap = new Map();
        
        String[] commandParts = new String[0];
        phase.StartPhase(controller, players, promptView, commandParts, gameMap);
        
        // After execution phase, player's orders should be empty and both orders executed.
        assertTrue(p.getOrders().isEmpty());
        assertTrue(order1.executed);
        assertTrue(order2.executed);
    }
}
