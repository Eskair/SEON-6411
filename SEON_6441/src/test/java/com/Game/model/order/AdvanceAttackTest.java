package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class AdvanceAttackTest {

    @Test
    public void testExecuteNegotiationBranch() {
        // Setup players and territories
        Player issuer = new Player("Attacker", 20);
        Player defender = new Player("Defender", 20);
        Territory from = new Territory("Origin", "Continent", 0);
        Territory to = new Territory("Target", "Continent", 0);
        
        // Set owners and initial armies
        from.setOwner(issuer);
        to.setOwner(defender);
        from.setNumOfArmies(10);
        to.setNumOfArmies(8);
        
        // Create an AdvanceAttack order with a given number of attacking armies
        int attackingArmies = 4;
        AdvanceAttack attackOrder = new AdvanceAttack(issuer, from, to, attackingArmies);
        
        // Simulate diplomacy: add defender to issuer's negotiated list.
        issuer.getNegociatedPlayersPerTurn().add(defender);
        
        // Record the original armies in from territory.
        int initialFromArmies = from.getNumOfArmies();
        
        // Execute the order.
        attackOrder.execute();
        
        // In the negotiation branch, the order should undo the attack by adding the armies back.
        assertEquals(initialFromArmies + attackingArmies, from.getNumOfArmies(),
                     "When negotiation is in effect, attacking armies should be restored to the source territory.");
    }
    
    @Test
    public void testCopyConstructor() {
        Player issuer = new Player("Attacker", 20);
        Player defender = new Player("Defender", 20);
        Territory from = new Territory("Origin", "Continent", 0);
        Territory to = new Territory("Target", "Continent", 0);
        from.setOwner(issuer);
        to.setOwner(defender);
        from.setNumOfArmies(10);
        to.setNumOfArmies(8);
        
        int attackingArmies = 4;
        AdvanceAttack original = new AdvanceAttack(issuer, from, to, attackingArmies);
        AdvanceAttack copy = new AdvanceAttack(original);
        
        assertEquals(original.getIssuer().getName(), copy.getIssuer().getName());
        assertEquals(original.getD_territoryFrom().getName(), copy.getD_territoryFrom().getName());
        assertEquals(original.getD_territoryTo().getName(), copy.getD_territoryTo().getName());
        assertEquals(original.getD_numberOfArmies(), copy.getD_numberOfArmies());
    }
}
