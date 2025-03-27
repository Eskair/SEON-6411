package com.Game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.order.Order;

public class PlayerTest {
    
    @Test
    public void testConstructorAndGettersSetters() {
        Player player = new Player("John", 10);
        assertEquals("John", player.getName());
        assertEquals(10, player.getNbrOfReinforcementArmies());
        assertTrue(player.getOwnedTerritories().isEmpty());
        
        player.setName("Doe");
        player.setNbrOfReinforcementArmies(15);
        assertEquals("Doe", player.getName());
        assertEquals(15, player.getNbrOfReinforcementArmies());
    }
    
    @Test
    public void testAddAndRemoveTerritory() {
        Player player = new Player("John");
        Territory t = new Territory("Alpha", "Continent", 2);
        player.addTerritory(t);
        assertEquals(t, player.findTerritoryByName("Alpha"));
        player.removeTerritory(t);
        assertNull(player.findTerritoryByName("Alpha"));
    }
    
    @Test
    public void testCreateDeployOrderValid() {
        Player player = new Player("John", 10);
        Territory t = new Territory("Alpha", "Continent", 2);
        t.setOwner(player);
        player.addTerritory(t);
        boolean result = player.createDeployOrder("Alpha", 5);
        assertTrue(result);
        // Verify that an order was added and reinforcement armies were deducted.
        assertEquals(1, player.getOrders().size());
        assertEquals(5, player.getNbrOfReinforcementArmies());
    }
    
    @Test
    public void testCreateDeployOrderInvalidTerritory() {
        Player player = new Player("John", 10);
        boolean result = player.createDeployOrder("NonExistent", 5);
        assertFalse(result);
        assertEquals(0, player.getOrders().size());
    }
    
    @Test
    public void testCreateDeployOrderInvalidArmies() {
        Player player = new Player("John", 3);
        Territory t = new Territory("Alpha", "Continent", 2);
        t.setOwner(player);
        player.addTerritory(t);
        boolean result = player.createDeployOrder("Alpha", 5);
        assertFalse(result);
        assertEquals(0, player.getOrders().size());
    }
    
    @Test
    public void testNextOrderAndClearOrders() {
        Player player = new Player("John", 10);
        Territory t = new Territory("Alpha", "Continent", 2);
        t.setOwner(player);
        player.addTerritory(t);
        player.createDeployOrder("Alpha", 5);
        assertEquals(1, player.getOrders().size());
        Order order = player.nextOrder();
        assertNotNull(order);
        assertEquals(0, player.getOrders().size());
        player.createDeployOrder("Alpha", 3);
        player.clearOrders();
        assertEquals(0, player.getOrders().size());
    }
    
    @Test
    public void testFindTerritoryByName() {
        Player player = new Player("John");
        Territory t = new Territory("Alpha", "Continent", 2);
        player.addTerritory(t);
        assertEquals(t, player.findTerritoryByName("Alpha"));
        assertNull(player.findTerritoryByName("Beta"));
    }
    
    @Test
    public void testCardManagement() {
        Player player = new Player("John");
        assertTrue(player.getCards().isEmpty());
        player.addCard(CardType.BOMB);
        assertEquals(1, player.getCards().get(CardType.BOMB));
        player.addCard(CardType.BOMB);
        assertEquals(2, player.getCards().get(CardType.BOMB));
        boolean removed = player.removeCard(CardType.BOMB);
        assertTrue(removed);
        assertEquals(1, player.getCards().get(CardType.BOMB));
        removed = player.removeCard(CardType.BOMB);
        assertTrue(removed);
        assertFalse(player.getCards().containsKey(CardType.BOMB));
        removed = player.removeCard(CardType.AIRLIFT);
        assertFalse(removed);
        
        player.addCard(CardType.AIRLIFT);
        player.addCard(CardType.BLOCKADE);
        String formatted = player.getFormattedCards();
        assertTrue(formatted.contains("AIRLIFT: 1"));
        assertTrue(formatted.contains("BLOCKADE: 1"));
    }
    
    @Test
    public void testConqueredTerritoriesManagement() {
        Player player = new Player("John");
        assertEquals(0, player.getConqueredTerritoriesPerTurn());
        player.incrementConqueredTerritoriesPerTurn();
        assertEquals(1, player.getConqueredTerritoriesPerTurn());
        player.resetConqueredTerritoriesPerTurn();
        assertEquals(0, player.getConqueredTerritoriesPerTurn());
    }
    
    @Test
    public void testNegociatedPlayersManagement() {
        Player player = new Player("John");
        Player other = new Player("Doe");
        assertTrue(player.getNegociatedPlayersPerTurn().isEmpty());
        List<Player> list = new ArrayList<>();
        list.add(other);
        player.setNegociatedPlayersPerTurn(list);
        assertEquals(1, player.getNegociatedPlayersPerTurn().size());
        player.resetNegociatedPlayersPerTurn();
        assertTrue(player.getNegociatedPlayersPerTurn().isEmpty());
    }
    
