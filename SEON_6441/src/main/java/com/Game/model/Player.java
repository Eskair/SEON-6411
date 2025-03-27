package com.Game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Game.model.order.AdvanceAttack;
import com.Game.model.order.AdvanceMove;
import com.Game.model.order.AirliftAttack;
import com.Game.model.order.AirliftMove;
import com.Game.model.order.BlockadeOrder;
import com.Game.model.order.BombOrder;
import com.Game.model.order.DeployOrder;
import com.Game.model.order.NegotiateOrder;
import com.Game.model.order.Order;

/**
 * Represents a player in the game who owns territories and can issue orders.
 * Players can deploy armies, acquire territories, manage their reinforcements,
 * cards, conquered territories count, and negotiated players per turn.
 */
public class Player {

    /**
     * The name of the player.
     */
    private String d_name;

    /**
     * The number of reinforcement armies available to the player.
     */
    private int d_nbrOfReinforcementArmies;

    /**
     * A list of territories owned by the player.
     */
    private List<Territory> d_ownedTerritories;

    /**
     * A list of orders issued by the player.
     */
    private List<Order> d_orders;
    
    /**
     * The number of territories conquered by the player in the current turn.
     */
    private int d_conqueredTerritoriesPerTurn;
    
    /**
     * The player's card collection, mapping CardType to the count.
     */
    private HashMap<CardType, Integer> d_cards;
    
    /**
     * List of players with whom this player has negotiated during the current turn.
     */
    private List<Player> d_negociatedPlayersPerTurn;
    
    /**
     * Flag indicating whether the player has conquered at least one territory this turn.
     */
    private boolean d_hasConqueredThisTurn;

    /**
     * Constructor initializing player with a name.
     * 
     * @param p_name Player's name
     */
    public Player(String p_name) {
        this.d_name = p_name;
        this.d_ownedTerritories = new ArrayList<>();
        this.d_orders = new ArrayList<>();
        this.d_nbrOfReinforcementArmies = 0;
        this.d_conqueredTerritoriesPerTurn = 0;
        this.d_cards = new HashMap<>();
        this.d_negociatedPlayersPerTurn = new ArrayList<>();
        this.d_hasConqueredThisTurn = false;
    }

    /**
     * Constructor initializing player with a name and reinforcement armies.
     * 
     * @param p_name Player's name
     * @param p_nbrOfReinforcementArmies Number of reinforcement armies
     */
    public Player(String p_name, int p_nbrOfReinforcementArmies) {
        this.d_name = p_name;
        this.d_ownedTerritories = new ArrayList<>();
        this.d_orders = new ArrayList<>();
        this.d_nbrOfReinforcementArmies = p_nbrOfReinforcementArmies;
        this.d_conqueredTerritoriesPerTurn = 0;
        this.d_cards = new HashMap<>();
        this.d_negociatedPlayersPerTurn = new ArrayList<>();
        this.d_hasConqueredThisTurn = false;
    }

    /**
     * Adds a territory to the player's owned territories.
     * 
     * @param p_territory Territory to be added
     */
    public void addTerritory(Territory p_territory) {
        d_ownedTerritories.add(p_territory);
    }

    /**
     * Removes a territory from the player's owned territories.
     * 
     * @param p_territory Territory to be removed
     */
    public void removeTerritory(Territory p_territory) {
        d_ownedTerritories.remove(p_territory);
    }

    /**
     * Creates a deploy order based on the provided command parameters.
     * This is an MVC-friendly version that doesn't depend on direct Scanner input.
     * 
     * @param p_targetTerritoryName The name of the target territory
     * @param p_numberOfArmies The number of armies to deploy
     * @return true if order creation was successful, false otherwise
     */
    public boolean createDeployOrder(String p_targetTerritoryName, int p_numberOfArmies) {
        Territory l_targetTerritory = findTerritoryByName(p_targetTerritoryName);
        if (l_targetTerritory == null) {
            return false;
        }
        if (p_numberOfArmies > this.d_nbrOfReinforcementArmies) {
            return false;
        }
        DeployOrder l_deployOrder = new DeployOrder(this, l_targetTerritory, p_numberOfArmies);
        d_orders.add(l_deployOrder);
        this.d_nbrOfReinforcementArmies -= p_numberOfArmies;
        return true;
    }

