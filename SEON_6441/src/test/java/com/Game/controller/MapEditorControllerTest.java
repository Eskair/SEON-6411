package com.Game.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Map;
import com.Game.model.Territory;
import com.Game.utils.MapLoader;
import com.Game.view.GameView;

// Dummy GameController subclass for testing MapEditorController.
class DummyGameControllerForEditor extends GameController {
    private String lastMessage;
    private String lastError;
    @Override
    public GameView getView() {
        return new GameView() {
            @Override
            public void displayMessage(String p_message) {
                lastMessage = p_message;
            }
            @Override
            public void displayError(String p_errorMessage) {
                lastError = p_errorMessage;
            }
        };
    }
    public String getLastMessage() { return lastMessage; }
    public String getLastError() { return lastError; }
}

public class MapEditorControllerTest {

    @Test
    public void testHandleEditContinentAdd() {
        DummyGameControllerForEditor controller = new DummyGameControllerForEditor();
        Map gameMap = new Map();
        MapLoader loader = new MapLoader();
        MapEditorController editor = new MapEditorController(controller, gameMap, loader);
        
        String[] commandParts = {"editcontinent", "-add", "Europe", "5"};
        editor.handleCommand(commandParts, "editcontinent", false);
        
        assertTrue(gameMap.getContinents().containsKey("Europe"));
        assertEquals(5, gameMap.getContinents().get("Europe").intValue());
        assertEquals("Continent added: Europe", controller.getLastMessage());
    }
    
    @Test
    public void testHandleEditCountryAdd() {
        DummyGameControllerForEditor controller = new DummyGameControllerForEditor();
        Map gameMap = new Map();
        // Add a continent so that addCountry can be performed.
        gameMap.addContinent("Europe", 5);
        MapLoader loader = new MapLoader();
        MapEditorController editor = new MapEditorController(controller, gameMap, loader);
        
        String[] commandParts = {"editcountry", "-add", "France", "Europe"};
        editor.handleCommand(commandParts, "editcountry", false);
        
        Territory france = gameMap.getTerritoryByName("France");
        assertNotNull(france);
        assertEquals("Europe", france.getContinent());
        assertEquals("Country added: France", controller.getLastMessage());
    }
    
    @Test
    public void testHandleGamePlayerCommandTransition() {
        DummyGameControllerForEditor controller = new DummyGameControllerForEditor();
        Map gameMap = new Map();
        MapLoader loader = new MapLoader();
        MapEditorController editor = new MapEditorController(controller, gameMap, loader);
        
        String[] commandParts = {"gameplayer", "-add", "Bob"};
        editor.handleCommand(commandParts, "gameplayer", false);
        // The gameplayer command should transition to the startup phase.
        // We check that the controller’s current phase has been set to STARTUP_PHASE.
        assertEquals(GameController.STARTUP_PHASE, controller.getCurrentPhase());
    }
}
