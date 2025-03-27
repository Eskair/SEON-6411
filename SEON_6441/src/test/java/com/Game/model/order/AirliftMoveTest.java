package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class AirliftMoveTest {

    @Test
    public void testExecuteNeutralTarget() {
        // Setup: territoryTo is neutral.
        Player issuer = new Player("AirliftMover", 20);
        Territory from = new Territory("AirliftSource", "Continent", 0);
        Territory to = new Territory("NeutralTarget", "Continent", 0);
        
        from.setOwner(issuer);
        from.setNumOfArmies(15);
        to.setOwner(null);
        to.setNumOfArmies(3);
        
        int moveArmies = 4;
        AirliftMove airliftMove = new AirliftMove(issuer, from, to, moveArmies);
        
        airliftMove.execute();
        
        // Verify that territoryTo's armies have increased.
        assertEquals(3 + moveArmies, to.getNumOfArmies(), "Airlift move should add armies to the neutral territory.");
        // And since territoryTo was neutral, issuer conquers it.
        assertEquals(issuer, to.getOwner());
        assertTrue(issuer.getOwnedTerritories().contains(to));
    }
    
    @Test
    public void testExecuteOccupiedTarget() {
        // Setup: territoryTo already owned by another player.
        Player issuer = new Player("AirliftMover", 20);
        Player defender = new Player("Defender", 20);
        Territory from = new Territory("AirliftSource", "Continent", 0);
        Territory to = new Territory("OccupiedTarget", "Continent", 0);
        
        from.setOwner(issuer);
        to.setOwner(defender);
        from.setNumOfArmies(15);
        to.setNumOfArmies(7);
        
        int moveArmies = 4;
        AirliftMove airliftMove = new AirliftMove(issuer, from, to, moveArmies);
        airliftMove.execute();
        
        // Verify that territoryTo's armies increase but ownership remains unchanged.
        assertEquals(7 + moveArmies, to.getNumOfArmies(), "Occupied territory should have increased armies after airlift move.");
        assertEquals(defender, to.getOwner());
    }
    
    @Test
    public void testCopyConstructor() {
        Player issuer = new Player("AirliftMover", 20);
        Territory from = new Territory("AirliftSource", "Continent", 0);
        Territory to = new Territory("AirliftTarget", "Continent", 0);
        from.setOwner(issuer);
        to.setOwner(null);
        
        int moveArmies = 4;
        // Using an AdvanceAttack instance as source for copy constructor.
        AdvanceAttack baseOrder = new AdvanceAttack(issuer, from, to, moveArmies);
        AirliftMove airliftMove = new AirliftMove(baseOrder);
        
        assertEquals(issuer.getName(), airliftMove.getIssuer().getName());
        assertEquals("AirliftSource", airliftMove.getD_territoryFrom().getName());
        assertEquals("AirliftTarget", airliftMove.getD_territoryTo().getName());
        assertEquals(moveArmies, airliftMove.getD_numberOfArmies());
    }
}