    @Test
    public void testConqueredFlag() {
        Player player = new Player("John");
        assertFalse(player.getHasConqueredThisTurn());
        player.setHasConqueredThisTurn(true);
        assertTrue(player.getHasConqueredThisTurn());
    }
    
    @Test
    public void testIssueOrderDeploy() {
        Player player = new Player("John", 10);
        Territory t = new Territory("Alpha", "Continent", 2);
        t.setOwner(player);
        player.addTerritory(t);
        Map map = new Map();
        map.addTerritory(t);
        List<Player> players = new ArrayList<>();
        players.add(player);
        
        boolean result = player.issueOrder("deploy Alpha 5", map, players);
        assertTrue(result);
        assertEquals(1, player.getOrders().size());
        assertEquals(5, player.getNbrOfReinforcementArmies());
    }
    
    @Test
    public void testIssueOrderAdvance() {
        Player player = new Player("John", 10);
        Territory from = new Territory("From", "Continent", 2);
        Territory to = new Territory("To", "Continent", 2);
        from.setOwner(player);
        player.addTerritory(from);
        // Set up neighbor relationship.
        from.addNeighbor(to);
        to.addNeighbor(from);
        Map map = new Map();
        map.addTerritory(from);
        map.addTerritory(to);
        List<Player> players = new ArrayList<>();
        players.add(player);
        
        from.setNumOfArmies(10);
        boolean result = player.issueOrder("advance From To 5", map, players);
        assertTrue(result);
        assertEquals(1, player.getOrders().size());
        assertEquals(5, from.getNumOfArmies());
    }
    
    @Test
    public void testIssueOrderBomb() {
        Player player = new Player("John", 10);
        Territory own = new Territory("Own", "Continent", 2);
        own.setOwner(player);
        player.addTerritory(own);
        Territory enemy = new Territory("Enemy", "Continent", 2);
        Player enemyPlayer = new Player("EnemyPlayer");
        enemy.setOwner(enemyPlayer);
        Map map = new Map();
        map.addTerritory(own);
        map.addTerritory(enemy);
        // Ensure enemy is adjacent.
        own.addNeighbor(enemy);
        enemy.addNeighbor(own);
        List<Player> players = Arrays.asList(player, enemyPlayer);
        
        // Without a bomb card, the command should fail.
        boolean result = player.issueOrder("bomb Enemy", map, players);
        assertFalse(result);
        // Add bomb card and try again.
        player.addCard(CardType.BOMB);
        result = player.issueOrder("bomb Enemy", map, players);
        assertTrue(result);
        assertEquals(1, player.getOrders().size());
    }
    
    @Test
    public void testIssueOrderBlockade() {
        Player player = new Player("John", 10);
        Territory t = new Territory("Alpha", "Continent", 2);
        t.setOwner(player);
        player.addTerritory(t);
        // Without a blockade card, should fail.
        boolean result = player.issueOrder("blockade Alpha", new Map(), new ArrayList<>());
        assertFalse(result);
        player.addCard(CardType.BLOCKADE);
        result = player.issueOrder("blockade Alpha", new Map(), new ArrayList<>());
        assertTrue(result);
        assertEquals(1, player.getOrders().size());
    }
    
    @Test
    public void testIssueOrderAirlift() {
        Player player = new Player("John", 10);
        Territory from = new Territory("From", "Continent", 2);
        Territory to = new Territory("To", "Continent", 2);
        from.setOwner(player);
        player.addTerritory(from);
        from.setNumOfArmies(10);
        Map map = new Map();
        map.addTerritory(from);
        map.addTerritory(to);
        // Without an airlift card, should fail.
        boolean result = player.issueOrder("airlift From To 5", map, new ArrayList<>());
        assertFalse(result);
        player.addCard(CardType.AIRLIFT);
        result = player.issueOrder("airlift From To 5", map, new ArrayList<>());
        assertTrue(result);
        assertEquals(1, player.getOrders().size());
        assertEquals(5, from.getNumOfArmies());
    }
    
    @Test
    public void testIssueOrderNegotiate() {
        Player player = new Player("John", 10);
        Player other = new Player("Doe", 10);
        List<Player> players = Arrays.asList(player, other);
        // Without a negotiate card, should fail.
        boolean result = player.issueOrder("negotiate Doe", new Map(), players);
        assertFalse(result);
        player.addCard(CardType.NEGOTIATE);
        result = player.issueOrder("negotiate Doe", new Map(), players);
        assertTrue(result);
        // Negotiate orders execute immediately, so orders list remains unchanged.
        assertEquals(0, player.getOrders().size());
    }
}
