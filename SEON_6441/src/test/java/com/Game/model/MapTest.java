package com.Game.model;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MapTest {

    @Test
    public void testDefaultConstructor() {
        Map gameMap = new Map();
        assertNotNull(gameMap.getTerritoryList());
        assertTrue(gameMap.getTerritoryList().isEmpty());
        assertNotNull(gameMap.getContinents());
        assertTrue(gameMap.getContinents().isEmpty());
    }
    
    @Test
    public void testAddAndGetTerritory() {
        Map gameMap = new Map();
        Territory t1 = new Territory("A", "X", 5);
        gameMap.addTerritory(t1);
        assertEquals(t1, gameMap.getTerritoryByName("A"));
    }
    
    @Test
    public void testAddContinentAndRemoveContinent() {
        Map gameMap = new Map();
        gameMap.addContinent("Asia", 7);
        assertTrue(gameMap.getContinents().containsKey("Asia"));
        // Add territory belonging to continent Asia
        Territory t1 = new Territory("India", "Asia", 5);
        gameMap.addTerritory(t1);
        // Remove continent; expect both the continent and its territories to be removed
        gameMap.removeContinent("Asia");
        assertFalse(gameMap.getContinents().containsKey("Asia"));
        assertNull(gameMap.getTerritoryByName("India"));
    }
    
    @Test
    public void testAddCountry() {
        Map gameMap = new Map();
        gameMap.addContinent("Europe", 5);
        gameMap.addCountry("France", "Europe");
        Territory t = gameMap.getTerritoryByName("France");
        assertNotNull(t);
        assertEquals("Europe", t.getContinent());
    }
    
    @Test
    public void testRemoveCountryAndNeighbors() {
        Map gameMap = new Map();
        Territory t1 = new Territory("Country1", "Continent1", 3);
        Territory t2 = new Territory("Country2", "Continent1", 3);
        gameMap.addTerritory(t1);
        gameMap.addTerritory(t2);
        // Set neighbor relationships manually
        t1.addNeighbor(t2);
        t2.addNeighbor(t1);
        // Remove country t1; it should also be removed from t2's neighbor list
        gameMap.removeCountry("Country1");
        assertNull(gameMap.getTerritoryByName("Country1"));
        assertFalse(t2.getNeighborList().contains(t1));
    }
    
    @Test
    public void testAddAndRemoveNeighbor() {
        Map gameMap = new Map();
        Territory t1 = new Territory("Alpha", "C1", 2);
        Territory t2 = new Territory("Beta", "C1", 2);
        gameMap.addTerritory(t1);
        gameMap.addTerritory(t2);
        gameMap.addNeighbor("Alpha", "Beta");
        assertTrue(t1.getNeighborList().contains(t2));
        assertTrue(t2.getNeighborList().contains(t1));
        gameMap.removeNeighbor("Alpha", "Beta");
        assertFalse(t1.getNeighborList().contains(t2));
        assertFalse(t2.getNeighborList().contains(t1));
    }
    
    @Test
    public void testSetContinents() {
        Map gameMap = new Map();
      java.util.Map<String, Integer> newContinents = new java.util.HashMap<>();

        newContinents.put("Africa", 3);
        gameMap.setContinents(newContinents);
        assertTrue(gameMap.getContinents().containsKey("Africa"));
        assertEquals(3, gameMap.getContinents().get("Africa").intValue());
    }
    
    @Test
    public void testToString() {
        Map gameMap = new Map();
        Territory t1 = new Territory("X", "C1", 1);
        gameMap.addTerritory(t1);
        String mapStr = gameMap.toString();
        assertTrue(mapStr.contains("X"));
    }
    
    @Test
    public void testCopyConstructor() {
        Map original = new Map();
        original.addContinent("Oceania", 4);
        Territory t1 = new Territory("Australia", "Oceania", 3);
        original.addTerritory(t1);
        Map copy = new Map(original);
        // Modify original; the copy should remain unaffected.
        original.removeContinent("Oceania");
        assertNotNull(copy.getContinents().get("Oceania"));
        assertNotNull(copy.getTerritoryByName("Australia"));
    }
    
    @Test
    public void testMapValidation() {
        Map gameMap = new Map();
        // Create 3 connected territories: A - B - C
        Territory a = new Territory("A", "Continent", 1);
        Territory b = new Territory("B", "Continent", 1);
        Territory c = new Territory("C", "Continent", 1);
        gameMap.addTerritory(a);
        gameMap.addTerritory(b);
        gameMap.addTerritory(c);
        a.addNeighbor(b);
        b.addNeighbor(a);
        b.addNeighbor(c);
        c.addNeighbor(b);
        assertTrue(gameMap.mapValidation());
        
        // Create a map with disconnected territories.
        Map gameMap2 = new Map();
        Territory d = new Territory("D", "Continent", 1);
        Territory e = new Territory("E", "Continent", 1);
        gameMap2.addTerritory(d);
        gameMap2.addTerritory(e);
        assertFalse(gameMap2.mapValidation());
    }
    
    @Test
    public void testContinentValidation() {
        Map gameMap = new Map();
        gameMap.addContinent("NorthAmerica", 5);
        // Two connected territories in NorthAmerica.
        Territory a = new Territory("USA", "NorthAmerica", 2);
        Territory b = new Territory("Canada", "NorthAmerica", 2);
        gameMap.addTerritory(a);
        gameMap.addTerritory(b);
        a.addNeighbor(b);
        b.addNeighbor(a);
        // Another continent with two disconnected territories.
        gameMap.addContinent("SouthAmerica", 3);
        Territory c = new Territory("Brazil", "SouthAmerica", 2);
        Territory d = new Territory("Argentina", "SouthAmerica", 2);
        gameMap.addTerritory(c);
        gameMap.addTerritory(d);
        // Since Brazil and Argentina are not connected, validation should fail.
        assertFalse(gameMap.continentValidation());
    }
    
    @Test
    public void testSaveToFile(@TempDir Path tempDir) throws Exception {
        Map gameMap = new Map();
        gameMap.addContinent("TestContinent", 10);
        Territory t1 = new Territory("TestLand", "TestContinent", 5);
        gameMap.addTerritory(t1);
        // Add a neighbor to write something in the borders section.
        t1.addNeighbor(t1); // Self-neighbor for simplicity.
        Path filePath = tempDir.resolve("map.txt");
        gameMap.saveToFile(filePath.toString());
        String content = Files.readString(filePath);
        assertTrue(content.contains("[continents]"));
        assertTrue(content.contains("TestContinent 10"));
        assertTrue(content.contains("[countries]"));
        assertTrue(content.contains("TestLand"));
        assertTrue(content.contains("[borders]"));
    }
}
