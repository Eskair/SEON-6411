package com.Game.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TerritoryTest {

    @Test
    public void testGettersSetters() {
        Territory t = new Territory("Alpha", "Continent", 3);
        assertEquals("Alpha", t.getName());
        assertEquals("Continent", t.getContinent());
        assertEquals(3, t.getBonus());
        t.setNumOfArmies(10);
        assertEquals(10, t.getNumOfArmies());
        Player p = new Player("John");
        t.setOwner(p);
        assertEquals(p, t.getOwner());
        t.setNumOfReservedArmies(5);
        assertEquals(5, t.getNumOfReservedArmies());
    }
    
    @Test
    public void testNeighborMethods() {
        Territory t1 = new Territory("A", "Continent", 1);
        Territory t2 = new Territory("B", "Continent", 1);
        assertTrue(t1.getNeighborList().isEmpty());
        t1.addNeighbor(t2);
        assertTrue(t1.getNeighborList().contains(t2));
        assertTrue(t1.hasNeighbor(t2));
    }
    
    @Test
    public void testGetEnemyNeighbors() {
        Territory t1 = new Territory("A", "Continent", 1);
        Territory t2 = new Territory("B", "Continent", 1);
        Player p1 = new Player("John");
        Player p2 = new Player("Doe");
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.addNeighbor(t2);
        List<Territory> enemyNeighbors = t1.getEnemyNeighbors();
        assertEquals(1, enemyNeighbors.size());
        assertEquals(t2, enemyNeighbors.get(0));
        t2.setOwner(p1);
        enemyNeighbors = t1.getEnemyNeighbors();
        assertTrue(enemyNeighbors.isEmpty());
    }
    
    @Test
    public void testEqualsAndToString() {
        Territory t1 = new Territory("A", "Continent", 1);
        Territory t2 = new Territory("A", "Continent", 2); // Different bonus but equals depends on name and continent.
        Territory t3 = new Territory("B", "Continent", 1);
        assertTrue(t1.equals(t2));
        assertFalse(t1.equals(t3));
        
        String str = t1.toString();
        assertTrue(str.contains("Territory Name: A"));
        assertTrue(str.contains("Continent: Continent"));
    }
}
