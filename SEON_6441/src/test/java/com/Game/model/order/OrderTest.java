package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;

public class OrderTest {

    @Test
    void testDefaultConstructor() {
        Order order = new Order() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };
        assertNull(order.getIssuer(), "The issuer should be null for the default constructor.");
    }

    @Test
    void testConstructorWithIssuer() {
        Player player = new Player("Player1");
        Order order = new Order(player) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };
        assertEquals(player, order.getIssuer(), "The issuer should match the provided player.");
    }

    @Test
    void testCopyConstructor() {
        Player player = new Player("Player1");
        Order original = new Order(player) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };
        Order copy = new Order(original) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };
        assertEquals(original.getIssuer(), copy.getIssuer(), "The issuer should match the original order.");
    }

    @Test
    void testSetAndGetIssuer() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        Order order = new Order() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        order.setIssuer(player1);
        assertEquals(player1, order.getIssuer(), "The issuer should match the set value.");

        order.setIssuer(player2);
        assertEquals(player2, order.getIssuer(), "The issuer should match the updated value.");
    }
}