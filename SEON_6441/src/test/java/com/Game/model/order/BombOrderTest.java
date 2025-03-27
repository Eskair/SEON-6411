package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class BombOrderTest {

    @Test
    public void testExecuteNegotiationBranch() {
        // When negotiation is in effect, the bomb order should not modify the target territory.
        Player issuer = new Player("Bomber", 20);
        Player targetOwner = new Player("TargetOwner", 20);
        Territory target = new Territory("BombTarget", "Continent", 0);
        target.setNumOfArmies(12);
        target.setOwner(targetOwner);
        
        BombOrder bombOrder = new BombOrder(issuer, target);
        
        // Simulate negotiation.
        issuer.getNegociatedPlayersPerTurn().add(targetOwner);
        
        bombOrder.execute();
        
        // The territory's armies should remain unchanged.
        assertEquals(12, target.getNumOfArmies(), "Under negotiation, bomb order should be undone (no change).");
    }
    
    @Test
    public void testExecuteNormalBranch() {
        // Without negotiation, bomb order halves the armies.
        Player issuer = new Player("Bomber", 20);
        Player targetOwner = new Player("TargetOwner", 20);
        Territory target = new Territory("BombTarget", "Continent", 0);
        target.setNumOfArmies(12);
        target.setOwner(targetOwner);
        
        BombOrder bombOrder = new BombOrder(issuer, target);
        bombOrder.execute();
        
        // Expect armies to be halved (integer division).
        assertEquals(6, target.getNumOfArmies(), "Bomb order should half the number of armies.");
    }
    
    @Test
    public void testGetterSetter() {
        BombOrder order = new BombOrder();
        Territory t = new Territory("TestBomb", "Continent", 0);
        order.setTerritoryTo(t);
        assertEquals(t, order.getTerritoryTo());
    }
    
    @Test
    public void testCopyConstructor() {
        Player issuer = new Player("Bomber", 20);
        Territory target = new Territory("BombTarget", "Continent", 0);
        target.setOwner(new Player("TargetOwner", 20));
        BombOrder original = new BombOrder(issuer, target);
        BombOrder copy = new BombOrder(original);
        assertEquals(original.getIssuer().getName(), copy.getIssuer().getName());
        assertEquals(original.getTerritoryTo().getName(), copy.getTerritoryTo().getName());
    }
}
