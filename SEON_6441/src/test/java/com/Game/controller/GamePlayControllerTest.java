package com.Game.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Map;
import com.Game.model.Player;
import com.Game.model.Territory;

// Dummy GameController subclass for GamePlayController testing.
class DummyGameControllerForGamePlay extends GameController {
    // Inherit default behavior.
}

public class GamePlayControllerTest {

    @Test
    public void testHandleAssignCountries() {
        // Create a map with 3 territories.
        Map gameMap = new Map();
        Territory t1 = new Territory("A", "Continent", 3);
        Territory t2 = new Territory("B", "Continent", 3);
        Territory t3 = new Territory("C", "Continent", 3);
        gameMap.addTerritory(t1);
        gameMap.addTerritory(t2);
        gameMap.addTerritory(t3);

        // Set up two players.
        List<Player> players = new ArrayList<>();
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        players.add(p1);
        players.add(p2);

        DummyGameControllerForGamePlay controller = new DummyGameControllerForGamePlay();
        controller.setGameMap(gameMap);
        controller.setPlayers(players);

        GamePlayController gameplayController = new GamePlayController(controller, gameMap, players);
        boolean result = gameplayController.handleAssignCountries();
        assertTrue(result);
        // Check that territories have been distributed.
            assertFalse(p1.getOwnedTerritories().isEmpty());
            assertFalse(p2.getOwnedTerritories().isEmpty());

        // Verify that each territory has been assigned an initial army count of 1.
        for (Player p : players) {
            for (Territory t : p.getOwnedTerritories()) {
                assertEquals(1, t.getNumOfArmies());
            }
        }
    }
    
    @Test
    public void testHandleReinforcement() {
        // Set up a map with one territory.
        Map gameMap = new Map();
        Territory t1 = new Territory("A", "Continent", 3);
        gameMap.addTerritory(t1);
        
        List<Player> players = new ArrayList<>();
        Player p = new Player("Alice");
        p.addTerritory(t1);
        players.add(p);
        
        DummyGameControllerForGamePlay controller = new DummyGameControllerForGamePlay();
        controller.setGameMap(gameMap);
        controller.setPlayers(players);
        controller.setGameStarted(true);
        
        GamePlayController gameplayController = new GamePlayController(controller, gameMap, players);
        gameplayController.handleReinforcement();
        // For one territory, reinforcements = max(3, 1/3) = 3.
        assertEquals(3, p.getNbrOfReinforcementArmies());
    }
    
    @Test
    public void testStartMainGameInsufficientPlayers() {
        Map gameMap = new Map();
        List<Player> players = new ArrayList<>();
        Player p = new Player("Alice");
        players.add(p);
        
        DummyGameControllerForGamePlay controller = new DummyGameControllerForGamePlay();
        controller.setPlayers(players);
        GamePlayController gameplayController = new GamePlayController(controller, gameMap, players);
        boolean result = gameplayController.startMainGame();
        assertFalse(result);
    }
    
    @Test
    public void testStartMainGameSufficientPlayers() {
        Map gameMap = new Map();
        List<Player> players = new ArrayList<>();
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        players.add(p1);
        players.add(p2);
        
        // Add territories to the map.
        Territory t1 = new Territory("A", "Continent", 3);
        Territory t2 = new Territory("B", "Continent", 3);
        gameMap.addTerritory(t1);
        gameMap.addTerritory(t2);
        
        DummyGameControllerForGamePlay controller = new DummyGameControllerForGamePlay();
        controller.setPlayers(players);
        controller.setGameMap(gameMap);
        GamePlayController gameplayController = new GamePlayController(controller, gameMap, players);
        boolean result = gameplayController.startMainGame();
        assertTrue(result);
        assertTrue(controller.isGameStarted());
        assertEquals(GameController.MAIN_GAME_PHASE, controller.getCurrentPhase());
    }
}
