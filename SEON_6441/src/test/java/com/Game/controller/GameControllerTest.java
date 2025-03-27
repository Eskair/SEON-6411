package com.Game.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;

public class GameControllerTest {

    @Test
    public void testHandleGamePlayerAddAndRemove() {
        GameController controller = new GameController();
        // Initially, the players list is empty.
        controller.handleGamePlayer("-add", "Alice");
        List<Player> players = controller.getPlayers();
        assertEquals(1, players.size());
        assertEquals("Alice", players.get(0).getName());

        // Adding the same player again should not add a duplicate.
        controller.handleGamePlayer("-add", "Alice");
        assertEquals(1, players.size());

        // Remove the player.
        controller.handleGamePlayer("-remove", "Alice");
        assertEquals(0, players.size());
    }

    @Test
    public void testGettersSetters() {
        GameController controller = new GameController();
        controller.setMapFilePath("map.txt");
        assertEquals("map.txt", controller.getMapFilePath());
        controller.setCurrentPhase(GameController.STARTUP_PHASE);
        assertEquals(GameController.STARTUP_PHASE, controller.getCurrentPhase());
        controller.setGameStarted(true);
        assertTrue(controller.isGameStarted());
    }

    @Test
    public void testGetViewAndCommandPromptView() {
        GameController controller = new GameController();
        assertNotNull(controller.getView());
        assertNotNull(controller.getCommandPromptView());
    }
}
