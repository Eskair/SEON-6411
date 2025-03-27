package com.Game.view;

import com.Game.model.Map;
import com.Game.model.Player;
import com.Game.model.Territory;
import com.Game.model.CardType;

import java.util.List;
import java.util.ArrayList;

/**
 * Responsible for displaying game information to the user.
 * This class handles all the console output related to the game state.
 */
public class GameView {
    
    /**
     * Displays the welcome message when the game starts.
     */
    public void displayWelcomeMessage() {
        System.out.println("Welcome to Warzone Game!");
    }
    
    /**
     * Displays the menu for the map editing phase.
     */
    public void displayMapEditingMenu() {
        System.out.println("\n=== Map Editing Phase Menu ===\n");
        System.out.println("1. editcontinent <args>   - Edit continent details");
        System.out.println("2. editcountry <args>     - Edit country details");
        System.out.println("3. editneighbor <args>    - Edit neighboring territories");
        System.out.println("4. showmap                - Display the current map");
        System.out.println("5. savemap <args>         - Save the current map");
        System.out.println("6. editmap <args>         - Edit the map");
        System.out.println("7. validatemap            - Validate the map");
        System.out.println("8. loadmap <args>         - Load an existing map");
        System.out.println("9. gameplayer <args>      - Setup players and transition to startup phase");
        System.out.println("\nType 'exit' to quit the map editing phase.\n");
    }
    
    /**
     * Displays the menu for the startup phase.
     */
    public void displayStartupMenu() {
        System.out.println("\n=== Startup Phase Menu ===\n");
        System.out.println("1. showmap            - Display the current map");
        System.out.println("2. gameplayer <args>  - Manage game players");
        System.out.println("3. assigncountries    - Assign countries to players");
        System.out.println("4. startgame          - Start the main game");
        System.out.println("5. editmap <args>     - Return to map editing phase");
        System.out.println("6. loadmap <args>     - Load a different map");
        System.out.println("\nType 'exit' to quit the startup phase.\n");
    }
    
    /**
     * Displays the menu for the main game phase.
     */
    public void displayMainGameMenu() {
        System.out.println("\n=== Main Game Phase Menu ===\n");
        System.out.println("1. showmap         - Display the current map");
        System.out.println("2. issueorder      - Issue an order");
        System.out.println("3. executeorders   - Execute all issued orders");
        System.out.println("4. endturn         - End the current turn, and move to the next one");
        System.out.println("\nType 'exit' to quit the main game phase.\n");
    }
    
    /**
     * Displays the current state of the map and players.
     * 
     * @param p_gameMap The game map to display
     * @param p_players The list of players to display
     */
    public void displayMap(Map p_gameMap, List<Player> p_players) {
        System.out.println("\n======== Game Map Overview ========");
        
        // Get the full list of territories from the map.
        List<Territory> l_allTerritories = p_gameMap.getTerritoryList();
        // Build a list of unique continents.
        List<String> l_continents = new ArrayList<>();
        for (Territory l_territory : l_allTerritories) {
            String l_continent = l_territory.getContinent();
            if (!l_continents.contains(l_continent)) {
                l_continents.add(l_continent);
            }
        }
        
        // Print territories grouped by continent.
        for (String l_continent : l_continents) {
            System.out.println("\nContinent: " + l_continent);
            System.out.println("----------------------------");
            for (Territory l_territory : l_allTerritories) {
                if (l_territory.getContinent().equals(l_continent)) {
                    System.out.println("Territory: " + l_territory.getName() 
                            + " | Armies: " + l_territory.getNumOfArmies());
                    
                    // Display neighbor list for this territory.
                    List<Territory> l_neighbors = l_territory.getNeighborList();
                    if (l_neighbors.isEmpty()) {
                        System.out.println("    Neighbors: None");
                    } else {
                        System.out.print("    --> Neighbors: ");
                        for (Territory l_neighbor : l_neighbors) {
                            System.out.print(l_neighbor.getName() + " || ");
                        }
                        System.out.println(); // Newline after listing neighbors.
                    }
                }
            }
        }
        System.out.println("=====================================");
        
        // Then display detailed status for each player.
        System.out.println("\nPlayers status:");
        for (Player l_player : p_players) {
            System.out.println("-------------------------------------------------");
            System.out.println("Player Name: " + l_player.getName());
            System.out.println("Total Territories Owned: " + l_player.getOwnedTerritories().size());
            System.out.println("Available Reinforcement Armies: " + l_player.getNbrOfReinforcementArmies());
            
            System.out.println("Cards Available:");
            System.out.println(l_player.getFormattedCards());


            System.out.println("Territory Details:");
            for (Territory l_territory : l_player.getOwnedTerritories()) {
                System.out.println("    - " + l_territory.getName() 
                        + " (" + l_territory.getNumOfArmies() + " armies)");
            }
        }
        System.out.println("-------------------------------------------------");
    }
    
