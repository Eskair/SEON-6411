package com.Game.model.order;
import com.Game.model.*;

/**
 * Abstract class representing an airlift order in the game.
 * An airlift order involves moving armies from one territory to another via airlift.
 */
public abstract class AirliftOrder extends Order {
    /**
     * The source territory from which armies are airlifted.
     */
    protected Territory d_territoryFrom;
    
    /**
     * The target territory to which armies are airlifted.
     */
    protected Territory d_territoryTo;
    
    /**
     * The number of armies involved in the airlift order.
     */
    protected int d_numberOfArmies;
    
    /**
     * Default constructor for AirliftOrder.
     */
    public AirliftOrder() {
        super();
    }
    
    /**
     * Constructs an AirliftOrder with the specified issuer, source territory, target territory,
     * and number of armies.
     * 
     * @param p_issuer The player issuing the airlift order.
     * @param p_territoryFrom The territory from which armies are airlifted.
     * @param p_territoryTo The territory to which armies are airlifted.
     * @param p_numberOfArmies The number of armies to airlift.
     */
    public AirliftOrder(Player p_issuer, Territory p_territoryFrom, Territory p_territoryTo, int p_numberOfArmies) {
        super(p_issuer);
        this.d_territoryFrom = p_territoryFrom;
        this.d_territoryTo = p_territoryTo;
        this.d_numberOfArmies = p_numberOfArmies;
    }
    
    /**
     * Copy constructor for AirliftOrder.
     * 
     * @param p_airOrder The AirliftOrder instance to copy.
     */
    public AirliftOrder(AirliftOrder p_airOrder) {
        super(p_airOrder.getIssuer());
        this.d_territoryFrom = p_airOrder.getD_territoryFrom();
        this.d_territoryTo = p_airOrder.getD_territoryTo();
        this.d_numberOfArmies = p_airOrder.getD_numberOfArmies();
    }
    
    /**
     * Executes the airlift order.
     * The specific implementation of the execution is defined by subclasses.
     */
    public abstract void execute();

    /**
     * Returns the source territory from which armies are airlifted.
     * 
     * @return the d_territoryFrom
     */
    public Territory getD_territoryFrom() {
        return d_territoryFrom;
    }

    /**
     * Sets the source territory from which armies are airlifted.
     * 
     * @param d_territoryFrom the territory to set as source.
     */
    public void setD_territoryFrom(Territory d_territoryFrom) {
        this.d_territoryFrom = d_territoryFrom;
    }

    /**
     * Returns the target territory to which armies are airlifted.
     * 
     * @return the d_territoryTo
     */
    public Territory getD_territoryTo() {
        return d_territoryTo;
    }

    /**
     * Sets the target territory to which armies are airlifted.
     * 
     * @param d_territoryTo the territory to set as target.
     */
    public void setD_territoryTo(Territory d_territoryTo) {
        this.d_territoryTo = d_territoryTo;
    }

    /**
     * Returns the number of armies involved in the airlift order.
     * 
     * @return the d_numberOfArmies
     */
    public int getD_numberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * Sets the number of armies involved in the airlift order.
     * 
     * @param d_numberOfArmies the number of armies to set.
     */
    public void setD_numberOfArmies(int d_numberOfArmies) {
        this.d_numberOfArmies = d_numberOfArmies;
    }
}
