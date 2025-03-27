package com.Game.model.order;

import com.Game.model.Player;
import com.Game.model.Territory;

/**
 * Represents a negotiate order in the game.
 * When executed, the order establishes a negotiation agreement between the issuing player
 * and the specified player, preventing direct attacks between them during that turn.
 */
public class NegotiateOrder extends Order {
	
	/**
	 * The player with whom the negotiation is initiated.
	 */
	private Player d_playerTo;
	
	/**
	 * Default constructor for NegotiateOrder.
	 */
	public NegotiateOrder() {
		super();
	}
	
	/**
	 * Constructs a NegotiateOrder with the specified issuer and target player.
	 * 
	 * @param p_issuer The player issuing the negotiation order.
	 * @param p_playerTo The player to negotiate with.
	 */
	public NegotiateOrder(Player p_issuer, Player p_playerTo) {
		super(p_issuer);
		this.d_playerTo = p_playerTo;
	}
	
	/**
	 * Copy constructor for NegotiateOrder.
	 * 
	 * @param negociateOrder The NegotiateOrder instance to copy.
	 */
	public NegotiateOrder(NegotiateOrder negociateOrder) {
		super(negociateOrder.getIssuer());
		this.d_playerTo = negociateOrder.getPlayerTo();
	}
	
	/**
	 * Executes the negotiate order.
	 * The method adds the target player to the issuer's list of negotiated players for the turn,
	 * and vice versa, preventing attacks between these players during the turn.
	 */
	@Override
	public void execute() {
		System.out.println();
		System.out.println("Negociating with: " + this.d_playerTo.getName());
		this.d_issuer.getNegociatedPlayersPerTurn().add(d_playerTo);
		d_playerTo.getNegociatedPlayersPerTurn().add(this.d_issuer);
		System.out.println();
	}
	
	/**
	 * Returns the player with whom the negotiation is initiated.
	 * 
	 * @return The target player for negotiation.
	 */
	public Player getPlayerTo() {
		return d_playerTo;
	}

	/**
	 * Sets the player with whom the negotiation is to be initiated.
	 * 
	 * @param d_playerTo The target player for negotiation.
	 */
	public void setPlayerTo(Player d_playerTo) {
		this.d_playerTo = d_playerTo;
	}
}
