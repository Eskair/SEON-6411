package com.Game.view;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Map;
import com.Game.model.Player;
import com.Game.model.Territory;

public class GameViewTest {
    
    @Test
    public void testDisplayWelcomeMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        GameView view = new GameView();
        view.displayWelcomeMessage();
        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Welcome to Warzone Game!"));
    }
    
    @Test
    public void testDisplayMap() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        
        // Create a simple map and players.
        Map gameMap = new Map();
        Territory t1 = new Territory("A", "Asia", 5);
        t1.setNumOfArmies(10);
        gameMap.addTerritory(t1);
        List<Player> players = new ArrayList<>();
        Player p = new Player("Alice");
        p.addTerritory(t1);
        players.add(p);
        
        GameView view = new GameView();
        view.displayMap(gameMap, players);
        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Continent: Asia"));
        assertTrue(output.contains("Territory: A"));
        assertTrue(output.contains("Alice"));
    }
    
    @Test
    public void testDisplayErrorAndMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(outContent));
        
        GameView view = new GameView();
        view.displayMessage("Test message");
        view.displayError("Test error");
        System.setOut(originalOut);
        System.setErr(originalErr);
        String output = outContent.toString();
        assertTrue(output.contains("Test message"));
        assertTrue(output.contains("Error: Test error"));
    }
}
