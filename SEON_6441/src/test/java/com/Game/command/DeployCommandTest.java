package com.Game.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

// Minimal stub implementations for testing.
class StubPlayer extends Player {
    public StubPlayer(String name, int armies) {
        super(name, armies);
    }
    @Override
    public void addTerritory(Territory t) {
        super.addTerritory(t);
    }
}

class StubTerritory extends Territory {
    public StubTerritory(String name, String continent, int bonus) {
        super(name, continent, bonus);
    }
}

public class DeployCommandTest {

    @Test
    public void testGetCommandName() {
        StubPlayer player = new StubPlayer("Alice", 10);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        player.addTerritory(territory);
        DeployCommand command = new DeployCommand(player, territory, 5);
        assertEquals("deploy", command.getCommandName());
    }
    
    @Test
    public void testValidateValid() {
        StubPlayer player = new StubPlayer("Alice", 10);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        territory.setOwner(player);
        player.addTerritory(territory);
        DeployCommand command = new DeployCommand(player, territory, 5);
        assertTrue(command.validate());
    }
    
    @Test
    public void testValidateInvalidTerritory() {
        StubPlayer player = new StubPlayer("Alice", 10);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        // Player does not own territory.
        DeployCommand command = new DeployCommand(player, territory, 5);
        assertFalse(command.validate());
    }
    
    @Test
    public void testValidateInvalidArmies() {
        StubPlayer player = new StubPlayer("Alice", 3);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        territory.setOwner(player);
        player.addTerritory(territory);
        DeployCommand command = new DeployCommand(player, territory, 5);
        assertFalse(command.validate());
    }
    
    @Test
    public void testExecuteValid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        StubPlayer player = new StubPlayer("Alice", 10);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        territory.setOwner(player);
        player.addTerritory(territory);
        DeployCommand command = new DeployCommand(player, territory, 5);
        
        command.execute();
        assertEquals(1, player.getOrders().size());
        assertEquals(5, player.getNbrOfReinforcementArmies());
        String output = outContent.toString();
        assertTrue(output.contains("Alice issued deploy order: 5 armies to Wonderland"));
        
        System.setOut(originalOut);
    }
    
    @Test
    public void testExecuteInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        StubPlayer player = new StubPlayer("Alice", 10);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        // Player does not own territory.
        DeployCommand command = new DeployCommand(player, territory, 5);
        command.execute();
        assertEquals(0, player.getOrders().size());
        assertEquals(10, player.getNbrOfReinforcementArmies());
        String output = outContent.toString();
        assertTrue(output.contains("Deploy command failed validation."));
        
        System.setOut(originalOut);
    }
    
    @Test
    public void testUndo() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        StubPlayer player = new StubPlayer("Alice", 10);
        StubTerritory territory = new StubTerritory("Wonderland", "Continent", 5);
        DeployCommand command = new DeployCommand(player, territory, 5);
        command.undo();
        String output = outContent.toString();
        assertTrue(output.contains("Undo not implemented for deploy command yet."));
        
        System.setOut(originalOut);
    }
}
