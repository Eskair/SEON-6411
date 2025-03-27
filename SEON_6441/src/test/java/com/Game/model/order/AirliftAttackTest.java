package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class AirliftAttackTest {

    @Test
    public void testExecuteNegotiationBranch() {
        // Setup players and territories
        Player issuer = new Player("AirliftAttacker", 20);
        Player defender = new Player("AirliftDefender", 20);
        Territory from = new Territory("AirliftSource", "Continent", 0);
        Territory to = new Territory("AirliftTarget", "Continent", 0);
        
        // Set owners and initial armies
        from.setOwner(issuer);
        to.setOwner(defender);
        from.setNumOfArmies(12);
        to.setNumOfArmies(6);
        
        int airliftArmies = 5;
        AirliftAttack airliftAttack = new AirliftAttack(issuer, from, to, airliftArmies);
        
        // Simulate diplomacy.
        issuer.getNegociatedPlayersPerTurn().add(defender);
        
        int initialFromArmies = from.getNumOfArmies();
        airliftAttack.execute();
        
        // In the negotiation branch, the order “undos” the attack.
        assertEquals(initialFromArmies + airliftArmies, from.getNumOfArmies(),
                     "When negotiation is in effect, airlift attack should restore the moved armies.");
    }
    
    @Test
    public void testCopyConstructor() {
        Player issuer = new Player("AirliftAttacker", 20);
        Player defender = new Player("AirliftDefender", 20);
        Territory from = new Territory("AirliftSource", "Continent", 0);
        Territory to = new Territory("AirliftTarget", "Continent", 0);
        from.setOwner(issuer);
        to.setOwner(defender);
        
        int airliftArmies = 5;
        AirliftAttack original = new AirliftAttack(issuer, from, to, airliftArmies);
        AirliftAttack copy = new AirliftAttack(original);
        
        assertEquals(original.getIssuer().getName(), copy.getIssuer().getName());
        assertEquals(original.getD_territoryFrom().getName(), copy.getD_territoryFrom().getName());
        assertEquals(original.getD_territoryTo().getName(), copy.getD_territoryTo().getName());
        assertEquals(original.getD_numberOfArmies(), copy.getD_numberOfArmies());
    }
}
