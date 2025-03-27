package com.Game.Phases;

import com.Game.model.Player;
import com.Game.controller.GameController;
import com.Game.view.CommandPromptView;
import com.Game.model.Map;

import java.util.List;

/**
 * Represents the Issue Order phase of the game.
 * In this phase, players assign orders such as deploying armies to their territories.
 */
public class IssueOrderPhase extends Phase{

    /**
     * Starts the Issue Order phase, allowing players to assign orders until they finish or run out of reinforcement armies.
     *
     * @param p_gameController    the game controller handling the game state
     * @param p_players           list of players participating in the game
     * @param p_commandPromptView view handling user interactions through the command prompt
     * @param p_commandParts       the command parts passed as input
     */
    @Override
    public void StartPhase(GameController p_gameController, List<Player> p_players, CommandPromptView p_commandPromptView, String[] p_commandParts, Map p_gameMap) {

    	for (Player l_player : p_players) {
            if (l_player.getNbrOfReinforcementArmies() > 0) {
                p_gameController.getView().displayPlayerTurn(l_player.getName(), l_player.getNbrOfReinforcementArmies());
                p_gameController.getView().displayPlayerTerritories(l_player.getOwnedTerritories(), l_player, p_gameMap);
                
                while (l_player.getNbrOfReinforcementArmies() > 0) {
                    String l_orderCommand = p_commandPromptView.getPlayerOrder(
                        l_player.getName(), l_player.getNbrOfReinforcementArmies());
                    
                    if (l_orderCommand.equalsIgnoreCase("FINISH")) {
                        break;  // Player is done issuing orders
                    }
                    
                    // Just call issueOrder with the entire string
                    boolean l_success = l_player.issueOrder(l_orderCommand, p_gameMap, p_players);
                    
                    if (!l_success) { 
                        p_gameController.getView().displayError(
                            "Failed to create order. Please check your command format.");
                    }
                }
            }
        }
    }

}
