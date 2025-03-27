package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class BlockadeOrderTest {

    @Test
    public void testExecuteBlockade() {
        Player issuer = new Player("Blockader", 20);
        Territory target = new Territory("ToBlockade", "Continent", 0);
        
        // Set an initial number of armies.
        target.setNumOfArmies(5);
        // Assume the territory is owned by the issuer.
        target.setOwner(issuer);
        issuer.addTerritory(target);
        
        BlockadeOrder blockadeOrder = new BlockadeOrder(issuer, target);
        blockadeOrder.execute();
        
        // After blockade, the territory should become neutral (owner == null)
        assertNull(target.getOwner(), "After blockade, territory should have no owner.");
        // And its armies should be tripled.
        assertEquals(15, target.getNumOfArmies(), "Number of armies should be tripled.");
        // Also, issuer's territory list should no longer contain the territory.
        assertFalse(issuer.getOwnedTerritories().contains(target));
    }
    
    @Test
    public void testGetterSetter() {
        BlockadeOrder order = new BlockadeOrder();
        Territory t = new Territory("Sample", "Continent", 0);
        order.setTerritoryTo(t);
        assertEquals(t, order.getTerritoryTo());
    }
}
