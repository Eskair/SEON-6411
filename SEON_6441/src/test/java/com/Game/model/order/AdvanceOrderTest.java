package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class AdvanceOrderTest {

    @Test
    void testConstructorWithParameters() {
        Player player = new Player("Player1");
        Territory source = new Territory("Source", "Continent1", 5);
        Territory target = new Territory("Target", "Continent1", 5);

        AdvanceOrder advanceOrder = new AdvanceOrder(player, source, target, 10) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        assertEquals(player, advanceOrder.getIssuer());
        assertEquals(source, advanceOrder.getD_territoryFrom());
        assertEquals(target, advanceOrder.getD_territoryTo());
        assertEquals(10, advanceOrder.getD_numberOfArmies());
    }

    @Test
    void testCopyConstructor() {
        Player player = new Player("Player1");
        Territory source = new Territory("Source", "Continent1", 5);
        Territory target = new Territory("Target", "Continent1", 5);

        AdvanceOrder original = new AdvanceOrder(player, source, target, 10) {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        AdvanceOrder copy = new AdvanceOrder(original) {
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

        AdvanceOrder advanceOrder = new AdvanceOrder() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        advanceOrder.setD_territoryFrom(source);
        assertEquals(source, advanceOrder.getD_territoryFrom());

        advanceOrder.setD_territoryFrom(newSource);
        assertEquals(newSource, advanceOrder.getD_territoryFrom());
    }

    @Test
    void testSetAndGetD_territoryTo() {
        Territory target = new Territory("Target", "Continent1", 5);
        Territory newTarget = new Territory("NewTarget", "Continent2", 3);

        AdvanceOrder advanceOrder = new AdvanceOrder() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        advanceOrder.setD_territoryTo(target);
        assertEquals(target, advanceOrder.getD_territoryTo());

        advanceOrder.setD_territoryTo(newTarget);
        assertEquals(newTarget, advanceOrder.getD_territoryTo());
    }

    @Test
    void testSetAndGetD_numberOfArmies() {
        AdvanceOrder advanceOrder = new AdvanceOrder() {
            @Override
            public void execute() {
                // Mock implementation for abstract method
            }
        };

        advanceOrder.setD_numberOfArmies(10);
        assertEquals(10, advanceOrder.getD_numberOfArmies());

        advanceOrder.setD_numberOfArmies(20);
        assertEquals(20, advanceOrder.getD_numberOfArmies());
    }
}