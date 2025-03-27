package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class AirliftOrderTest {

    @Test
    void testConstructorWithParameters() {
        Player player = new Player("Player1");
        Territory source = new Territory("Source", "Continent1", 5);
        Territory target = new Territory("Target", "Continent1", 5);

        AirliftOrder airliftOrder = new AirliftOrder(player, source, target, 10) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        assertEquals(player, airliftOrder.getIssuer());
        assertEquals(source, airliftOrder.getD_territoryFrom());
        assertEquals(target, airliftOrder.getD_territoryTo());
        assertEquals(10, airliftOrder.getD_numberOfArmies());
    }

    @Test
    void testCopyConstructor() {
        Player player = new Player("Player1");
        Territory source = new Territory("Source", "Continent1", 5);
        Territory target = new Territory("Target", "Continent1", 5);

        AirliftOrder original = new AirliftOrder(player, source, target, 10) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        AirliftOrder copy = new AirliftOrder(original) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        assertEquals(original.getIssuer(), copy.getIssuer());
        assertEquals(original.getD_territoryFrom(), copy.getD_territoryFrom());
        assertEquals(original.getD_territoryTo(), copy.getD_territoryTo());
        assertEquals(original.getD_numberOfArmies(), copy.getD_numberOfArmies());
    }

    @Test
    void testSetAndGetD_territoryFrom() {
        Territory source = new Territory("Source", "Continent1", 5);
        Territory newSource = new Territory("NewSource", "Continent2", 3);

        AirliftOrder airliftOrder = new AirliftOrder() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        airliftOrder.setD_territoryFrom(source);
        assertEquals(source, airliftOrder.getD_territoryFrom());

        airliftOrder.setD_territoryFrom(newSource);
        assertEquals(newSource, airliftOrder.getD_territoryFrom());
    }

    @Test
    void testSetAndGetD_territoryTo() {
        Territory target = new Territory("Target", "Continent1", 5);
        Territory newTarget = new Territory("NewTarget", "Continent2", 3);

        AirliftOrder airliftOrder = new AirliftOrder() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        airliftOrder.setD_territoryTo(target);
        assertEquals(target, airliftOrder.getD_territoryTo());

        airliftOrder.setD_territoryTo(newTarget);
        assertEquals(newTarget, airliftOrder.getD_territoryTo());
    }

    @Test
    void testSetAndGetD_numberOfArmies() {
        AirliftOrder airliftOrder = new AirliftOrder() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        airliftOrder.setD_numberOfArmies(10);
        assertEquals(10, airliftOrder.getD_numberOfArmies());

        airliftOrder.setD_numberOfArmies(20);
        assertEquals(20, airliftOrder.getD_numberOfArmies());
    }
}