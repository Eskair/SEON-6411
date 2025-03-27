package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class AdvanceMoveTest {

    @Test
    public void testExecuteNeutralTarget() {
        // Setup: territoryTo is neutral (owner == null)
        Player issuer = new Player("Mover", 20);
        Territory from = new Territory("Source", "Continent", 0);
        Territory to = new Territory("Neutral", "Continent", 0);
        
        // Set initial states
        from.setOwner(issuer);
        from.setNumOfArmies(10);
        to.setOwner(null);
        to.setNumOfArmies(2);
        
        int moveArmies = 3;
        AdvanceMove moveOrder = new AdvanceMove(issuer, from, to, moveArmies);
        
        // Execute order.
        moveOrder.execute();
        
        // Expect that territoryTo’s armies increased by moveArmies
        assertEquals(2 + moveArmies, to.getNumOfArmies(), "Neutral territory should receive additional armies.");
        // And since the territory was neutral, issuer should now conquer it.
        assertEquals(issuer, to.getOwner());
        // Optionally, check that the issuer's territory list has been updated.
        // (Assuming addTerritory() adds it.)
        assertTrue(issuer.getOwnedTerritories().contains(to), "Issuer should have conquered the neutral territory.");
    }
    
    @Test
    public void testExecuteNonNeutralTarget() {
        // Setup: territoryTo already has an owner.
        Player issuer = new Player("Mover", 20);
        Player defender = new Player("Defender", 20);
        Territory from = new Territory("Source", "Continent", 0);
        Territory to = new Territory("Occupied", "Continent", 0);
        
        from.setOwner(issuer);
        to.setOwner(defender);
        from.setNumOfArmies(10);
        to.setNumOfArmies(5);
        
        int moveArmies = 4;
        AdvanceMove moveOrder = new AdvanceMove(issuer, from, to, moveArmies);
        
        // Execute order.
        moveOrder.execute();
        
        // Expect that territoryTo’s armies increased by moveArmies,
        // but ownership remains unchanged.
        assertEquals(5 + moveArmies, to.getNumOfArmies(), "Occupied territory should receive additional armies.");
        assertEquals(defender, to.getOwner());
    }
    
    @Test
    public void testCopyConstructor() {
        Player issuer = new Player("Mover", 20);
        Territory from = new Territory("Source", "Continent", 0);
        Territory to = new Territory("Target", "Continent", 0);
        from.setOwner(issuer);
        to.setOwner(null);
        int moveArmies = 4;
        AdvanceAttack baseOrder = new AdvanceAttack(issuer, from, to, moveArmies);
        AdvanceMove copyOrder = new AdvanceMove(baseOrder);
        
        assertEquals(issuer.getName(), copyOrder.getIssuer().getName());
        assertEquals("Source", copyOrder.getD_territoryFrom().getName());
        assertEquals("Target", copyOrder.getD_territoryTo().getName());
        assertEquals(moveArmies, copyOrder.getD_numberOfArmies());
    }
}