    /**
     * Validates a deploy command.
     * <p>
     * This method checks if the deploy command has valid syntax, whether the target territory exists,
     * if the number of armies is valid, and if the player owns the target territory.
     * </p>
     *
     * @param p_parts An array of strings containing the command parts. The expected format is:
     *                [orderType, targetTerritoryName, numberOfArmies].
     * @return {@code true} if the deploy command is valid, {@code false} otherwise.
     */
    private boolean validateDeploy(String[] p_parts) {
        if (p_parts.length != 3) {
            return false;
        }
        int l_numberOfArmies;
        try {
            l_numberOfArmies = Integer.parseInt(p_parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        String l_targetTerritoryName = p_parts[1];
        Territory l_targetTerritory = findTerritoryByName(l_targetTerritoryName);
        if (l_targetTerritory == null) {
            return false;
        }
        if (l_numberOfArmies > this.d_nbrOfReinforcementArmies) {
            return false;
        }
        if (!l_targetTerritory.getOwner().getName().equals(this.d_name)) {
            return false;
        }
        return true;
    }

    /**
     * Validates an advance command.
     * <p>
     * This method checks if the advance command has valid syntax, whether the territories exist,
     * if they are adjacent, if the player owns the source territory, and if they have enough armies
     * to advance.
     * </p>
     *
     * @param p_parts An array of strings containing the command parts. The expected format is:
     *                [orderType, fromTerritoryName, toTerritoryName, numberOfArmies].
     * @param p_map The map containing the territories.
     * @return {@code true} if the advance command is valid, {@code false} otherwise.
     */
    private boolean validateAdvance(String[] p_parts, Map p_map) {
        if (p_parts.length != 4) {
            return false;
        }
        Territory l_territoryFrom = findTerritoryByName(p_parts[1]);
        Territory l_territoryTo = p_map.getTerritoryByName(p_parts[2]);
        if (l_territoryFrom == null || l_territoryTo == null) {
            System.err.println("Territorie(s) not found!");
            return false;
        }
        if (l_territoryFrom.getName().equals(l_territoryTo.getName())) {
            System.err.println("Can't advance in your own territory!");
            return false;
        }
        int l_numberOfArmies;
        try {
            l_numberOfArmies = Integer.parseInt(p_parts[3]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number of armies!");
            return false;
        }
        if (!l_territoryFrom.getOwner().getName().equals(this.getName())) {
            System.err.println(l_territoryFrom + " does not belong to " + this.getName());
            return false;
        }
        if (!l_territoryFrom.hasNeighbor(l_territoryTo)) {
            System.err.println(l_territoryFrom.getName() + " is not adjacent to " + l_territoryTo.getName());
            return false;
        }
        if (l_territoryFrom.getNumOfArmies() - l_numberOfArmies < 0) {
            System.err.println("Not enough armies on " + l_territoryFrom.getName() + ". Only " 
                               + l_territoryFrom.getNumOfArmies() + " available!");
            return false;
        }
        return true;
    }

    /**
     * Validates a bomb command.
     * <p>
     * This method checks if the bomb command has valid syntax, whether the target territory exists,
     * if the player has a bomb card, and if the target territory is adjacent to any of the player's
     * owned territories.
     * </p>
     *
     * @param p_parts An array of strings containing the command parts. The expected format is:
     *                [orderType, targetTerritoryName].
     * @param p_map The map containing the territories.
     * @return {@code true} if the bomb command is valid, {@code false} otherwise.
     */
    public boolean validateBomb(String[] p_parts, Map p_map) {
        if (p_parts.length != 2) {
            System.err.println("Expecting 2 arguments!");
            return false;
        }
        if (!removeCard(CardType.BOMB)) {
            System.err.println("No Bomb cards available!");
            return false;
        }
        Territory l_territoryTo = p_map.getTerritoryByName(p_parts[1]);
        if (l_territoryTo == null) {
            System.err.println("Invalid Territory Name!");
            return false;
        }
        if (this.getOwnedTerritories().contains(l_territoryTo)) {
            System.err.println("Cannot use bomb on your own territory!");
            return false;
        }
        boolean l_found = false;
        for (Territory l_territory : this.getOwnedTerritories()) {
            if (l_territory.hasNeighbor(l_territoryTo)) {
                l_found = true;
                break;
            }
        }
        if (!l_found) {
            System.err.println(l_territoryTo.getName() + " is not adjacent to any of your owned territories!");
            return false;
        }
        return true;
    }

    /**
     * Validates a blockade command.
     * <p>
     * This method checks if the blockade command has valid syntax, whether the target territory exists,
     * and if the player has a blockade card.
     * </p>
     *
     * @param p_parts An array of strings containing the command parts. The expected format is:
     *                [orderType, targetTerritoryName].
     * @return {@code true} if the blockade command is valid, {@code false} otherwise.
     */
    public boolean validateBlockade(String[] p_parts) {
        if (p_parts.length != 2) {
            System.err.println("Invalid number of arguments!");
            return false;
        }
        if (!removeCard(CardType.BLOCKADE)) {
            System.err.println("No Blockade cards available!");
            return false;
        }
        Territory l_territoryTo = findTerritoryByName(p_parts[1]);
        if (l_territoryTo == null) {
            System.err.println("Territory is not owned by " + this.getName() + " or does not exist");
            return false;
        }
        return true;
    }

    /**
     * Validates an airlift command.
     * <p>
     * This method checks if the airlift command has valid syntax, whether the source and destination
     * territories exist, if the player has an airlift card, if the player owns the source territory,
     * and if the player has enough armies to airlift.
     * </p>
     *
     * @param p_parts An array of strings containing the command parts. The expected format is:
     *                [orderType, fromTerritoryName, toTerritoryName, numberOfArmies].
     * @param p_map The map containing the territories.
     * @return {@code true} if the airlift command is valid, {@code false} otherwise.
     */
    public boolean validateAirlift(String[] p_parts, Map p_map) {
        if (p_parts.length != 4) {
            System.err.println("Invalid number of arguments!");
            return false;
        }
        if (!removeCard(CardType.AIRLIFT)) {
            System.err.println("No Airlift cards available!");
            return false;
        }
        Territory l_territoryFrom = findTerritoryByName(p_parts[1]);
        Territory l_territoryTo = p_map.getTerritoryByName(p_parts[2]);
        if (l_territoryFrom == null || l_territoryTo == null) {
            System.err.println("Territorie(s) not found!");
            return false;
        }
        if (l_territoryFrom.getName().equals(l_territoryTo.getName())) {
            System.err.println("Can't Airlift in your own territory!");
            return false;
        }
        int l_numberOfArmies;
        try {
            l_numberOfArmies = Integer.parseInt(p_parts[3]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number of armies!");
            return false;
        }
        if (!l_territoryFrom.getOwner().getName().equals(this.getName())) {
            System.err.println(l_territoryFrom + " does not belong to " + this.getName());
            return false;
        }
        if (l_territoryFrom.getNumOfArmies() - l_numberOfArmies < 0) {
            System.err.println("Not enough armies on " + l_territoryFrom.getName() + ". Only " 
                               + l_territoryFrom.getNumOfArmies() + " available!");
            return false;
        }
        return true;
    }

    /**
     * Validates a negotiate command.
     * <p>
     * This method checks if the negotiate command has valid syntax, whether the target player exists,
     * and if the player has a negotiate card.
     * </p>
     *
     * @param l_parts An array of strings containing the command parts. The expected format is:
     *                [orderType, targetPlayerName].
     * @param p_players A list of all players in the game.
     * @return {@code true} if the negotiate command is valid, {@code false} otherwise.
     */
    public boolean validateNegociate(String[] l_parts, List<Player> p_players) {
        if (l_parts.length != 2) {
            System.err.println("Invalid number of arguments!");
            return false;
        }
        if (!removeCard(CardType.NEGOTIATE)) {
            System.err.println("No Negotiate cards available!");
            return false;
        }
        Player l_playerToNegociateWith = null;
        for (Player l_player : p_players) {
            if (l_player.getName().equals(l_parts[1])) {
                l_playerToNegociateWith = l_player;
                break;
            }
        }
        if (l_playerToNegociateWith == null) {
            System.err.println("Player not found!");
            return false;
        }
        if (l_playerToNegociateWith.getName().equals(this.d_name)) {
            System.err.println("Cannot negotiate with yourself!");
            return false;
        }
        return true;
    }

    /**
     * Issues an order based on the provided command.
     * <p>
     * This method parses the command, validates it using the appropriate validation method, and
     * creates the corresponding order if the command is valid.
     * </p>
     *
     * @param p_command The command string containing the order type and its parameters.
     * @param p_map The map containing the territories.
     * @param p_players A list of all players in the game.
     * @return {@code true} if the order was successfully issued, {@code false} otherwise.
     */
    public boolean issueOrder(String p_command, Map p_map, List<Player> p_players) {
        String[] l_parts = p_command.split(" ");
        if (l_parts.length < 2 || l_parts.length > 4) {
            return false;
        }
        String l_orderType = l_parts[0].toLowerCase();
        if (l_orderType.equals("deploy")) {
            if (!validateDeploy(l_parts)) {
                return false;
            }
            String l_targetTerritoryName = l_parts[1];
            int l_numberOfArmies = Integer.parseInt(l_parts[2]);
            Territory l_targetTerritory = findTerritoryByName(l_targetTerritoryName);
            DeployOrder l_deployOrder = new DeployOrder(this, l_targetTerritory, l_numberOfArmies);
            d_orders.add(l_deployOrder);
            this.d_nbrOfReinforcementArmies -= l_numberOfArmies;
            return true;
        } else if (l_orderType.equals("advance")) {
            if (!validateAdvance(l_parts, p_map)) {
                return false;
            }
            Territory l_territoryFrom = findTerritoryByName(l_parts[1]);
            Territory l_territoryTo = p_map.getTerritoryByName(l_parts[2]);
            int l_numberOfArmies = Integer.parseInt(l_parts[3]);
            l_territoryFrom.setNumOfArmies(l_territoryFrom.getNumOfArmies() - l_numberOfArmies);
            if (l_territoryTo.getOwner() == null || l_territoryTo.getOwner().getName().equals(this.getName())) {
                AdvanceMove advanceMove = new AdvanceMove(this, l_territoryFrom, l_territoryTo, l_numberOfArmies);
                d_orders.add(advanceMove);
            } else {
                AdvanceAttack advanceAttack = new AdvanceAttack(this, l_territoryFrom, l_territoryTo, l_numberOfArmies);
                d_orders.add(advanceAttack);
            }
            return true;
        } else if (l_orderType.equalsIgnoreCase("bomb")) {
            if (!validateBomb(l_parts, p_map)) {
                return false;
            }
            Territory l_territoryTo = p_map.getTerritoryByName(l_parts[1]);
            BombOrder bombOrder = new BombOrder(this, l_territoryTo);
            d_orders.add(bombOrder);
            return true;
        } else if (l_orderType.equalsIgnoreCase("blockade")) {
            if (!validateBlockade(l_parts)) {
                return false;
            }
            Territory l_territoryTo = findTerritoryByName(l_parts[1]);
            BlockadeOrder blockadeOrder = new BlockadeOrder(this, l_territoryTo);
            d_orders.add(blockadeOrder);
            return true;
        } else if (l_orderType.equalsIgnoreCase("airlift")) {
            if (!validateAirlift(l_parts, p_map)) {
                return false;
            }
            Territory l_territoryFrom = findTerritoryByName(l_parts[1]);
            Territory l_territoryTo = p_map.getTerritoryByName(l_parts[2]);
            int l_numberOfArmies = Integer.parseInt(l_parts[3]);
            l_territoryFrom.setNumOfArmies(l_territoryFrom.getNumOfArmies() - l_numberOfArmies);
            if (l_territoryTo.getOwner() == null || l_territoryTo.getOwner().getName().equals(this.getName())) {
                AirliftMove airliftMove = new AirliftMove(this, l_territoryFrom, l_territoryTo, l_numberOfArmies);
                d_orders.add(airliftMove);
            } else {
                AirliftAttack airliftAttack = new AirliftAttack(this, l_territoryFrom, l_territoryTo, l_numberOfArmies);
                d_orders.add(airliftAttack);
            }
            return true;
        } else if (l_orderType.equalsIgnoreCase("negotiate")) {
            if (!validateNegociate(l_parts, p_players)) {
                return false;
            }
            Player l_playerToNegotiateWith = null;
            for (Player l_player : p_players) {
                if (l_player.getName().equals(l_parts[1])) {
                    l_playerToNegotiateWith = l_player;
                    break;
                }
            }
            NegotiateOrder negotiateOrder = new NegotiateOrder(this, l_playerToNegotiateWith);
            negotiateOrder.execute();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prompts the user to issue an order.
     * <p>
     * This method repeatedly prompts the user to enter a deploy order until a valid command is entered
     * or the user finishes the process.
     * </p>
     */
    public void issueOrder() {
        java.util.Scanner l_scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.print("Hi " + this.d_name + ", please enter your next deploy order, or type FINISH: ");
            String l_command = l_scanner.nextLine();
            String[] l_commandParts = l_command.split(" ");
            if (l_command.equalsIgnoreCase("FINISH")) {
                break;
            }
            if (l_commandParts.length != 3) {
                System.out.println("Invalid command format. Usage: <OrderType> <territoryName> <numArmies>");
                continue;
            }
            String l_orderType = l_commandParts[0];
            String l_targetTerritoryName = l_commandParts[1];
            int l_numberOfArmies;
            try {
                l_numberOfArmies = Integer.parseInt(l_commandParts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number of armies: " + l_commandParts[2]);
                continue;
            }
            Territory l_targetTerritory = findTerritoryByName(l_targetTerritoryName);
            if (l_targetTerritory == null) {
                System.out.println("Territory not found!: " + l_targetTerritoryName);
                continue;
            }
            if (l_numberOfArmies > this.d_nbrOfReinforcementArmies) {
                System.out.println("Not enough reinforcements. You have " + this.d_nbrOfReinforcementArmies + " available.");
                continue;
            }
            if (l_orderType.equalsIgnoreCase("deploy")) {
                DeployOrder l_deployOrder = new DeployOrder(this, l_targetTerritory, l_numberOfArmies);
                d_orders.add(l_deployOrder);
                this.d_nbrOfReinforcementArmies -= l_numberOfArmies;
                System.out.println(this.d_name + "'s deploy order issued: Deploy " + l_numberOfArmies + " armies to " + l_targetTerritoryName);
                continue;
            }
        }
    }

    /**
     * Retrieves the next order from the list of orders.
     * <p>
     * This method returns and removes the first order from the list of orders.
     * </p>
     *
     * @return The next {@link Order} in the list, or {@code null} if no orders are available.
     */
    public Order nextOrder() {
        return d_orders.isEmpty() ? null : d_orders.remove(0);
    }

    /**
     * Gets the number of reinforcement armies available for the player.
     *
     * @return The number of reinforcement armies.
     */
    public int getNbrOfReinforcementArmies() {
        return d_nbrOfReinforcementArmies;
    }

    /**
     * Sets the number of reinforcement armies available for the player.
     *
     * @param p_nbrOfReinforcementArmies The number of reinforcement armies to set.
     */
    public void setNbrOfReinforcementArmies(int p_nbrOfReinforcementArmies) {
        this.d_nbrOfReinforcementArmies = p_nbrOfReinforcementArmies;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Sets the name of the player.
     *
     * @param p_name The name to set for the player.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Gets the list of owned territories.
     *
     * @return A list of {@link Territory} objects representing the owned territories.
     */
    public List<Territory> getOwnedTerritories() {
        return d_ownedTerritories;
    }

    /**
     * Sets the list of owned territories.
     *
     * @param p_ownedTerritories A list of {@link Territory} objects to set as the owned territories.
     */
    public void setOwnedTerritories(List<Territory> p_ownedTerritories) {
        this.d_ownedTerritories = p_ownedTerritories;
    }

    /**
     * Gets the list of orders.
     *
     * @return A list of {@link Order} objects representing the orders.
     */
    public List<Order> getOrders() {
        return d_orders;
    }

    /**
     * Sets the list of orders.
     *
     * @param p_orders A list of {@link Order} objects to set as the orders.
     */
    public void setOrders(List<Order> p_orders) {
        this.d_orders = p_orders;
    }

    /**
     * Clears all the orders.
     * <p>
     * This method removes all orders from the list of orders.
     * </p>
     */
    public void clearOrders() {
        this.d_orders.clear();
    }

    /**
     * Finds a territory by its name.
     *
     * @param p_territoryName The name of the territory to search for.
     * @return The {@link Territory} object with the given name, or {@code null} if no such territory is found.
     */
    public Territory findTerritoryByName(String p_territoryName) {
        for (Territory l_territory : d_ownedTerritories) {
            if (l_territory.getName().equals(p_territoryName)) {
                return l_territory;
            }
        }
        return null;
    }
    
    ///////////////// Card Management using HashMap /////////////////

    /**
     * Adds a card of the specified type to the player's collection.
     * If the card type already exists, increments its count.
     *
     * @param p_cardType The type of card to add.
     */
    public void addCard(CardType p_cardType) {
        int count = d_cards.getOrDefault(p_cardType, 0);
        d_cards.put(p_cardType, count + 1);
    }

    /**
     * Removes one card of the specified type from the player's collection.
     * If the card count is greater than one, decrements the count.
     * If the count is one, removes the card type from the collection.
     *
     * @param p_cardType The type of card to remove.
     * @return {@code true} if the card was successfully removed, {@code false} if the card type was not found.
     */
    public boolean removeCard(CardType p_cardType) {
        if (d_cards.containsKey(p_cardType)) {
            int count = d_cards.get(p_cardType);
            if (count > 1) {
                d_cards.put(p_cardType, count - 1);
            } else {
                d_cards.remove(p_cardType);
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves the player's current collection of cards.
     *
     * @return A {@link HashMap} containing card types as keys and their respective counts as values.
     */
    public HashMap<CardType, Integer> getCards() {
        return d_cards;
    }
    
    /**
     * Returns a formatted string of the player's cards.
     * Example output: "BOMB: 1, AIRLIFT: 2"
     * 
     * @return A formatted string.
     */
    public String getFormattedCards() {
        StringBuilder sb = new StringBuilder();
        for (CardType card : d_cards.keySet()) {
            sb.append(card.name()).append(": ").append(d_cards.get(card)).append(", ");
        }
        if (sb.length() > 0) {
            // Remove trailing comma and space
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
    
    ///////////////////////////// Conquered Territories Management /////////////////////////////

    /**
     * Gets the number of territories conquered by the player in the current turn.
     *
     * @return The number of conquered territories for the current turn.
     */
    public int getConqueredTerritoriesPerTurn() {
        return d_conqueredTerritoriesPerTurn;
    }

    /**
     * Increments the count of conquered territories for the current turn by one.
     */
    public void incrementConqueredTerritoriesPerTurn() {
        d_conqueredTerritoriesPerTurn++;
    }

    /**
     * Resets the count of conquered territories for the current turn to zero.
     */
    public void resetConqueredTerritoriesPerTurn() {
        d_conqueredTerritoriesPerTurn = 0;
    }
    
    ///////////////////////////// Negotiated Players Management /////////////////////////////

    /**
     * Retrieves the list of players that the current player has negotiated with during the current turn.
     *
     * @return A list of players that have been negotiated with in the current turn.
     */
    public List<Player> getNegociatedPlayersPerTurn() {
        return d_negociatedPlayersPerTurn;
    }

    /**
     * Sets the list of players that the current player has negotiated with during the current turn.
     *
     * @param p_negociatedPlayers A list of players that the current player has negotiated with.
     */
    public void setNegociatedPlayersPerTurn(List<Player> p_negociatedPlayers) {
        this.d_negociatedPlayersPerTurn = p_negociatedPlayers;
    }

    /**
     * Resets the list of players that the current player has negotiated with, clearing all entries.
     */
    public void resetNegociatedPlayersPerTurn() {
        d_negociatedPlayersPerTurn.clear();
    }
    
    ///////////////////////////// Conquered Flag Management /////////////////////////////

    /**
     * Retrieves whether the player has conquered any territories during the current turn.
     *
     * @return {@code true} if the player has conquered territories this turn, {@code false} otherwise.
     */
    public boolean getHasConqueredThisTurn() {
        return d_hasConqueredThisTurn;
    }

    /**
     * Sets the flag indicating whether the player has conquered territories during the current turn.
     *
     * @param p_value {@code true} if the player has conquered territories, {@code false} otherwise.
     */
    public void setHasConqueredThisTurn(boolean p_value) {
        d_hasConqueredThisTurn = p_value;
    }

    /**
     * Returns a string representation of the player, including their name, number of reinforcement armies,
     * and the number of conquered territories for the current turn.
     *
     * @return A string describing the player's current status.
     */
    @Override
    public String toString() {
        return "\nPlayer: " + this.d_name +
               "\nNumber of Reinforcement Armies: " + this.d_nbrOfReinforcementArmies +
               "\nConquered Territories This Turn: " + this.d_conqueredTerritoriesPerTurn;
    }
}
