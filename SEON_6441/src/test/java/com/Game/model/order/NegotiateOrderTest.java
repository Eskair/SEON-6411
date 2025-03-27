package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;

public class NegotiateOrderTest {

    @Test
    public void testExecuteNegotiate() {
        Player issuer = new Player("Negotiator1", 20);
        Player target = new Player("Negotiator2", 20);
        
        NegotiateOrder negotiateOrder = new NegotiateOrder(issuer, target);
        negotiateOrder.execute();
        
        // After execution, each player's negotiated list should contain the other.
        assertTrue(issuer.getNegociatedPlayersPerTurn().contains(target),
                   "Issuer should have target in negotiated list.");
        assertTrue(target.getNegociatedPlayersPerTurn().contains(issuer),
                   "Target should have issuer in negotiated list.");
    }
    
    @Test
    public void testGetterSetter() {
        NegotiateOrder order = new NegotiateOrder();
        Player target = new Player("Negotiator2", 20);
        order.setPlayerTo(target);
        assertEquals(target, order.getPlayerTo());
    }
    
    @Test
    public void testCopyConstructor() {
        Player issuer = new Player("Negotiator1", 20);
        Player target = new Player("Negotiator2", 20);
        NegotiateOrder original = new NegotiateOrder(issuer, target);
        NegotiateOrder copy = new NegotiateOrder(original);
        assertEquals(original.getIssuer().getName(), copy.getIssuer().getName());
        assertEquals(original.getPlayerTo().getName(), copy.getPlayerTo().getName());
    }
}
