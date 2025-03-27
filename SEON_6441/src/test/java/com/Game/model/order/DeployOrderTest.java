package com.Game.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.Game.model.Player;
import com.Game.model.Territory;

public class DeployOrderTest {

    @Test
    public void testExecuteDeploy() {
        Player issuer = new Player("Deployer", 20);
        Territory target = new Territory("DeployTarget", "Continent", 0);
        target.setNumOfArmies(3);
        
        DeployOrder deployOrder = new DeployOrder(issuer, target, 5);
        deployOrder.execute();
        
        // Expect the target territory’s armies to increase by 5.
        assertEquals(3 + 5, target.getNumOfArmies(), "Deploy order should add the specified number of armies.");
    }
    
    @Test
    public void testGetterSetter() {
        DeployOrder order = new DeployOrder();
        Territory t = new Territory("SampleDeploy", "Continent", 0);
        order.setTargetTerritory(t);
        order.setNumberOfArmies(7);
        assertEquals(t, order.getTargetTerritory());
        assertEquals(7, order.getNumberOfArmies());
    }
}
