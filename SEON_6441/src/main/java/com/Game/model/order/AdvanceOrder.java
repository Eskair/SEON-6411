package com.Game.model.order;
import com.Game.model.*;

/**
 * Abstract class representing an advance order in the game.
 * An advance order involves moving armies from one territory to another.
 */
public abstract class AdvanceOrder extends Order {
    /**
     * The source territory from which armies are moved.
     */
    protected Territory d_territoryFrom;
    
    /**
     * The target territory to which armies are moved.
     */
    protected Territory d_territoryTo;
    
    /**
     * The number of armies involved in the order.
     */
    protected int d_numberOfArmies;
    
    /**
     * Default constructor for AdvanceOrder.
     */
    public AdvanceOrder() {
        super();
    }
    
    /**
     * Constructs an AdvanceOrder with the specified issuer, source territory, target territory, 
     * and number of armies.
     * 
     * @param p_issuer The player issuing the order.
     * @param p_territoryFrom The territory from which armies are moved.
     * @param p_territoryTo The territory to which armies are moved.
     * @param p_numberOfArmies The number of armies to move.
     */
    public AdvanceOrder(Player p_issuer, Territory p_territoryFrom, Territory p_territoryTo, int p_numberOfArmies) {
        super(p_issuer);
        this.d_territoryFrom = p_territoryFrom;
        this.d_territoryTo = p_territoryTo;
        this.d_numberOfArmies = p_numberOfArmies;
    }
    
    /**
     * Copy constructor for AdvanceOrder.
     * 
     * @param p_advOrder The AdvanceOrder instance to copy.
     */
    public AdvanceOrder(AdvanceOrder p_advOrder) {
        super(p_advOrder.getIssuer());
        this.d_territoryFrom = p_advOrder.getD_territoryFrom();
        this.d_territoryTo = p_advOrder.getD_territoryTo();
        this.d_numberOfArmies = p_advOrder.getD_numberOfArmies();
    }
    
    /**
     * Executes the advance order.
     * The specific implementation is defined by subclasses.
     */
    public abstract void execute();

    /**
     * Returns the source territory from which armies are moved.
     * 
     * @return the d_territoryFrom
     */
    public Territory getD_territoryFrom() {
        return d_territoryFrom;
    }

    /**
     * Sets the source territory from which armies are moved.
     * 
     * @param d_territoryFrom the territory to set as source.
     */
    public void setD_territoryFrom(Territory d_territoryFrom) {
        this.d_territoryFrom = d_territoryFrom;
    }

    /**
     * Returns the target territory to which armies are moved.
     * 
     * @return the d_territoryTo
     */
    public Territory getD_territoryTo() {
        return d_territoryTo;
    }

    /**
     * Sets the target territory to which armies are moved.
     * 
     * @param d_territoryTo the territory to set as target.
     */
    public void setD_territoryTo(Territory d_territoryTo) {
        this.d_territoryTo = d_territoryTo;
    }

    /**
     * Returns the number of armies involved in the order.
     * 
     * @return the d_numberOfArmies
     */
    public int getD_numberOfArmies() {
        return d_numberOfArmies;
    }

    /**
     * Sets the number of armies involved in the order.
     * 
     * @param d_numberOfArmies the number of armies to set.
     */
    public void setD_numberOfArmies(int d_numberOfArmies) {
        this.d_numberOfArmies = d_numberOfArmies;
    }
}