    /**
     * Displays a general message to the user.
     * 
     * @param p_message The message to display
     */
    public void displayMessage(String p_message) {
        System.out.println(p_message);
    }
    
    /**
     * Displays an error message to the user.
     * 
     * @param p_errorMessage The error message to display
     */
    public void displayError(String p_errorMessage) {
        System.err.println("Error: " + p_errorMessage);
    }
    
    /**
     * Displays information about the reinforcement phase.
     */
    public void displayReinforcementPhase() {
        System.out.println("\n===== REINFORCEMENT PHASE =====");
    }
    
    /**
     * Displays reinforcement allocation for a player.
     * 
     * @param p_playerName The name of the player
     * @param p_reinforcements The number of reinforcements allocated
     */
    public void displayReinforcementAllocation(String p_playerName, int p_reinforcements) {
        System.out.println(p_playerName + " receives " + p_reinforcements + " reinforcement armies.");
    }
    
    /**
     * Displays the end of reinforcement phase message.
     */
    public void displayReinforcementComplete() {
        System.out.println("\nPlayers have been assigned their armies!\n");
    }
    
    /**
     * Displays information about the issue orders phase.
     */
    public void displayIssueOrdersPhase() {
        System.out.println("\n===== ISSUE ORDERS PHASE =====");
    }
    
    /**
     * Displays information about a player's turn during the issue orders phase.
     * 
     * @param p_playerName The name of the player whose turn it is
     * @param p_reinforcements The number of reinforcements the player has left
     */
    public void displayPlayerTurn(String p_playerName, int p_reinforcements) {
        System.out.println("\n" + p_playerName + "'s turn (" + p_reinforcements + " reinforcements left)");
    }
    
    /**
     * Displays a player's territories.
     *
     * @param p_territories The list of territories owned by the player.
     * @param p_player The player whose territories are being displayed.
     * @param p_gameMap The game map containing all territories and their connections.
     */
    public void displayPlayerTerritories(List<Territory> p_territories, Player p_player, Map p_gameMap) {
        System.out.println("Your territories:");
        for (int i = 0; i < p_territories.size(); i++) {
            Territory l_territory = p_territories.get(i);
            System.out.println((i+1) + ". " + l_territory.getName() + " (" + l_territory.getNumOfArmies() + " armies)");
            List<Territory> neighbors = l_territory.getNeighborList();
            System.out.print("		- Neighbors: ");
            for (Territory l_neighbor : neighbors) {
            	if(p_player.getOwnedTerritories().contains(l_neighbor)) {
            		System.out.print(l_neighbor.getName() + "(Owned) || ");
            	}
            	else {
            		if(l_neighbor.getOwner() != null) {
            			System.out.print(l_neighbor.getName() + " (Enemy: "+ l_neighbor.getOwner().getName() +") || ");
            		}
            		else {
            			System.out.print(l_neighbor.getName() + " (Neutral) || ");
            		}
            		
            	}
            	
            }
            System.out.println();
        }
    }
    
    /**
     * Displays the completion of the issue orders phase.
     */
    public void displayIssueOrdersComplete() {
        System.out.println("\nAll players have issued their orders.\n");
    }
    
    /**
     * Displays information about the execute orders phase.
     */
    public void displayExecuteOrdersPhase() {
        System.out.println("\n===== EXECUTE ORDERS PHASE =====");
        System.out.println("Executing all orders...");
    }
    
    /**
     * Displays information about a player's order being executed.
     * 
     * @param p_playerName The name of the player whose order is being executed
     */
    public void displayExecutingOrder(String p_playerName) {
        System.out.println("\nExecuting order from " + p_playerName + ":");
    }
    
    /**
     * Displays the completion of the execute orders phase.
     */
    public void displayExecuteOrdersComplete() {
        System.out.println("\nAll orders executed. Use 'endturn' to end the turn or 'showmap' to see the current state.");
    }
    
    /**
     * Displays a message when a player wins the game.
     * 
     * @param p_winnerName The name of the winning player
     */
    public void displayWinner(String p_winnerName) {
        System.out.println("\n*******************************");
        System.out.println("Game Over! " + p_winnerName + " wins!");
        System.out.println("*******************************\n");
    }
    
    /**
     * Displays a message at the end of a turn.
     */
    public void displayEndTurn() {
        System.out.println("\nTurn ended. Starting new turn.");
    }
}