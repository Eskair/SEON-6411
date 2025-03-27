package com.Game.model.order;
import com.Game.model.Territory;
import com.Game.model.Player;

/**
 * Represents a bomb order in the game.
 * When executed, the bomb order halves the number of armies in the target territory,
 * unless diplomacy prevents the order from being executed.
 */
public class BombOrder extends Order {
	
	/**
	 * The territory that is targeted by the bomb order.
	 */
	private Territory d_territoryTo;
	
	/**
	 * Default constructor for BombOrder.
	 */
	public BombOrder() {
		super();
	}
	
	/**
	 * Constructs a BombOrder with the specified issuer and target territory.
	 * 
	 * @param p_issuer The player issuing the bomb order.
	 * @param p_territoryTo The territory to be bombed.
	 */
	public BombOrder(Player p_issuer, Territory p_territoryTo) {
		super(p_issuer);
		this.d_territoryTo = p_territoryTo;
	}
	
	/**
	 * Copy constructor for BombOrder.
	 * 
	 * @param bombOrder The BombOrder instance to copy.
	 */
	public BombOrder(BombOrder bombOrder) {
		super(bombOrder.getIssuer());
		this.d_territoryTo = bombOrder.getTerritoryTo();
	}
	
	/**
	 * Executes the bomb order.
	 * If the target territory's owner is under diplomacy with the issuer, the order is undone.
	 * Otherwise, the number of armies in the target territory is halved.
	 */
	@Override
	public void execute() {
		if(getIssuer().getNegociatedPlayersPerTurn().contains(d_territoryTo.getOwner())) {
			System.out.println("Undo Bomb order from: " + getIssuer().getName());
			System.out.println("Diplomacy between: " + getIssuer().getName() + " and " + d_territoryTo.getOwner().getName());
			return;
		}
		System.out.println();
		System.out.println("Number of armies in " + d_territoryTo.getName() + ": " + d_territoryTo.getNumOfArmies());
		System.out.println("Using BOMB on " + d_territoryTo.getName());
		d_territoryTo.setNumOfArmies(d_territoryTo.getNumOfArmies() / 2);
		System.out.println("Number of armies in " + d_territoryTo.getName() + ": " + d_territoryTo.getNumOfArmies());
		System.out.println();
	}
	
	/**
	 * Returns the target territory of the bomb order.
	 * 
	 * @return the d_territoryTo
	 */
	public Territory getTerritoryTo() {
		return d_territoryTo;
	}

	/**
	 * Sets the target territory for the bomb order.
	 * 
	 * @param d_territoryTo the territory to be bombed.
	 */
	public void setTerritoryTo(Territory d_territoryTo) {
		this.d_territoryTo = d_territoryTo;
	}
}
